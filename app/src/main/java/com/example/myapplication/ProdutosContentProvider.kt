package com.example.myapplication

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

class ProdutosContentProvider : ContentProvider() {

    private var bdOpenHelper : BdProductsOpenHelper? = null

    override fun onCreate(): Boolean {
        bdOpenHelper = BdProductsOpenHelper(context)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        TODO("Not yet implemented")
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }

    companion object {
        const val  AUTHORITY = "com.example.myapplication"
        const val PRODUTOS = "produtos"
        const val STOCK = "stock"

        private const val URI_PRODUTOS = 100
        private const val URI_STOCK = 200

        fun  uriMatcher() = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, PRODUTOS, URI_PRODUTOS)
            addURI(AUTHORITY, STOCK, URI_STOCK)
        }
    }
}