package com.vertice.teepop.liveat500pxkotlin.activity

import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import com.vertice.teepop.liveat500pxkotlin.dao.PhotoItemDao
import com.vertice.teepop.liveat500pxkotlin.R
import com.vertice.teepop.liveat500pxkotlin.databinding.ActivityMainBinding
import com.vertice.teepop.liveat500pxkotlin.fragment.MainFragment

class MainActivity : AppCompatActivity(), MainFragment.FragmentListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.
                    beginTransaction()
                    .add(R.id.contentContainer, MainFragment.newInstance())
                    .commit()
        }

        initInstances()
    }

    private fun initInstances() {
        setSupportActionBar(binding.toolbar)

        actionBarDrawerToggle = ActionBarDrawerToggle(
                this@MainActivity,
                binding.drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer
        )
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        actionBarDrawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        actionBarDrawerToggle.onConfigurationChanged(newConfig)
    }

    override fun onPhotoItemClicked(dao: PhotoItemDao) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }

}
