package com.example.myapplication

import android.content.ContentValues

data class Stock(
    var quantidade: Int,
    var idProduto: Long,
    var data: Long,
    var id: Long = -1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()
        valores.put(TabelaStock.CAMPO_QUANTIDADE, quantidade)
        valores.put(TabelaStock.CAMPO_ID_PRODUTO, idProduto)
        valores.put(TabelaStock.CAMPO_DATA_ATUALIZADA, data)
        return valores
    }
}