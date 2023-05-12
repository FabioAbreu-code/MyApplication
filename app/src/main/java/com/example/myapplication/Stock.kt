package com.example.myapplication

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Stock(
    var quantidade: Int,
    var fkProduto: Long,
    var data: Long,
    var id: Long = -1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()
        valores.put(TabelaStock.CAMPO_QUANTIDADE, quantidade)
        valores.put(TabelaStock.CAMPO_FK_PRODUTO, fkProduto)
        valores.put(TabelaStock.CAMPO_DATA_ATUALIZADA, data)
        return valores
    }

    companion object{

        fun fromCursor(cursor: Cursor): Stock {
            val posQuantidade = cursor.getColumnIndex(TabelaStock.CAMPO_QUANTIDADE)
            val posFkProduto = cursor.getColumnIndex(TabelaStock.CAMPO_FK_PRODUTO)
            val posData = cursor.getColumnIndex(TabelaStock.CAMPO_DATA_ATUALIZADA)
            val posId = cursor.getColumnIndex(BaseColumns._ID)

            val Quantidade = cursor.getInt(posQuantidade)
            val FkProduto = cursor.getLong(posFkProduto)
            val Data = cursor.getLong(posData)
            val Id = cursor.getLong(posId)

            return Stock(Quantidade,FkProduto,Data,Id)
        }
    }
}