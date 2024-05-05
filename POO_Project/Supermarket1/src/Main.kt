fun main() {
    var opc: Int =100
    //while (opc!=0) {

        println("Segmentação de Clientes de um Supermercado!\n\n")

        var file = Compras(fileCompras = "src\\compras.csv")
        var dadosC = file.load()
        val listaPorData = file.dividirPorAnoMes()

        todosDadosPorData(listaPorData)
    //}
}

fun todosDadosPorData(listaPorData: MutableMap<String, MutableMap<String, List<String>>>){
    for (line in listaPorData) {
        val anoMes = line.key
        println("\nCompras na data: $anoMes")

        val innerMap = line.value
        for (cliente in innerMap) {

            val clienteId = cliente.key.substring(0,4)
            val produtos = cliente.value

            println(" Cliente  $clienteId")
            println("Produtos  $produtos\n")
        }
    }
}