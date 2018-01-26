package com.vertice.teepop.liveat500pxkotlin.activity

import android.content.Intent
import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.transition.Transition
import android.support.transition.TransitionInflater
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import com.vertice.teepop.liveat500pxkotlin.dao.PhotoItemDao
import com.vertice.teepop.liveat500pxkotlin.R
import com.vertice.teepop.liveat500pxkotlin.databinding.ActivityMainBinding
import com.vertice.teepop.liveat500pxkotlin.fragment.MainFragment
import com.vertice.teepop.liveat500pxkotlin.fragment.PhotoInfoSummaryFragment

class MainActivity : AppCompatActivity(), MainFragment.FragmentListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }

    override fun onPhotoItemClicked(view: View?, dao: PhotoItemDao?) {
        val intent = Intent(this, MoreInfoActivity::class.java)
        intent.putExtra("dao", dao)

        val moreInfoContainer: FrameLayout? = findViewById(R.id.moreInfoContainer)

        if (moreInfoContainer == null) {
            view?.let {
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, it, resources.getString(R.string.photo_item_image))
                startActivity(intent, options.toBundle())
            }

            //it not work!!
//            if (view != null && dao != null)
//                inflateSummaryFragmentWithTransition(view, dao)
        } else {
            dao?.let {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.moreInfoContainer, PhotoInfoSummaryFragment.newInstance(it))
                        .commit()
            }
        }
        Toast.makeText(this, dao?.username + dao?.caption, Toast.LENGTH_SHORT).show()
    }

    private fun inflateSummaryFragmentWithTransition(view: View, dao: PhotoItemDao) {
        // Inflate transitions to apply
        val changeTransform = TransitionInflater.from(this)
                .inflateTransition(R.transition.change_image_transform)
        val explodeTransform = TransitionInflater.from(this)
                .inflateTransition(android.R.transition.explode)

        // Setup exit transition on first fragment
        supportFragmentManager.findFragmentById(R.id.contentContainer).apply {
            sharedElementReturnTransition = changeTransform
            exitTransition = explodeTransform
        }

        // Setup enter transition on second fragment
        val fragmentTwo = PhotoInfoSummaryFragment.newInstance(dao).apply {
            sharedElementEnterTransition = changeTransform
            enterTransition = explodeTransform
        }

        supportFragmentManager.beginTransaction()
                .replace(R.id.contentContainer, fragmentTwo)
                .addToBackStack("transaction")
                .addSharedElement(view, resources.getString(R.string.photo_item_image))
                .commit()
    }

}
