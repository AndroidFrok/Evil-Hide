package me.sweetll.evilhide.model


import android.content.pm.ApplicationInfo
import me.sweetll.evilhide.AppApplication
import me.sweetll.evilhide.extension.*
import me.sweetll.evilhide.service.HiddenService

class AppInfo(var applicationInfo: ApplicationInfo) {
    val packageName: String
        get() = applicationInfo.packageName

    var favorite: Boolean
        get() = packageName.getFavorite()
        set(value) {
            packageName.saveFavorite(value)
        }
    var hidden: Boolean
        get() = applicationInfo.flags and ApplicationInfo.FLAG_INSTALLED != ApplicationInfo.FLAG_INSTALLED
        set(value) {
            val cmd = "pm ${if (value) "hide" else "unhide"} $packageName"
            HiddenService.performAction(cmd)
            applicationInfo.flags = applicationInfo.flags xor ApplicationInfo.FLAG_INSTALLED
        }
    var password: String
        get() = packageName.getPassword()
        set(value) {
            packageName.savePassword(value)
        }
}