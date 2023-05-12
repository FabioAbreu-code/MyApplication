package com.example.myapplication

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Produtos(
    var descricao: String,
    var nome: String,
    var id: Long = -1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()
        valores.put(TabelaProdutos.CAMPO_DESCRICAO_PRODUTO, descricao)
        valores.put(TabelaProdutos.CAMPO_NOME, nome)
        return valores
    }

    companion object{

        fun fromCursor(cursor: Cursor): Produtos {
            val posDescricao = cursor.getColumnIndex(TabelaProdutos.CAMPO_DESCRICAO_PRODUTO)
            val posNome = cursor.getColumnIndex(TabelaProdutos.CAMPO_NOME)
            val posId = cursor.getColumnIndex(BaseColumns._ID)

            val Descricao = cursor.getString(posDescricao)
            val Nome = cursor.getString(posNome)
            val Id = cursor.getLong(posId)

            return Produtos(Descricao,Nome,Id)
        }
    }
}