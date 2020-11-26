package com.example.thecatapi.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thecatapi.databinding.ItemPagerBinding
import com.example.thecatapi.mvp.models.entities.Cat

class PagerAdapter(
    private val removeCallback: (cat: Cat) -> Unit,
    private val downloadCallback: (cat: Cat) -> Unit
) : BaseAdapter<PagerAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder = Holder(
        ItemPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    ).apply {
        binding.buttonRemove.setOnClickListener { removeCallback(cats[adapterPosition]) }
        binding.buttonDownload.setOnClickListener { downloadCallback(cats[adapterPosition]) }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        Glide.with(holder.binding.image)
            .load(cats[position].url)
            .apply(glideOptions)
            .into(holder.binding.image)
    }

    class Holder(val binding: ItemPagerBinding) : RecyclerView.ViewHolder(binding.root)
}