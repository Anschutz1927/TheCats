package com.example.thecatapi.ui.adapters

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.thecatapi.mvp.models.entities.Cat

abstract class BaseAdapter<Holder : RecyclerView.ViewHolder> : RecyclerView.Adapter<Holder>() {

    protected val glideOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        .priority(Priority.IMMEDIATE)

    open var cats: List<Cat> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = cats.size

    open fun onSaveInstanceState(outState: Bundle) {}

    open fun onRestoreInstanceState(savedInstanceState: Bundle) {}
}