package com.chao.search.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.chao.search.api.model.ImageResult
import com.chao.search.databinding.CellImageLayoutBinding
import com.chao.search.ui.viewholder.ImageViewHolder
import com.chao.search.util.BaseListAdapter
import coil.load

class ViewModelAdapter: BaseListAdapter<ImageResult, ImageViewHolder>() {

    interface Listener {
        fun longClickListener(id: String, url: String?, title: String?)
    }

    var listener: Listener? = null

    fun setViewModels(viewModels: List<ImageResult>) {
        submitList(viewModels)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ImageViewHolder(CellImageLayoutBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = getItem(position) as ImageResult

        holder.binding.imageView.load(getUrl(item))
        holder.binding.title.text = item.title
        holder.binding.root.setOnLongClickListener {
            listener?.longClickListener(item.id, getUrl(item), item.title)
            true
        }
    }

    private fun getUrl(imageResult: ImageResult): String {
        return "https://farm${imageResult.farm}.staticflickr.com/${imageResult.server}/${imageResult.id}_${imageResult.secret}.jpg"
    }
}
