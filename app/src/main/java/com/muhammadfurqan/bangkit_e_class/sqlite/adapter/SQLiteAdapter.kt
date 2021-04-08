package com.muhammadfurqan.bangkit_e_class.sqlite.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.muhammadfurqan.bangkit_e_class.databinding.SqliteItemBinding
import com.muhammadfurqan.bangkit_e_class.sqlite.BookModel
import com.muhammadfurqan.bangkit_e_class.sqlite.`interface`.BookInterface

class SQLiteAdapter: ListAdapter<BookModel, SQLiteAdapter.ViewHolder>(DiffCallback()) {

    var bookInterface : BookInterface? = null

    inner class ViewHolder(private val binding : SqliteItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(bookModel: BookModel, bookInterface: BookInterface?){
            binding.tvBookTitle.text = bookModel.name

            binding.btnUpdate.setOnClickListener {
                bookInterface?.onUpdate(binding.root, bookModel)
            }
            binding.btnDelete.setOnClickListener {
                bookInterface?.onDelete(binding.root, bookModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        SqliteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), bookInterface)
    }
}

class DiffCallback : DiffUtil.ItemCallback<BookModel>() {
    override fun areItemsTheSame(oldItem: BookModel, newItem: BookModel): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: BookModel, newItem: BookModel): Boolean =
        oldItem.name == newItem.name
}

