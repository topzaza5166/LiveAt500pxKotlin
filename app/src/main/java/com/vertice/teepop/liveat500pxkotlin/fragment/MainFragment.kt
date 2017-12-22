package com.vertice.teepop.liveat500pxkotlin.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.AbsListView
import android.widget.ListView
import android.widget.Toast
import com.vertice.teepop.liveat500pxkotlin.MainApplication
import com.vertice.teepop.liveat500pxkotlin.R
import com.vertice.teepop.liveat500pxkotlin.adapter.PhotoListAdapter
import com.vertice.teepop.liveat500pxkotlin.dao.PhotoItemCollectionDao
import com.vertice.teepop.liveat500pxkotlin.dao.PhotoItemDao
import com.vertice.teepop.liveat500pxkotlin.databinding.FragmentMainBinding
import com.vertice.teepop.liveat500pxkotlin.manager.ApiService
import com.vertice.teepop.liveat500pxkotlin.manager.PhotoListManager
import com.vertice.teepop.liveat500pxkotlin.utils.MutableInteger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by nuuneoi on 11/16/2014.
 */
class MainFragment : Fragment() {

    // Variable

    interface FragmentListener {
        fun onPhotoItemClicked(dao: PhotoItemDao?)
    }

    private val TAG: String = MainFragment::class.java.simpleName

    private val MODE_RELOAD = 1
    private val MODE_RELOAD_NEWER = 2
    private val MODE_LOAD_MORE = 3

    @field:[Named("PRIMARY_PREFS")]
    @Inject
    lateinit var pref: SharedPreferences

    @Inject
    lateinit var service: ApiService

//    val service by lazy {
//        ApiApiService.create()
//    }

    private lateinit var binding: FragmentMainBinding

    private lateinit var listAdapter: PhotoListAdapter
    private lateinit var photoListManager: PhotoListManager
    private lateinit var lastPositionInteger: MutableInteger

    private var disposables = CompositeDisposable()

    /*****************
     * Functions
     *****************/

    companion object {

        fun newInstance(): MainFragment {
            val fragment = MainFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainApplication.appComponent.inject(this)
        init(savedInstanceState)

        savedInstanceState?.let {
            onRestoreInstanceState(it)
        }
//        if (savedInstanceState != null)
//            onRestoreInstanceState(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        initInstances(savedInstanceState)
        return binding.root
    }

    private fun init(savedInstanceState: Bundle?) {
        // Init Fragment level's variable(s) here
        photoListManager = PhotoListManager(pref)
        lastPositionInteger = MutableInteger(-1)
    }

    private fun initInstances(savedInstanceState: Bundle?) {
        // Init 'View' instance(s) with rootView.findViewById here
        listAdapter = PhotoListAdapter(lastPositionInteger)
        listAdapter.dao = photoListManager.dao
        binding.apply {
            listView.adapter = listAdapter
            listView.setOnItemClickListener({ _, _, position: Int, _ ->
                onListViewItemClickListener(position)
            })
            listView.setOnScrollListener(onScrollListener)

            swipeRefreshLayout.setOnRefreshListener({
                refreshData()
            })

            btnNewPhotos.setOnClickListener({
                hideButtonNewPhoto()
                listView.smoothScrollToPosition(0)
            })
        }

        if (savedInstanceState == null)
            refreshData()
    }

    override fun onStart() {
        super.onStart()
        disposables = CompositeDisposable()
    }

    override fun onStop() {
        super.onStop()
        disposables.clear()
    }

    /*
     * Save Instance State Here
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save Instance State here
        outState.putBundle("lastPositionInteger", lastPositionInteger.onSaveInstanceState())
        outState.putBundle("photoListManger", photoListManager.onSaveInstanceState())
    }

    /*
     * Restore Instance State Here
     */
    private fun onRestoreInstanceState(savedInstanceState: Bundle) {
        // Restore Instance State here
        photoListManager.onRestoreInstanceState(
                savedInstanceState.getBundle("photoListManger"))
        lastPositionInteger.onRestoreInstanceState(
                savedInstanceState.getBundle("lastPositionInteger"))
    }

    private fun refreshData() {
        if (photoListManager.getCount() == 0)
            reloadData()
        else
            reloadDataNewer()
    }

    private var isLoadingMore = false

    private fun loadMoreData() {
        if (isLoadingMore)
            return

        isLoadingMore = true
        val minId = photoListManager.getMinimumId()
        val disposable = service.loadPhotoListBeforeId(minId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> onResponse(result, MODE_LOAD_MORE) },
                        { error -> onError(error, MODE_LOAD_MORE) }
                )
        disposables.add(disposable)
    }

    private fun clearLoadingMoreFlagIfCapable(mode: Int) {
        if (mode == MODE_LOAD_MORE)
            isLoadingMore = false
    }

    private fun reloadData() {
        val disposable = service.loadPhotoList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> onResponse(result, MODE_RELOAD) },
                        { error -> onError(error, MODE_RELOAD) }
                )
        disposables.add(disposable)
    }

    private fun reloadDataNewer() {
        val maxId = photoListManager.getMaximumId()
        val disposable = service.loadPhotoListAfterId(maxId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> onResponse(result, MODE_RELOAD_NEWER) },
                        { error -> onError(error, MODE_RELOAD_NEWER) }
                )
        disposables.add(disposable)
    }

    private fun onError(error: Throwable, mode: Int) {
        showToast("Something was Wrong")
        Log.d(TAG, error.message)
        clearLoadingMoreFlagIfCapable(mode)
    }

    private fun onResponse(result: PhotoItemCollectionDao, mode: Int) {
        if (!result.success) {
            showToast("Can't Load Data")
            return
        }

        val firstVisiblePosition = binding.listView.firstVisiblePosition
        val c = binding.listView.getChildAt(0)
        val top = c?.top ?: 0

        when (mode) {
            MODE_RELOAD_NEWER -> photoListManager.insertDaoAtTopPosition(result)
            MODE_LOAD_MORE -> photoListManager.appendDaoAtBottomPosition(result)
            else -> photoListManager.dao = result
        }
        clearLoadingMoreFlagIfCapable(mode)
        listAdapter.dao = photoListManager.dao
//        listAdapter.notifyDataSetChanged()

        if (mode == MODE_RELOAD_NEWER) {
            //Maintain Scroll Position
            val additionalSize = result.data.size
            listAdapter.increaseLastPosition(additionalSize)
            binding.listView.setSelectionFromTop(firstVisiblePosition + additionalSize, top)
            if (additionalSize > 0)
                showButtonNewPhoto()
        }

        binding.swipeRefreshLayout.isRefreshing = false

        showToast("Load Complete")
    }

    private fun showButtonNewPhoto() {
        binding.btnNewPhotos.visibility = View.VISIBLE
        val anim = AnimationUtils.loadAnimation(context, R.anim.down_from_top)
        binding.btnNewPhotos.startAnimation(anim)
    }

    private fun hideButtonNewPhoto() {
        binding.btnNewPhotos.visibility = View.GONE
        val anim = AnimationUtils.loadAnimation(context, R.anim.up_to_top)
        binding.btnNewPhotos.startAnimation(anim)
    }

    private fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    /********************
     * Listener Zone
     ********************/

    private val onScrollListener: AbsListView.OnScrollListener = object : AbsListView.OnScrollListener {
        override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
            if (view is ListView) {
                binding.swipeRefreshLayout.isEnabled = (firstVisibleItem == 0)
                if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                    if (photoListManager.getCount() > 0) {
                        //Load More
                        loadMoreData()
                    }
                }
            }
        }

        override fun onScrollStateChanged(p0: AbsListView?, p1: Int) {
        }
    }

    private fun onListViewItemClickListener(position: Int) {
        if (position < photoListManager.getCount()) {
            val listener = activity as? FragmentListener
            listener?.onPhotoItemClicked(photoListManager.dao.data[position])
//            if (listener is FragmentListener)
//                listener.onPhotoItemClicked(photoListManager.dao.data.get(position))
        }
    }

    /*****************
     * Inner Class
     *****************/

}

