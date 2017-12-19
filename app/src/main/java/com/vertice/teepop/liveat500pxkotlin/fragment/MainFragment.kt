package com.vertice.teepop.liveat500pxkotlin.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.vertice.teepop.liveat500pxkotlin.MainApplication
import com.vertice.teepop.liveat500pxkotlin.R
import com.vertice.teepop.liveat500pxkotlin.dao.PhotoItemDao
import com.vertice.teepop.liveat500pxkotlin.manager.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by nuuneoi on 11/16/2014.
 */
class MainFragment : Fragment() {

    interface FragmentListener {
        fun onPhotoItemClicked(dao: PhotoItemDao)
    }

    private val TAG: String = MainFragment::class.java.simpleName

    @field:[Named("PRIMARY_PREFS")]
    @Inject
    lateinit var pref: SharedPreferences

    @Inject
    lateinit var service: ApiService

//    val service by lazy {
//        ApiApiService.create()
//    }

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainApplication.appComponent.inject(this)
        init(savedInstanceState)

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_main, container, false)
        initInstances(rootView, savedInstanceState)
        return rootView
    }

    private fun init(savedInstanceState: Bundle?) {
        // Init Fragment level's variable(s) here
    }

    private fun initInstances(rootView: View, savedInstanceState: Bundle?) {
        // Init 'View' instance(s) with rootView.findViewById here
        disposable =
                service.loadPhotoList()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { result -> Toast.makeText(context, result.success.toString(), Toast.LENGTH_SHORT).show() },
                                { error -> Log.d(TAG, error.message) }
                        )
    }


    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
        disposable?.dispose()
    }

    /*
     * Save Instance State Here
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    private fun onRestoreInstanceState(savedInstanceState: Bundle) {
        // Restore Instance State here
    }

    companion object {

        fun newInstance(): MainFragment {
            val fragment = MainFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

}