package com.example.myapplication

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.Calendar

data class Stock(
    var quantidade: Int,
    var fkProduto: Produtos,
    var data: Calendar,
    var id: Long = -1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()
        valores.put(TabelaStock.CAMPO_QUANTIDADE, quantidade)
        valores.put(TabelaStock.CAMPO_FK_PRODUTO, fkProduto.id)
        valores.put(TabelaStock.CAMPO_DATA_ATUALIZADA, data.timeInMillis)
        return valores
    }

    companion object{

        fun fromCursor(cursor: Cursor): Stock {
            val posQuantidade = cursor.getColumnIndex(TabelaStock.CAMPO_QUANTIDADE)
            val posFkProduto = cursor.getColumnIndex(TabelaStock.CAMPO_FK_PRODUTO)
            val posData = cursor.getColumnIndex(TabelaStock.CAMPO_DATA_ATUALIZADA)
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posDescProd = cursor.getColumnIndex(TabelaStock.CAMPO_DESC_PRODUTO)
            val posNomProd = cursor.getColumnIndex(TabelaStock.CAMPO_NOM_PRODUTO)

            val Quantidade = cursor.getInt(posQuantidade)
            val FkProduto = cursor.getLong(posFkProduto)
            val Data = Calendar.getInstance()
            Data.timeInMillis = cursor.getLong(posData)
            val nomProduto = cursor.getString(posNomProd)
            val desricaoProduto = cursor.getString(posDescProd)
            val Id = cursor.getLong(posId)

            return Stock(Quantidade,Produtos(desricaoProduto,nomProduto,FkProduto),Data,Id)
        }
    }
}