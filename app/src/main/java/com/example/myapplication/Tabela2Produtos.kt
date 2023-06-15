package com.example.myapplication

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns

class Tabela2Produtos (db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, $CAMPO_NOME_DO_PRODUTO TEXT NOT NULL, $CAMPO_DESC_DO_PRODUTO TEXT NOT NULL, $CAMPO_FK_FORNECEDOR INTEGER NOT NULL, FOREIGN KEY($CAMPO_FK_FORNECEDOR) REFERENCES ${TabelaFornecedores.NOME_TABELA}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }

    companion object {
        const val NOME_TABELA = "produtos_2"

        const val CAMPO_NOME_DO_PRODUTO = "nome_do_produto"
        const val CAMPO_DESC_DO_PRODUTO = "desc_do_produto"
        const val CAMPO_FK_FORNECEDOR = "id_do_fornecedor"

        val CAMPOS = arrayOf(
            BaseColumns._ID,
            CAMPO_NOME_DO_PRODUTO,
            CAMPO_DESC_DO_PRODUTO,
            CAMPO_FK_FORNECEDOR
        )

    }
}