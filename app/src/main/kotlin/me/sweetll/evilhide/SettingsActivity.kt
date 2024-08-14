package me.sweetll.evilhide

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import me.sweetll.evilhide.databinding.ActivitySettingsBinding
import me.sweetll.evilhide.fragment.SettingFragment

class SettingsActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivitySettingsBinding = DataBindingUtil.setContentView(this, R.layout.activity_settings)
        fragmentManager.beginTransaction()
                .replace(android.R.id.content, SettingFragment())
                .commit()
    }
}
