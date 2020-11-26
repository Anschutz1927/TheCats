package com.example.thecatapi.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import com.example.thecatapi.App
import com.example.thecatapi.R
import com.example.thecatapi.databinding.FragmentFavoriteCatsBinding
import com.example.thecatapi.mvp.models.entities.Cat
import com.example.thecatapi.mvp.presenters.FavoriteCatsPresenter
import com.example.thecatapi.mvp.views.MvpView
import com.example.thecatapi.ui.adapters.PagerAdapter
import javax.inject.Inject

const val PAGE_ITEM = "PAGE_ITEM"

class FavoriteCatsFragment : BaseFragment(R.layout.fragment_favorite_cats),
    MvpView.FavoriteCatsView {

    @Inject
    lateinit var presenter: FavoriteCatsPresenter
    private lateinit var binding: FragmentFavoriteCatsBinding
    private lateinit var adapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteCatsBinding.bind(view)
        adapter = PagerAdapter(
            { presenter.removeFromFavorites(it) },
            { presenter.onDownloadClicked(it) }
        )
        binding.pager.adapter = adapter
        presenter.attachView(this)
        binding.pager.run {
            post { currentItem = savedInstanceState?.getInt(PAGE_ITEM, 0) ?: 0 }
        }
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(PAGE_ITEM, binding.pager.currentItem)
    }

    override fun onCats(cats: LiveData<List<Cat>>) = cats.observe(viewLifecycleOwner) {
        adapter.cats = it
        if (it.isEmpty()) binding.textNotification.visibility = View.VISIBLE
        else binding.textNotification.visibility = View.GONE
    }

    override fun showLoader(isShow: Boolean) {
        if (isShow) binding.progress.visibility = View.VISIBLE
        else binding.progress.visibility = View.GONE
    }
}