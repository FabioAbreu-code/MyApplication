package com.example.myapplication

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

class Fornecedores(
    var nome: String,
    var endereco: String,
    var contacto: String,
    var id: Long = -1
){
    fun toContentValues(): ContentValues {
        val valores = ContentValues()

        valores.put(TabelaFornecedor.CAMPO_NOME_FORNECEDOR, nome)
        valores.put(TabelaFornecedor.CAMPO_ENDERECO, endereco)
        valores.put(TabelaFornecedor.CAMPO_CONTACTO, contacto)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Fornecedores {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaFornecedor.CAMPO_NOME_FORNECEDOR)
            val posEndereco = cursor.getColumnIndex(TabelaFornecedor.CAMPO_ENDERECO)
            val posContacto = cursor.getColumnIndex(TabelaFornecedor.CAMPO_CONTACTO)

            val id = cursor.getLong(posId)
            val Nome = cursor.getString(posNome)
            val Endereco = cursor.getString(posEndereco)
            val Contacto = cursor.getString(posContacto)

            return Fornecedores(Nome, Endereco, Contacto, id)
        }
    }
}