fun main() {
    println("Segmentação de Clientes de um Supermercado!\n\n")

    var file = Compras(fileCompras = "src\\compras.csv")
    var dadosC = file.load()

    //Teste se os dados estão corretos
    for (it in dadosC) {
        println(it)
    }

    var teste1 = file.main2()

}
