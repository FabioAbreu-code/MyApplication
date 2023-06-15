package com.example.myapplication

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Fornecedores(
    var nome_fornecedor: String,
    var contacto_fornecedor: String,
    var id: Long = -1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaFornecedores.NOME_FORNECEDOR, nome_fornecedor)
        valores.put(TabelaFornecedores.CONTACTO_FORNECEDOR, contacto_fornecedor)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor) : Fornecedores {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNomeFornecedor = cursor.getColumnIndex(TabelaFornecedores.NOME_FORNECEDOR)
            val posContactoFornecedor = cursor.getColumnIndex(TabelaFornecedores.CONTACTO_FORNECEDOR)

            val id = cursor.getLong(posId)
            val NomeFornecedor = cursor.getString(posNomeFornecedor)
            val ContactoFornecedor = cursor.getString(posContactoFornecedor)

            return Fornecedores(NomeFornecedor, ContactoFornecedor, id)
        }
    }
}