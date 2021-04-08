package com.muhammadfurqan.bangkit_e_class.sqlite.`interface`

import android.view.View
import com.muhammadfurqan.bangkit_e_class.sqlite.BookModel

interface BookInterface {
    fun onDelete(view: View, bookModel: BookModel)

    fun onUpdate(view: View, bookModel: BookModel)
}