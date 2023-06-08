package com.example.myapplication

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns


class TabelaStock(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, $CAMPO_QUANTIDADE INTEGER NOT NULL, $CAMPO_DATA_ATUALIZADA INTEGER NOT NULL, $CAMPO_FK_PRODUTO INTEGER NOT NULL, FOREIGN KEY($CAMPO_FK_PRODUTO) REFERENCES ${TabelaProdutos.NOME_TABELA}(${BaseColumns._ID}) ON DELETE RESTRICT)")
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
        sql.tables = "$NOME_TABELA INNER JOIN ${TabelaProdutos.NOME_TABELA} ON ${TabelaProdutos.CAMPO_ID}=$CAMPO_FK_PRODUTO"

        return sql.query(db, colunas, selecao, argsSelecao, groupby, having, orderby)
    }


    companion object {
        const val NOME_TABELA = "stock"
        const val CAMPO_ID = "$NOME_TABELA.${BaseColumns._ID}"

        const val CAMPO_QUANTIDADE = "quantidade"
        const val CAMPO_DATA_ATUALIZADA = "data"
        const val CAMPO_FK_PRODUTO = "id_produto"
        const val CAMPO_DESC_PRODUTO = TabelaProdutos.CAMPO_DESCRICAO_PRODUTO
        const val CAMPO_NOM_PRODUTO = TabelaProdutos.CAMPO_NOME

        val CAMPOS = arrayOf(CAMPO_ID, CAMPO_QUANTIDADE, CAMPO_DATA_ATUALIZADA, CAMPO_FK_PRODUTO, CAMPO_DESC_PRODUTO, CAMPO_NOM_PRODUTO)
    }
}