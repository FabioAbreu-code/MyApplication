package com.example.myapplication

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns


class TabelaStock(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, $CAMPO_QUANTIDADE INTEGER NOT NULL, $CAMPO_DATA_ATUALIZADA DATE NOT NULL, $CAMPO_FK_PRODUTO INTEGER NOT NULL, FOREIGN KEY($CAMPO_FK_PRODUTO) REFERENCES ${TabelaProdutos.NOME_TABELA}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }

    companion object {
        const val NOME_TABELA = "stock"
        const val CAMPO_QUANTIDADE = "quantidade"
        const val CAMPO_DATA_ATUALIZADA = "data"
        const val CAMPO_FK_PRODUTO = "id_produto"

        val CAMPOS = arrayOf(BaseColumns._ID, CAMPO_QUANTIDADE, CAMPO_DATA_ATUALIZADA, CAMPO_FK_PRODUTO)
    }
}