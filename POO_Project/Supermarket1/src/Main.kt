fun main() {
    println("Segmentação de Clientes de um Supermercado!\n\n")

    var file = Compras(fileCompras = "src\\compras.csv")
    var dadosC = file.load()
    val listaPorData = file.extrairAnoMes()

    for (it in listaPorData) {
        println(it)
    }


    for (line in listaPorData) {
        val anoMes = line.key
        println("\nCompras na data: $anoMes")

        val innerMap = line.value
        for (cliente in innerMap) {

            val clienteId = cliente.key
            val produtos = cliente.value

            println(" Cliente  $clienteId")
            println("Produtos  $produtos\n")
        }
    }
}
