package me.sweetll.evilhide.extension

import me.sweetll.evilhide.config.SharedPreferenceConfig.PREFIX_PREF_KEY_B_FAVORITE
import me.sweetll.evilhide.config.SharedPreferenceConfig.PREFIX_PREF_KEY_B_HIDDEN
import me.sweetll.evilhide.config.SharedPreferenceConfig.PREFIX_PREF_KEY_S_PASSWORD
import me.sweetll.evilhide.config.SharedPreferenceConfig.sp

fun String.getFavorite(): Boolean = sp.getBoolean("$PREFIX_PREF_KEY_B_FAVORITE$this", false)
fun String.saveFavorite(favorite: Boolean) {
    sp.edit {
        putBoolean("$PREFIX_PREF_KEY_B_FAVORITE${this@saveFavorite}", favorite)
    }
}

fun String.getHidden(): Boolean = sp.getBoolean("$PREFIX_PREF_KEY_B_HIDDEN$this", false)
fun String.saveHidden(hidden: Boolean) {
    sp.edit {
        putBoolean("$PREFIX_PREF_KEY_B_HIDDEN${this@saveHidden}", hidden)
    }
}

fun String.getPassword(): String = sp.getString("$PREFIX_PREF_KEY_S_PASSWORD$this", "").toString()
fun String.savePassword(password: String) {
    sp.edit {
        putString("$PREFIX_PREF_KEY_S_PASSWORD${this@savePassword}", password)
    }
}
