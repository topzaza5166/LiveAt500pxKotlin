package com.vertice.teepop.liveat500pxkotlin.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.vertice.teepop.liveat500pxkotlin.R
import com.vertice.teepop.liveat500pxkotlin.dao.PhotoItemDao
import com.vertice.teepop.liveat500pxkotlin.fragment.PhotoInfoSummaryFragment

class MoreInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_info)

        initInstances()

        val dao: PhotoItemDao = intent.getParcelableExtra("dao")

        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                    .add(R.id.infoContentContainer, PhotoInfoSummaryFragment.newInstance(dao))
                    .commit()
    }

    private fun initInstances() {
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }
}
