package me.sweetll.evilhide.model


import android.content.pm.ApplicationInfo
import me.sweetll.evilhide.AppApplication
import me.sweetll.evilhide.extension.*
import me.sweetll.evilhide.service.HiddenService

class AppInfo(var applicationInfo: ApplicationInfo, _hidden: Boolean) {
    val packageName: String
        get() = applicationInfo.packageName

    var favorite: Boolean
        get() = packageName.getFavorite()
        set(value) {
            packageName.saveFavorite(value)
        }

    var hidden: Boolean = _hidden
        set(value) {
            val cmd = "pm ${if (value) "hide" else "unhide"} $packageName"
            HiddenService.performAction(cmd)
        }

    var password: String
        get() = packageName.getPassword()
        set(value) {
            packageName.savePassword(value)
        }
}