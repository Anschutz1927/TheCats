package com.example.thecatapi.ui.adapters

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.thecatapi.databinding.ItemRecyclerBinding
import com.example.thecatapi.mvp.models.entities.Cat
import com.example.thecatapi.network.ITEMS_PER_PAGE

private const val SCAN_IF_LOWER_ITEMS = 20
private const val NEXT_PAGE_TO_LOAD_KEY = "NEXT_PAGE_TO_LOAD_KEY"

class RecyclerAdapter(val onNextPage: (page: Int) -> Unit, val onLongClick: (cat: Cat) -> Boolean) :
    BaseAdapter<RecyclerAdapter.Holder>() {

    private var nextPageToLoad = 1

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        nextPageToLoad = savedInstanceState.getInt(NEXT_PAGE_TO_LOAD_KEY, 1)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(NEXT_PAGE_TO_LOAD_KEY, nextPageToLoad)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder = Holder(
        ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    ).apply { binding.root.setOnLongClickListener { onLongClick(cats[adapterPosition]) } }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        checkForRequestNextPage(position)
        val item = cats[position]
        Glide.with(holder.binding.image)
            .load(item.url)
            .transform(RoundedCorners(24))
            .apply(glideOptions)
            .addListener(OnImageLoadedListener {
                if (holder.adapterPosition == position) {
                    onResizeImageView(holder.binding.image, item.width, item.height)
                }
            }).into(holder.binding.image)
    }

    private fun onResizeImageView(image: ImageView, width: Int, height: Int) {
        val heightRatio: Float = height / width.toFloat()
        image.layoutParams.height = (image.width * heightRatio).toInt()
    }

    private fun checkForRequestNextPage(position: Int) {
        if ((ITEMS_PER_PAGE * nextPageToLoad - SCAN_IF_LOWER_ITEMS) == position) {
            onNextPage(nextPageToLoad++)
        }
    }

    class Holder(val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root)

    class OnImageLoadedListener(private val body: () -> Any) : RequestListener<Drawable> {

        override fun onLoadFailed(
            e: GlideException?, model: Any?,
            target: Target<Drawable>?, isFirstResource: Boolean
        ): Boolean {
            return false
        }

        override fun onResourceReady(
            resource: Drawable?, model: Any?, target: Target<Drawable>?,
            dataSource: DataSource?, isFirstResource: Boolean
        ): Boolean {
            body()
            return false
        }
    }
}