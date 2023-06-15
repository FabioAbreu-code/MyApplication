package com.example.myapplication

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaFornecedores (db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, $NOME_FORNECEDOR TEXT NOT NULL, $CONTACTO_FORNECEDOR  TEXT NOT NULL)")
    }

    companion object {
        const val NOME_TABELA = "fornecedor"

        const val NOME_FORNECEDOR = "nome_fornecedor"
        const val CONTACTO_FORNECEDOR = "contacto_fornecedor"

        val CAMPOS = arrayOf(BaseColumns._ID, NOME_FORNECEDOR, CONTACTO_FORNECEDOR)

    }
}