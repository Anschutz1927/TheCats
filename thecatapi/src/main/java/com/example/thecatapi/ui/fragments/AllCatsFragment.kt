package com.example.thecatapi.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thecatapi.App
import com.example.thecatapi.R
import com.example.thecatapi.databinding.DialogItemClickedBinding
import com.example.thecatapi.databinding.FragmentAllCatsBinding
import com.example.thecatapi.mvp.models.entities.Cat
import com.example.thecatapi.mvp.presenters.AllCatsPresenter
import com.example.thecatapi.mvp.views.MvpView
import com.example.thecatapi.ui.adapters.RecyclerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import javax.inject.Inject

class AllCatsFragment : BaseFragment(R.layout.fragment_all_cats), MvpView.AllCatsView {

    @Inject
    lateinit var presenter: AllCatsPresenter
    private lateinit var binding: FragmentAllCatsBinding
    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAllCatsBinding.bind(view)
        adapter = RecyclerAdapter({ presenter.requestCats(it) }, { presenter.onItemLonClick(it) })
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        presenter.attachView(this)
        savedInstanceState?.let { adapter.onRestoreInstanceState(it) }
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        adapter.onSaveInstanceState(outState)
    }

    override fun onItemClicked(cat: Cat) {
        context?.let { showBottomDialog(it, cat) }
    }

    override fun onCats(cats: LiveData<List<Cat>>) {
        cats.observe(viewLifecycleOwner) {
            adapter.cats = it
            if (it.isEmpty()) {
                binding.textNotification.visibility = View.VISIBLE
                presenter.requestCats(0)
            } else {
                binding.textNotification.visibility = View.GONE
            }
        }
    }

    override fun showLoader(isShow: Boolean) {
        if (isShow) binding.progress.visibility = View.VISIBLE
        else binding.progress.visibility = View.GONE
    }

    private fun showBottomDialog(context: Context, cat: Cat) = BottomSheetDialog(context).apply {
        setContentView(DialogItemClickedBinding.inflate(layoutInflater).apply {
            buttonFavorite.setOnClickListener {
                presenter.addFavorite(cat)
                dismiss()
            }
            buttonDownload.setOnClickListener {
                presenter.onDownloadClicked(cat)
                dismiss()
            }
            buttonCancel.setOnClickListener { dismiss() }
        }.root)
    }.show()
}