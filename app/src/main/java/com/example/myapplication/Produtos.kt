package com.example.myapplication

import android.content.ContentValues

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
}