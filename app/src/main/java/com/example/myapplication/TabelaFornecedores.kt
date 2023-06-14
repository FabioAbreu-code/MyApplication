package com.example.myapplication

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaFornecedor(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, $CAMPO_NOME_FORNECEDOR TEXT NOT NULL,$CAMPO_ENDERECO TEXT NOT NULL,$CAMPO_CONTACTO TEXT NOT NULL)")
    }

    companion object {
        const val NOME_TABELA = "Fornecedor"
        const val CAMPO_ID = "$NOME_TABELA.${BaseColumns._ID}"
        const val CAMPO_NOME_FORNECEDOR = "nome_fornecedor"
        const val CAMPO_ENDERECO = "endereco_fornecedor"
        const val CAMPO_CONTACTO = "contacto_fornecedor"

        val CAMPOS = arrayOf(CAMPO_ID, CAMPO_NOME_FORNECEDOR,
            CAMPO_ENDERECO,CAMPO_CONTACTO)
    }
}