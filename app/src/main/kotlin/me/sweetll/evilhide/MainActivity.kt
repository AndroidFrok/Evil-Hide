package me.sweetll.evilhide

import android.Manifest
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener

import me.sweetll.evilhide.adapter.AppAdapter
import me.sweetll.evilhide.config.Settings
import me.sweetll.evilhide.databinding.ActivityMainBinding
import me.sweetll.evilhide.extension.getFavorite
import me.sweetll.evilhide.model.AppInfo

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val appAdapter = AppAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        initPermissions()

        setupRecyclerView()

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                populateAppList(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    fun setupRecyclerView() {
        binding.appRecycler.layoutManager = LinearLayoutManager(this)
        binding.appRecycler.adapter = appAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent()
                intent.setClass(this, SettingsActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun initPermissions() {
        val snackBarPermissionListener = SnackbarOnDeniedPermissionListener.Builder
                .with(binding.root as ViewGroup, "需要电话权限以便从拨号盘启动")
                .withOpenSettingsButton("设置")
                .build()
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.PROCESS_OUTGOING_CALLS)
                .withListener(snackBarPermissionListener)
                .check()
    }

    fun populateAppList(flag: Int) {
        val pm = packageManager
        val allApps = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES)
        val installedApps = pm.getInstalledApplications(0)
        val uninstalledApps = allApps.filterNot {
            outerApp ->
            installedApps.any { it.packageName == outerApp.packageName }
        }

        appAdapter.setNewData(
                when (flag) {
                    Settings.SPINNER_STAR_APP -> allApps.filter { it.packageName.getFavorite() }
                    Settings.SPINNER_HIDDEN_APP -> uninstalledApps
                    else -> allApps
                }
                .filter { it.packageName != BuildConfig.APPLICATION_ID && it.flags and ApplicationInfo.FLAG_SYSTEM != 1}
                .fold(mutableListOf(), {
                    newData, applicationInfo ->
                    newData.add(AppInfo(applicationInfo, applicationInfo in uninstalledApps))
                    newData
                })
        )
    }

}
