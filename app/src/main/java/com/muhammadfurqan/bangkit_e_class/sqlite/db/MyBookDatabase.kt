package com.muhammadfurqan.bangkit_e_class.sqlite.db

import android.content.ContentValues
import android.content.Context
import com.muhammadfurqan.bangkit_e_class.sqlite.BookModel

/**
 * @author by furqan on 08/04/2021
 */
class MyBookDatabase(context: Context) {

    private val openHelper: MyBookOpenHelper by lazy {
        MyBookOpenHelper(context)
    }

    private val readableDb by lazy {
        openHelper.readableDatabase
    }

    public fun open() {
        openHelper.writableDatabase
    }

    public fun close() {
        openHelper.writableDatabase.close()
    }

    fun addBook(name: String) {
        val writeableDb = openHelper.writableDatabase

        val values = ContentValues()
        values.put(MyBookOpenHelper.FIELD_NAME, name)

        writeableDb.insert(MyBookOpenHelper.TABLE_BOOK, null, values)
    }

    // create update function

    // create delete function

    fun getAllBooks(): List<BookModel> {
        val bookList: MutableList<BookModel> = mutableListOf()

        val cursor = readableDb.rawQuery(
            "SELECT * FROM ${MyBookOpenHelper.TABLE_BOOK}",
            null
        )

        cursor?.apply {
            while (moveToNext()) {
                val book = BookModel(
                    getInt(getColumnIndexOrThrow(MyBookOpenHelper.FIELD_ID)),
                    getString(getColumnIndexOrThrow(MyBookOpenHelper.FIELD_NAME))
                )
                bookList.add(book)
            }
        }

        return bookList
    }


    public fun deleteBookById(id: String): Int {
        val database = openHelper.writableDatabase
        return database.delete(MyBookOpenHelper.TABLE_BOOK, "id = $id", null)
    }

    public fun updateById(id: String, values: ContentValues?): Int {
        val database = openHelper.writableDatabase
        return database.update(MyBookOpenHelper.TABLE_BOOK, values, "id = ?", arrayOf(id))
    }

}