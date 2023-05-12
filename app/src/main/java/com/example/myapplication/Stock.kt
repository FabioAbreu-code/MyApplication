package com.example.myapplication

data class Stock(
    var quantidade: Int,
    var idProduto: Int,
    var data: Long,
    var id: Long = -1
) {
}