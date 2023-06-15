package com.example.myapplication

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
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
        getAppContext().deleteDatabase(BdStockOpenHelper.NOME_BASE_DADOS)
    }

    @Test
    fun consegueAbrirBaseDados() {
        val openHelper = BdStockOpenHelper(getAppContext())
        val bd = openHelper.readableDatabase
        assert(bd.isOpen)
    }

    private fun getWritableDatabase(): SQLiteDatabase {
        val openHelper = BdStockOpenHelper(getAppContext())
        return openHelper.writableDatabase

    }
    /*
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
    */
    @Test
    fun consegueInserirFornecedor() {
        val bd = getWritableDatabase()

        val fornecedores = Fornecedores("Luso","935 635 051")
        insereFornecedor(bd, fornecedores)

    }

    @Test
    fun consegueInserirProduto2() {
        val bd = getWritableDatabase()

        val fornecedores = Fornecedores("Compal","940 840 481")
        insereFornecedor(bd, fornecedores)

        val produto = Produtos2("Néctar Pêssego 1lt","Uma vez aberto conservar no frigorífico.",fornecedores.id)
        insereProdutos2(bd, produto)

    }

    @Test
    fun consegueLerFornecedores() {
        val bd = getWritableDatabase()

        val fornecedor = Fornecedores("Sumol","950 668 903")
        insereFornecedor(bd, fornecedor)

        val fornecedor2 = Fornecedores("Parmalat","935 867 264")
        insereFornecedor(bd, fornecedor2)

        val tabelaFornecedores = TabelaFornecedores(bd)

        val cursor = tabelaFornecedores.consulta(
            TabelaFornecedores.CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(fornecedor2.id.toString()),
            null,
            null,
            null
        )

        assert(cursor.moveToNext())

        val fornecedoresBD = Fornecedores.fromCursor(cursor)

        assertEquals(fornecedor2, fornecedoresBD)

        val cursorTodosFornecedores = tabelaFornecedores.consulta(
            TabelaFornecedores.CAMPOS,
            null, null, null, null,
            TabelaFornecedores.NOME_FORNECEDOR
        )

        assert(cursorTodosFornecedores.count > 1)
    }

    @Test
    fun consegueLerProdutos2() {
        val bd = getWritableDatabase()

        val fornecedor1 = Fornecedores("Matutano","972 138 453")
        insereFornecedor(bd, fornecedor1)

        val produto1 = Produtos2("Amendoim no Forno emb.200g","Conservar em local fresco e seco, ao abrigo da luz solar. Fechar bem a embalagem após cada utilização.",fornecedor1.id)
        insereProdutos2(bd, produto1)

        val produto2 = Produtos2("Pistachos Tostados no Forno MATUTANO emb.70g","Conservar em local fresco e seco, ao abrigo da luz solar. Fechar bem a embalagem após cada utilização.", fornecedor1.id)
        insereProdutos2(bd, produto2)

        val tabelaProduto2 = Tabela2Produtos(bd)

        val cursor = tabelaProduto2.consulta(
            Tabela2Produtos.CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(produto1.id.toString()),
            null,
            null,
            null
        )

        assert(cursor.moveToNext())

        val produto2BD = Produtos2.fromCursor(cursor)

        assertEquals(produto1, produto2BD)

        val cursorTodosProduto2 = tabelaProduto2.consulta(
            Tabela2Produtos.CAMPOS,
            null, null, null, null,
            Tabela2Produtos.CAMPO_NOME_DO_PRODUTO
        )

        assert(cursorTodosProduto2.count > 1)
    }

    @Test
    fun consegueAlterarFornecedores() {
        val bd = getWritableDatabase()

        val fornecedores = Fornecedores("Sica","943 378 092")
        insereFornecedor(bd, fornecedores)

        fornecedores.nome_fornecedor = "Sical"
        fornecedores.contacto_fornecedor = "943 378 093"

        val registosAlterados = TabelaFornecedores(bd).altera(
            fornecedores.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(fornecedores.id.toString())
        )

        assertEquals(1, registosAlterados)
    }

    @Test
    fun consegueAlterarProduto2() {
        val bd = getWritableDatabase()

        val fornecedor1 = Fornecedores("Parmalat","980 261 863")
        insereFornecedor(bd, fornecedor1)

        val fornecedor2 = Fornecedores("Super Bock","926 848 648")
        insereFornecedor(bd, fornecedor2)

        val produto = Produtos2("Nata para Culinária emb.200ml", "Conservar à temperatura ambiente, em local fresco e seco. Após abertura, conservar no frigorífico e consumir no prazo de 4 dias.", fornecedor1.id)
        insereProdutos2(bd, produto)

        produto.nome_produto = "Natas para Culinária emb.1l"
        produto.desc_produto = "Conservar à temperatura ambiente, em local fresco e seco. Após abertura, conservar no frigorífico e consumir no prazo de 3 dias."

        val registosAlterados = Tabela2Produtos(bd).altera(
            produto.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(produto.id.toString())
        )

        assertEquals(1, registosAlterados)
    }


    @Test
    fun consegueApagarFornecedores() {
        val bd = getWritableDatabase()

        val fornecedores = Fornecedores("Paladin","982 747 382")
        insereFornecedor(bd, fornecedores)

        val registosEliminados = TabelaFornecedores(bd).elimina(
            "${BaseColumns._ID}=?",
            arrayOf(fornecedores.id.toString())
        )

        assertEquals(1, registosEliminados)
    }

    @Test
    fun consegueApagarProdutos() {
        val bd = getWritableDatabase()

        val fornecedor = Fornecedores("Guloso","918 814 769")
        insereFornecedor(bd, fornecedor)

        val produto = Produtos2("Polpa de Tomate emb. 500gr", "Depois de aberto colocar no frigorífico e consumir o mais breve possível",fornecedor.id)
        insereProdutos2(bd, produto)

        val registosEliminados = Tabela2Produtos(bd).elimina(
            "${BaseColumns._ID}=?",
            arrayOf(produto.id.toString())
        )

        assertEquals(1, registosEliminados)
    }
    private fun insereProdutos2(bd: SQLiteDatabase, produto2: Produtos2) {
        produto2.id = Tabela2Produtos(bd).insere(produto2.toContentValues())
        assertNotEquals(-1, produto2.id)
    }

    private fun insereFornecedor(bd: SQLiteDatabase, fornecedores: Fornecedores) {
        fornecedores.id = TabelaFornecedores(bd).insere(fornecedores.toContentValues())
        assertNotEquals(-1, fornecedores.id)
    }

    /*
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

        val Data1 = Calendar.getInstance()
        Data1.set(2023,5,12)
        val stock = Stock(2,produtos1, Data1)
        insereStock(bd, stock)

        val produtos2 = Produtos("Conservar entre 4ºC e 8ºC","Queijo Flamengo Fatiado 500gr Continente")
        insereProduto(bd, produtos2)

        val Data2 = Calendar.getInstance()
        Data2.set(2023,5,13)
        val stock2 = Stock(0,produtos2,Data2)
        insereStock(bd, stock2)

        val produtos3 = Produtos("Conservar a temperatura inferior a 6ºC","Manteiga Mimosa com Sal 250gr")
        insereProduto(bd, produtos3)

        val Data3 = Calendar.getInstance()
        Data3.set(2023,5,13)
        val stock3 = Stock(1,produtos3,Data3)
        insereStock(bd, stock3)
    }

    @Test
    fun consegueLerProdutos(){
        val bd = getWritableDatabase()

        val produto1 = Produtos("Conservar em local fresco e seco.","Arroz Basmati 1kg Continente")
        insereProduto(bd, produto1)

        val produto2 = Produtos("Conservar em local fresco e seco ao abrigo da luz.","Ice Tea Pêssego 2lt Continente")
        insereProduto(bd, produto2)

        val tabelaProdutos = TabelaProdutos(bd)

        val cursor = tabelaProdutos.consulta(
            TabelaProdutos.CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(produto1.id.toString()),
            null,
            null,
            null
            )

        assert(cursor.moveToNext())

        val produtoBD = Produtos.fromCursor(cursor)

        assertEquals(produto1, produtoBD)

        val cursorTodosProdutos = tabelaProdutos.consulta(
            TabelaProdutos.CAMPOS,
            null,
            null,
            null,
            null,
            TabelaProdutos.CAMPO_NOME
        )

        assert(cursorTodosProdutos.count > 1)

    }

    @Test
    fun consegueLerStock(){
        val bd = getWritableDatabase()

        val produto1 = Produtos("Conservar num local fresco e seco.","Massa Esparguete 500gr Continente")
        insereProduto(bd, produto1)

        val Data1 = Calendar.getInstance()
        Data1.set(2023,5,12)
        val stock1 = Stock(2,produto1, Data1)
        insereStock(bd, stock1)

        val produto2 = Produtos("Local fresco e seco. Depois de aberto, colocar no frigorífico e consumir de preferência no prazo de 3 dias.","Nata UHT para Culinária 200ml Continente")
        insereProduto(bd, produto2)

        val Data2 = Calendar.getInstance()
        Data2.set(2023,5,13)
        val stock2 = Stock(1,produto2, Data2)
        insereStock(bd, stock2)

        val tabelaStock = TabelaStock(bd)

        val cursor = tabelaStock.consulta(
            TabelaStock.CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(stock1.id.toString()),
            null,
            null,
            null
        )

        assert(cursor.moveToNext())

        val stockBD = Stock.fromCursor(cursor)

        assertEquals(stock1, stockBD)

        val cursorTodoStock = tabelaStock.consulta(
            TabelaStock.CAMPOS,
            null,
            null,
            null,
            null,
            TabelaStock.CAMPO_QUANTIDADE
        )

        assert(cursorTodoStock.count > 1)

    }

    @Test
    fun  consegueEliminarStock() {
        val db = getWritableDatabase()

        val produto = Produtos("Lava Tudo Lavanda com tecnologia extra brilho oferece uma limpeza profunda com uma fragrância intensa dando um brilho único às superfícies.", "Sonasol, Lava Tudo Perfumado Lavanda 3lt")
        insereProduto(db, produto)

        val Data1 = Calendar.getInstance()
        Data1.set(2023,5,12)
        val stock = Stock(0,produto,Data1)
        insereStock(db, stock)

        val registosEliminados = TabelaStock(db).elimina(
            "${BaseColumns._ID}=?",
            arrayOf("${stock.id}"))

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun  consegueEliminarProdutos() {
        val db = getWritableDatabase()

        val produto = Produtos("Manter fora do alcance das crianças. Armazenar afastado de alimentos ou bebidas. Provoca irritação ocular.", "Detergente Manual Loiça Limão 1lt Continente")
        insereProduto(db, produto)

        val registosEliminados = TabelaProdutos(db).elimina(
            "${BaseColumns._ID}=?",
            arrayOf("${produto.id}"))

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueAlterarStock() {
        val db = getWritableDatabase()


        val produto = Produtos("Conservar a -18ºC. Contém Trigo. Pode conter vestígios de Ovos.", "Nuggets de Frango emb. 208 gr (10 un)")
        insereProduto(db, produto)

        val Data1 = Calendar.getInstance()
        Data1.set(2023,5,12)

        val Data2 = Calendar.getInstance()
        Data1.set(2023,5,15)

        val stock = Stock(1,produto,Data1)
        insereStock(db, stock)

        stock.quantidade = 2
        stock.data = Data2

        val registosAlterados = TabelaStock(db).altera(
            stock.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf("${stock.id}"))

        assertEquals(1,registosAlterados)

        db.close()
    }

    @Test
    fun consegueAlterarProduto() {
        val db = getWritableDatabase()


        val produto = Produtos("Armazenar em local fresco e seco. Proteger dos raios solares.", "Cuétara, Bolachas Maria emb. 800gr (4 un)")
        insereProduto(db, produto)

        produto.nome = "Cuétara, Bolachas Maria emb. 400gr (2 un)"

        val registosAlterados = TabelaProdutos(db).altera(
            produto.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf("${produto.id}"))

        assertEquals(1,registosAlterados)

        db.close()
    }
     */
}