package com.sergokuzneczow.home.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import com.sergokuzneczow.domain.phone_mask_converter_case.ConverterToTimePatterCaseApi
import com.sergokuzneczow.home.R
import com.sergokuzneczow.home.databinding.RecyclerItemPostBinding
import com.sergokuzneczow.model.Post
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject


internal class PostsRecyclerAdapter @AssistedInject constructor(
    private val converterToTimePatterCaseApi: ConverterToTimePatterCaseApi,
    @Assisted private val onClick: (id: String) -> Unit,
) : ListAdapter<Post, PostViewHolder>(PostsRecyclerDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_post, parent, false)
        return PostViewHolder(view, onClick, converterToTimePatterCaseApi)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun onViewAttachedToWindow(holder: PostViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.attachToWindow()
    }

    override fun onViewDetachedFromWindow(holder: PostViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.detachFromWindow()
    }

    @AssistedFactory
    interface Factory {
        fun create(onClick: (id: String) -> Unit): PostsRecyclerAdapter
    }
}

internal class PostViewHolder(
    view: View,
    private val onClick: (id: String) -> Unit,
    private val converterToTimePatterCaseApi: ConverterToTimePatterCaseApi,
) : RecyclerView.ViewHolder(view) {

    private val binding = RecyclerItemPostBinding.bind(view)

    private var key: String? = null

    fun bind(data: Post) {
        key = data.id
        binding.tvTitle.text = data.title
        binding.tvContent.text = data.text.checkLength()
        binding.tvDate.text = converterToTimePatterCaseApi.execute(data.date)
        binding.containerRecyclerItemPost.setOnClickListener { onClick.invoke(data.id) }
        binding.ivPreview.load(data.imageUrl) {
            crossfade(true)
        }
    }

    fun attachToWindow() {
        key?.let { key -> binding.containerRecyclerItemPost.setOnClickListener { onClick.invoke(key) } }
    }

    fun detachFromWindow() {
        binding.containerRecyclerItemPost.setOnClickListener(null)
    }

    private fun String.checkLength(): String {
        return if (this.length > 80) "${this.substring(0, 79)}..." else this
    }
}

internal class PostsRecyclerDiffUtil() : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean = newItem.id == oldItem.id
    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean = newItem == oldItem
}