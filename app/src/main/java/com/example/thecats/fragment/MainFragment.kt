package com.example.thecats.fragment

import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.thecats.App
import com.example.thecats.R
import com.example.thecats.adapter.CatsAdapter
import com.example.thecats.mvp.presenter.MainPresenter
import com.example.thecats.mvp.view.MVP
import com.example.thecats.repository.entity.favoriteentity.TheCat
import com.example.thecats.utils.*
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class MainFragment : Fragment(R.layout.fragment_main), MVP.MainView,
    CatsAdapter.AdapterCallback {

    companion object {
        fun getInstance(): MainFragment {
            return MainFragment()
        }
    }

    @Inject
    lateinit var presenter: MainPresenter
    private var adapter = CatsAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }

    override fun onRequestNextPage(page: Int) {
        presenter.loadPage(page)
    }

    override fun onItemLongClick(cat: TheCat) {
        showItemDialog(cat)
    }

    override fun onDataLoaded(data: List<TheCat>?) {
        adapter.setData(data?.toMutableList() ?: mutableListOf())
    }

    override fun onPageLoaded(data: List<TheCat>?) {
        adapter.addData(data)
    }

    override fun onError(error: Error) {
        context?.let { Toast.makeText(it, error.message, Toast.LENGTH_SHORT).show() }
    }

    override fun onLoadingStateChanged(isLoading: Boolean) {
        progress.visibility = if (isLoading) View.VISIBLE else View.GONE
        swipe.isRefreshing = isLoading
    }

    private fun init() {
        App.appComponent.inject(this)
        presenter.attachView(this)
        presenter.requestCats()
        swipe.setOnRefreshListener { presenter.loadCats() }
        recycler.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recycler.adapter = adapter
    }

    private fun showItemDialog(cat: TheCat) {
        AlertDialog.Builder(context)
            .setMessage(R.string.add_to_favorite)
            .setCancelable(true)
            .setPositiveButton(R.string.favorite) { dialog, _ ->
                dialog.dismiss()
                presenter.addFavorite(cat)
            }.setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
            .setNeutralButton(R.string.download) { dialog, _ ->
                dialog.dismiss()
                context?.let { it ->
                    if (checkDownloadPermissions(it)) {
                        onDownloadImage(it, Uri.parse(cat.url), cat.id)
                        Toast.makeText(it, R.string.downloading, Toast.LENGTH_SHORT).show()
                    } else {
                        onAskPermissions()
                    }
                }
            }.show()
    }

    private fun onAskPermissions() {
        activity?.let {
            requestDownloadPermissions(it)
            Toast.makeText(it, R.string.ask_permissions, Toast.LENGTH_SHORT).show()
        }
    }

    private fun onDownloadImage(context: Context, uri: Uri, name: String) {
        val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(uri)
            .addRequestHeader(HEADER_KEY, HEADER_API_KEY)
            .setTitle(name)
            .setDescription(context.getString(R.string.downloading_description))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        manager.enqueue(request)
    }
}