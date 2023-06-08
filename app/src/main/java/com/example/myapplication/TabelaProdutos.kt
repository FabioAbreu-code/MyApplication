package com.example.myapplication

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns


class TabelaProdutos(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, $CAMPO_NOME TEXT NOT NULL, $CAMPO_DESCRICAO_PRODUTO TEXT NOT NULL)")
    }

    companion object {
        const val NOME_TABELA = "produtos"
        const val CAMPO_ID = "$NOME_TABELA.${BaseColumns._ID}"

        const val CAMPO_NOME = "nome"
        const val CAMPO_DESCRICAO_PRODUTO = "descricao_do_produto"

        val CAMPOS = arrayOf(BaseColumns._ID, CAMPO_NOME, CAMPO_DESCRICAO_PRODUTO)

    }

}
