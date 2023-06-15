package com.example.myapplication
import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable
import java.util.Calendar

data class Produtos2(
    var nome_produto: String,
    var desc_produto: String,
    var id_fornecedor: Long,
    var id: Long = -1
) : Serializable{
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(Tabela2Produtos.CAMPO_NOME_DO_PRODUTO, nome_produto)
        valores.put(Tabela2Produtos.CAMPO_DESC_DO_PRODUTO, desc_produto)
        valores.put(Tabela2Produtos.CAMPO_FK_FORNECEDOR, id_fornecedor)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor) : Produtos2 {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNomeProduto2 = cursor.getColumnIndex(Tabela2Produtos.CAMPO_NOME_DO_PRODUTO)
            val posDescProduto2= cursor.getColumnIndex(Tabela2Produtos.CAMPO_DESC_DO_PRODUTO)
            val posFornecedorFK = cursor.getColumnIndex(Tabela2Produtos.CAMPO_FK_FORNECEDOR)

            val id = cursor.getLong(posId)
            val NomeProduto2 = cursor.getString(posNomeProduto2)
            val DescProduto2 = cursor.getString(posDescProduto2)
            val FornecedorFK = cursor.getLong(posFornecedorFK)

            return Produtos2(NomeProduto2, DescProduto2, FornecedorFK, id)
        }
    }
}