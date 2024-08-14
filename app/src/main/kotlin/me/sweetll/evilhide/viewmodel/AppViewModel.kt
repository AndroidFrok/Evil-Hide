package me.sweetll.evilhide.viewmodel

import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AlertDialog
import android.view.View
import android.widget.EditText
import me.sweetll.evilhide.model.AppInfo
import me.sweetll.evilhide.service.HiddenService

class AppViewModel(val context: Context, val appInfo: AppInfo) {
    val appName: ObservableField<String> = ObservableField()
    val appIcon: ObservableField<Drawable> = ObservableField()
    val isStar: ObservableBoolean = ObservableBoolean()
    val isHidden: ObservableBoolean = ObservableBoolean()

    init {
        val pm = context.packageManager
        appName.set(pm.getApplicationLabel(appInfo.applicationInfo).toString())
        appIcon.set(pm.getApplicationIcon(appInfo.applicationInfo))
        isStar.set(appInfo.favorite)
        isHidden.set(appInfo.hidden)
    }

    @Suppress("UNUSED_PARAMETER")
    fun onClickApp(view: View) {
        val intent = context.packageManager.getLaunchIntentForPackage(appInfo.packageName)
        intent?.let { context.startActivity(it) }
    }

    @Suppress("UNUSED_PARAMETER")
    fun onClickAdd(view: View) {
        val passwordEdit = EditText(context)
        passwordEdit.setText(appInfo.password)
        AlertDialog.Builder(context)
                .setTitle("请输入启动该应用的密码")
                .setView(passwordEdit)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", {
                    dialog, which ->
                        appInfo.password = passwordEdit.text.toString()
                })
    }

    fun onFavoriteChange(favorite: Boolean) {
        appInfo.favorite = favorite
        isStar.set(favorite)
    }

    fun onCheckChange(hidden: Boolean) {
        appInfo.hidden = hidden
        isHidden.set(hidden)
    }

}
