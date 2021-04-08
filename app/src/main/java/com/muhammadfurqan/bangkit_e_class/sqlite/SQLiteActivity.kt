package com.muhammadfurqan.bangkit_e_class.sqlite

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.muhammadfurqan.bangkit_e_class.R
import com.muhammadfurqan.bangkit_e_class.databinding.ActivitySqliteBinding
import com.muhammadfurqan.bangkit_e_class.sqlite.`interface`.BookInterface
import com.muhammadfurqan.bangkit_e_class.sqlite.adapter.SQLiteAdapter
import com.muhammadfurqan.bangkit_e_class.sqlite.db.MyBookDatabase
import kotlinx.coroutines.launch

class SQLiteActivity : AppCompatActivity(), BookInterface {

    // implement recyclerview to show the book list (only name)
    // the recyclerview data must be updated every time new book added
    // item must have edit function to change the book name
    // item must have delete function to delete book


    private lateinit var binding : ActivitySqliteBinding

    companion object{
        lateinit var itemAdapter : SQLiteAdapter
    }



    private val bookDatabase: MyBookDatabase by lazy {
        MyBookDatabase(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySqliteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
            onAdd()
        }

        binding.btnRead.setOnClickListener {
            onRead()
        }

        itemAdapter = SQLiteAdapter()
        itemAdapter.bookInterface = this

        binding.rvBooks.adapter = itemAdapter
        itemAdapter.submitList(bookDatabase.getAllBooks())
    }

    private fun onAdd() {
        bookDatabase.open()
        val bookName = binding.etBookName.text.toString()
        binding.etBookName.setText("")

        if (bookName.isNotEmpty()) {
            lifecycleScope.launch {
                bookDatabase.addBook(bookName)
                itemAdapter.submitList(bookDatabase.getAllBooks())
                itemAdapter.notifyDataSetChanged()
            }
        } else {
            Toast.makeText(this, "Please input the book name", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onRead() {
        val bookList = bookDatabase.getAllBooks()
        val stringOfBookList = bookList.joinToString(separator = "\n") {
            "Book ${it.id} is ${it.name}"
        }
        if (stringOfBookList.isEmpty()) {
            Toast.makeText(this, "No Books Available", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, stringOfBookList, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDelete(view: View, bookModel: BookModel) {
        val database = MyBookDatabase(this)
        database.deleteBookById(bookModel.id.toString())
        itemAdapter.submitList(bookDatabase.getAllBooks())
        itemAdapter.notifyDataSetChanged()
        database.close()
    }

    override fun onUpdate(view: View, bookModel: BookModel) {
        val intent = Intent(this, EditActivity::class.java)
        intent.putExtra(EditActivity.ID, bookModel.id.toString())
        startActivity(intent)
    }


}