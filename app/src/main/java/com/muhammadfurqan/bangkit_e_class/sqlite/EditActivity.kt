package com.muhammadfurqan.bangkit_e_class.sqlite

import android.content.ContentValues
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.muhammadfurqan.bangkit_e_class.databinding.ActivityEditBinding
import com.muhammadfurqan.bangkit_e_class.sqlite.db.MyBookDatabase
import com.muhammadfurqan.bangkit_e_class.sqlite.db.MyBookOpenHelper.Companion.FIELD_NAME

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding

    companion object {
        const val ID = "id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnUpdate.setOnClickListener {
            val bookName = binding.etBookName.text.toString().trim()

            val values = ContentValues()
            values.put(FIELD_NAME, bookName)

            val id = intent.getStringExtra(ID)

            val database = MyBookDatabase(this)
            database.updateById(id, values)
            SQLiteActivity.itemAdapter.submitList(database.getAllBooks())
            SQLiteActivity.itemAdapter.notifyDataSetChanged()
            database.close()
            finish()
        }
    }
}