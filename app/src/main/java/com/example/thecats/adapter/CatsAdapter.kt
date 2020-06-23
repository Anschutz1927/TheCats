package com.example.thecats.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.thecats.GlideApp
import com.example.thecats.R
import com.example.thecats.repository.entity.favoriteentity.TheCat
import com.example.thecats.utils.ITEMS_PER_PAGE
import com.example.thecats.utils.SCAN_IF_LOWER_ITEMS
import kotlinx.android.synthetic.main.item_cat_info.view.*

class CatsAdapter(private val callback: AdapterCallback) :
    RecyclerView.Adapter<CatsAdapter.Holder>() {

    private var data: MutableList<TheCat> = mutableListOf()
    private var nextPageToLoad = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val holder = Holder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_cat_info, parent, false)
        )
        holder.itemView.setOnLongClickListener {
            callback.onItemLongClick(data[holder.adapterPosition])
            return@setOnLongClickListener true
        }
        return holder
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        checkForRequestNextPage(position)
        val item = data[position]
        val image = holder.itemView.image
        GlideApp.with(image)
            .load(item.url)
            .error(R.drawable.ic_baseline_error_outline_24)
            .transform(RoundedCorners(24))
            .addListener(OnImageLoadedListener {
                if (holder.adapterPosition == position) {
                    onResizeImageView(image, item.width, item.height)
                }
            }).into(image)
    }

    fun setData(data: MutableList<TheCat>) {
        nextPageToLoad = 1
        this.data = data
        notifyDataSetChanged()
    }

    fun addData(data: List<TheCat>?) {
        data?.let {
            nextPageToLoad++
            this.data.addAll(data)
            val commonSize = this.data.size
            notifyItemRangeInserted(commonSize - data.size, commonSize)
        }
    }

    private fun onResizeImageView(image: ImageView, width: Int, height: Int) {
        val heightRatio: Float = height / width.toFloat()
        image.layoutParams.height = (image.width * heightRatio).toInt()
    }

    private fun checkForRequestNextPage(position: Int) {
        if ((ITEMS_PER_PAGE * nextPageToLoad - SCAN_IF_LOWER_ITEMS) == position) {
            callback.onRequestNextPage(nextPageToLoad)
        }
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view)

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

    interface AdapterCallback {
        fun onRequestNextPage(page: Int)
        fun onItemLongClick(cat: TheCat)
    }
}
