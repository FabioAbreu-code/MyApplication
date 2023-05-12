package com.example.myapplication

import android.database.sqlite.SQLiteDatabase
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BdInstrumentedTest {

    private fun getAppContext() = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun apagaBaseDados(){
        getAppContext().deleteDatabase(BdProductsOpenHelper.NOME_BASE_DADOS)
    }

    @Test
    fun consegueAbrirBaseDados() {
        val openHelper = BdProductsOpenHelper(getAppContext())
        val bd = openHelper.readableDatabase
        assert(bd.isOpen)
    }

    private fun getWritableDatabase(): SQLiteDatabase {
        val openHelper = BdProductsOpenHelper(getAppContext())
        return openHelper.writableDatabase

    }

    private fun insereProduto(
        bd: SQLiteDatabase,
        produtos: Produtos
    ) {
        produtos.id = TabelaProdutos(bd).insere(produtos.toContentValues())
        assertNotEquals(-1, produtos.id)
    }

    private fun insereStock(
        bd: SQLiteDatabase,
        stock: Stock
    ) {
        stock.id = TabelaStock(bd).insere(stock.toContentValues())
        assertNotEquals(-1, stock.id)
    }

    @Test
    fun consegueInserirProdutos() {
        val bd = getWritableDatabase()

        val produtos = Produtos("Água Monchique de 720ml, com 9.5pH. Garrafa 100% reciclada.","Água Monchique 720ml")
        insereProduto(bd, produtos)
    }


    @Test
    fun consegueInserirStock() {
        val bd = getWritableDatabase()

        val produtos1 = Produtos("Conservar em local fresco e seco. Uma vez aberta a embalagem conservar no frigorífico e consumir dentro de 3 dias.","Leite Mimosa UHT Meio Gordo 1L")
        insereProduto(bd, produtos1)

        val stock = Stock(2,produtos1.id,12052023)
        insereStock(bd, stock)

        val produtos2 = Produtos("Conservar entre 4ºC e 8ºC","Queijo Flamengo Fatiado 500gr Continente")
        insereProduto(bd, produtos2)

        val stock2 = Stock(0,produtos2.id,12052023)
        insereStock(bd, stock2)

        val produtos3 = Produtos("Conservar a temperatura inferior a 6ºC","Manteiga Mimosa com Sal 250gr")
        insereProduto(bd, produtos3)

        val stock3 = Stock(1,produtos3.id,12052023)
        insereStock(bd, stock3)
    }



}