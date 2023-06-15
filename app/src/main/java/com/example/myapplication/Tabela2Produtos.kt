package com.example.myapplication

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns

class Tabela2Produtos (db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, $CAMPO_NOME_DO_PRODUTO TEXT NOT NULL, $CAMPO_DESC_DO_PRODUTO TEXT NOT NULL, $CAMPO_FK_FORNECEDOR INTEGER NOT NULL, FOREIGN KEY($CAMPO_FK_FORNECEDOR) REFERENCES ${TabelaFornecedores.NOME_TABELA}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }

    override fun consulta(
        colunas: Array<String>,
        selecao: String?,
        argsSelecao: Array<String>?,
        groupby: String?,
        having: String?,
        orderby: String?
    ): Cursor {
        val sql = SQLiteQueryBuilder()
        sql.tables = "$NOME_TABELA INNER JOIN ${TabelaFornecedores.NOME_TABELA} ON ${TabelaFornecedores.CAMPO_ID}=$CAMPO_FK_FORNECEDOR"

        return sql.query(db, colunas, selecao, argsSelecao, groupby, having, orderby)
    }

    companion object {
        const val NOME_TABELA = "produtos_2"
        const val CAMPO_ID = "$NOME_TABELA.${BaseColumns._ID}"

        const val CAMPO_NOME_DO_PRODUTO = "nome_do_produto"
        const val CAMPO_DESC_DO_PRODUTO = "desc_do_produto"
        const val CAMPO_FK_FORNECEDOR = "id_do_fornecedor"
        const val CAMPO_NOM_FORNECEDOR = TabelaFornecedores.NOME_FORNECEDOR
        const val CAMPO_CONTACTO_FORNECEDOR = TabelaFornecedores.NOME_FORNECEDOR


        val CAMPOS = arrayOf(
            CAMPO_ID,
            CAMPO_NOME_DO_PRODUTO,
            CAMPO_DESC_DO_PRODUTO,
            CAMPO_FK_FORNECEDOR,
            CAMPO_NOM_FORNECEDOR,
            CAMPO_CONTACTO_FORNECEDOR
        )

    }
}