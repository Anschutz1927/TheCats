package com.example.thecats.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.thecats.App
import com.example.thecats.R
import com.example.thecats.adapter.CatsAdapter
import com.example.thecats.mvp.presenter.FavoritePresenter
import com.example.thecats.mvp.view.MVP
import com.example.thecats.repository.entity.favoriteentity.TheCat
import com.example.thecats.utils.Error
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class FavoriteFragment : Fragment(R.layout.fragment_main), MVP.FavoriteView,
    CatsAdapter.AdapterCallback {

    companion object {
        fun getInstance(): FavoriteFragment {
            return FavoriteFragment()
        }
    }

    @Inject
    lateinit var presenter: FavoritePresenter
    private var adapter = CatsAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        App.appComponent.inject(this)
        presenter.attachView(this)
        swipe.isEnabled = false
        recycler.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recycler.adapter = adapter
        presenter.observeFavorites()
    }

    override fun onLoadingStateChanged(isLoading: Boolean) {
        progress.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDataLoaded(data: List<TheCat>?) {
        adapter.setData(data?.toMutableList() ?: mutableListOf())
    }

    override fun onError(error: Error) {
        context?.let { Toast.makeText(it, error.message, Toast.LENGTH_SHORT).show() }
    }

    override fun onRequestNextPage(page: Int) {
        //ignored
    }

    override fun onItemLongClick(cat: TheCat) {
        showItemDialog(cat)
    }

    private fun showItemDialog(cat: TheCat) {
        AlertDialog.Builder(context)
            .setMessage(R.string.add_to_favorite)
            .setCancelable(true)
            .setPositiveButton(R.string.remove) { dialog, _ ->
                dialog.dismiss()
                presenter.removeFavorite(cat)
            }.setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
            .show()
    }
}