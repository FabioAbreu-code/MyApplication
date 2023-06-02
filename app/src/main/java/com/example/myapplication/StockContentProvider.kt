package com.example.myapplication

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

class StockContentProvider : ContentProvider() {

    private var bdOpenHelper : BdStockOpenHelper? = null

    override fun onCreate(): Boolean {
        bdOpenHelper = BdStockOpenHelper(context)
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
        val endereco = uriMatcher().match(uri)

        return when(endereco){

            URI_PRODUTOS_ID -> "vnd.android.cursor.dir/$PRODUTOS"
            URI_PRODUTOS -> "vnd.android.cursor.item/$PRODUTOS"
            URI_STOCK_ID -> "vnd.android.cursor.dir/$STOCK"
            URI_STOCK -> "vnd.android.cursor.item/$STOCK"
            else -> null
        }
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
        val bd = bdOpenHelper!!.writableDatabase
        val endereco = uriMatcher().match(uri)

        val tabela = when(endereco){
            URI_PRODUTOS_ID -> TabelaProdutos(bd)
            URI_STOCK_ID -> TabelaStock(bd)
            else -> return 0
        }

        val id = uri.lastPathSegment!!
        return tabela.elimina("${BaseColumns._ID}=?", arrayOf(id))
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        val bd = bdOpenHelper!!.writableDatabase
        val endereco = uriMatcher().match(uri)

        val tabela = when(endereco){
            URI_PRODUTOS_ID -> TabelaProdutos(bd)
            URI_STOCK_ID -> TabelaStock(bd)
            else -> return 0
        }

        val id = uri.lastPathSegment!!
        return tabela.altera(values!!,"${BaseColumns._ID}=?", arrayOf(id))

    }

    companion object {
        private const val  AUTHORITY = "com.example.myapplication"
        private const val PRODUTOS = "produtos"
        private const val STOCK = "stock"

        private const val URI_PRODUTOS = 100
        private const val URI_PRODUTOS_ID = 101
        private const val URI_STOCK = 200
        private const val URI_STOCK_ID = 201

        private val ENDERECO_BASE = Uri.parse("content://$AUTHORITY")

        val ENDERECO_STOCK = Uri.withAppendedPath(ENDERECO_BASE, STOCK)
        val ENDERECO_PRODUTOS = Uri.withAppendedPath(ENDERECO_BASE, PRODUTOS)

        fun  uriMatcher() = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, PRODUTOS, URI_PRODUTOS)
            addURI(AUTHORITY, "$PRODUTOS/#", URI_PRODUTOS_ID)
            addURI(AUTHORITY, STOCK, URI_STOCK)
            addURI(AUTHORITY, "$STOCK/#", URI_STOCK_ID)

        }
    }
}