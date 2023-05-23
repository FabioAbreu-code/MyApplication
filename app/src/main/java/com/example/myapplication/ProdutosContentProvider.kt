package com.example.myapplication

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

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
        val bd = bdOpenHelper!!.readableDatabase
        val id = uri.lastPathSegment

        val endereco = uriMatcher().match(uri)

        val tabela = when(endereco){
            URI_PRODUTOS, URI_PRODUTOS_ID -> TabelaProdutos(bd)
            URI_STOCK, URI_STOCK_ID -> TabelaStock(bd)
            else -> null
        }

        val (selecao, argsSel) = when(endereco){
            URI_PRODUTOS_ID, URI_STOCK_ID -> Pair("${BaseColumns._ID}=?", arrayOf(id))
            else -> Pair(selection,  selectionArgs)
        }

        return tabela?.consulta(
                projection as Array<String>,
                selecao,
                argsSel as Array<String>?,
                null,
                null,
                sortOrder)

    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val bd = bdOpenHelper!!.writableDatabase
        val endereco = uriMatcher().match(uri)

        val tabela = when(endereco){
            URI_PRODUTOS -> TabelaProdutos(bd)
            URI_STOCK -> TabelaStock(bd)
            else -> return null
        }

        val id = tabela.insere(values!!)

        if(id == -1L ) return null

        return  Uri.withAppendedPath(uri, id.toString())
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
        private const val URI_PRODUTOS_ID = 101
        private const val URI_STOCK = 200
        private const val URI_STOCK_ID = 201

        fun  uriMatcher() = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, PRODUTOS, URI_PRODUTOS)
            addURI(AUTHORITY, "$PRODUTOS/#", URI_PRODUTOS_ID)
            addURI(AUTHORITY, STOCK, URI_STOCK)
            addURI(AUTHORITY, "$STOCK/#", URI_STOCK_ID)

        }
    }
}