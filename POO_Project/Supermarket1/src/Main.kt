fun main() {
    println("Segmentação de Clientes de um Supermercado!\n\n")

    var file = Compras(fileCompras = "Supermarket1\\src\\compras.csv")
    var dadosC = file.load()

    //Teste se os dados estão corretos
    for (it in dadosC) {
        println(it)
    }
}
