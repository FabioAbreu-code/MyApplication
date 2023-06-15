package com.example.myapplication

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

class ProdutoContentProvider : ContentProvider() {

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

            URI_FORNECEDOR, URI_FORNECEDOR_ID -> TabelaFornecedores(bd)
            URI_PRODUTO2, URI_PRODUTO2_ID -> Tabela2Produtos(bd)

            else -> null
        }

        val (selecao, argsSel) = when(endereco){
            URI_PRODUTOS_ID, URI_STOCK_ID, URI_FORNECEDOR_ID, URI_PRODUTO2_ID -> Pair("${BaseColumns._ID}=?", arrayOf(id))
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

            URI_FORNECEDOR_ID -> "vnd.android.cursor.dir/$FORNECEDOR"
            URI_FORNECEDOR -> "vnd.android.cursor.item/$FORNECEDOR"
            URI_PRODUTO2_ID -> "vnd.android.cursor.dir/$PRODUTO2"
            URI_PRODUTO2 -> "vnd.android.cursor.item/$PRODUTO2"
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val bd = bdOpenHelper!!.writableDatabase
        val endereco = uriMatcher().match(uri)

        val tabela = when(endereco){
            URI_PRODUTOS -> TabelaProdutos(bd)
            URI_STOCK -> TabelaStock(bd)
            URI_FORNECEDOR -> TabelaFornecedores(bd)
            URI_PRODUTO2 -> Tabela2Produtos(bd)
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
            URI_FORNECEDOR_ID -> TabelaFornecedores(bd)
            URI_PRODUTO2_ID -> Tabela2Produtos(bd)
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
            URI_FORNECEDOR_ID -> TabelaFornecedores(bd)
            URI_PRODUTO2_ID -> Tabela2Produtos(bd)
            else -> return 0
        }

        val id = uri.lastPathSegment!!
        return tabela.altera(values!!,"${BaseColumns._ID}=?", arrayOf(id))

    }

    companion object {
        private const val  AUTHORITY = "com.example.myapplication"
        private const val PRODUTOS = "produtos"
        private const val STOCK = "stock"

        private const val FORNECEDOR = "fornecedor"
        private const val PRODUTO2 = "produto2"

        private const val URI_PRODUTOS = 100
        private const val URI_PRODUTOS_ID = 101
        private const val URI_STOCK = 200
        private const val URI_STOCK_ID = 201

        private const val URI_FORNECEDOR = 300
        private const val URI_FORNECEDOR_ID = 301
        private const val URI_PRODUTO2 = 400
        private const val URI_PRODUTO2_ID = 401

        private val ENDERECO_BASE = Uri.parse("content://$AUTHORITY")

        val ENDERECO_STOCK = Uri.withAppendedPath(ENDERECO_BASE, STOCK)
        val ENDERECO_PRODUTOS = Uri.withAppendedPath(ENDERECO_BASE, PRODUTOS)

        val ENDERECO_FORNECEDOR = Uri.withAppendedPath(ENDERECO_BASE, FORNECEDOR)
        val ENDERECO_PRODUTO2 = Uri.withAppendedPath(ENDERECO_BASE, PRODUTO2)

        fun  uriMatcher() = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, PRODUTOS, URI_PRODUTOS)
            addURI(AUTHORITY, "$PRODUTOS/#", URI_PRODUTOS_ID)
            addURI(AUTHORITY, STOCK, URI_STOCK)
            addURI(AUTHORITY, "$STOCK/#", URI_STOCK_ID)

            addURI(AUTHORITY, FORNECEDOR, URI_FORNECEDOR)
            addURI(AUTHORITY, "$FORNECEDOR/#", URI_FORNECEDOR_ID)
            addURI(AUTHORITY, PRODUTO2, URI_PRODUTO2)
            addURI(AUTHORITY, "$PRODUTO2/#", URI_PRODUTO2_ID)

        }
    }
}