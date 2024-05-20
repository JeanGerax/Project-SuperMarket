import java.io.IOException

fun main() {
    var opc: Int =100
    do {
        limparConsole()
        println("Segmentação de Clientes de um Supermercado!\n")

        var file = Compras(fileCompras = "src\\compras.csv")
        var dadosC = file.load()

        println("--------------------Menu-------------------")
        println("1 - Mostrar todos os dados por AnoMes")
        println("2 - Ver por Idas ao supermercado no AnoMes(em desenvolvimento)")
        println("3 - Ver por Numero de produtos comprados(falta fazer)")
        println("4 - Pesuisar produto no AnoMes(falta fazes)")
        //No futuro poderá ser adicionado outras
        println("0 - Sair")
        print("\nEscolha uma opção: ")

        opc = readLine()?.toIntOrNull() ?: -1

        when (opc) {
            1 -> {
                val listaPorData = file.dividirPorAnoMes()
                todosDadosPorData(listaPorData)
            }
            2 -> {
                val listaPorData = file.mostrarPorIdasAoSupermercadoNoAnoMes_v2()
                mostrarIdasAoSuperAnoMes(listaPorData)
            }
            0 -> println("Adeus!")
            else -> println("Opção inválida! Tente novamente.")
        }
    } while (opc != 0)
}

fun todosDadosPorData(listaPorData: MutableMap<String, MutableMap<String, List<String>>>){
    for (line in listaPorData) {
        val ano = line.key.substring(0, 4)
        val Mes = line.key.substring(5, 7)
        println("\nCompras na data: $Mes - $ano\n")

        val innerMap = line.value
        for (cliente in innerMap) {

            val clienteId = cliente.key.substring(0,4)
            val produtos = cliente.value

            println(" Cliente  $clienteId")
            println("Produtos  $produtos\n")
        }
    }
    println("Digite qualquer tecla para voltar...")
    var opcTeste = readLine()
}

fun limparConsole() {
    for (i in 1..50) {
        println()
    }
}

fun mostrarIdasAoSuperAnoMes(listaPorData: MutableMap<String, MutableMap<String, Int>>){

    for ((data, clientes) in listaPorData) {
        val contagemClientesPorIdas = mutableMapOf<Int, Int>().withDefault { 0 }

        for ((_, idas) in clientes) {
            contagemClientesPorIdas[idas] = contagemClientesPorIdas.getValue(idas) + 1
        }

        println("Data: $data")
        for ((idas, contagem) in contagemClientesPorIdas) {
            println("Clientes que foram $idas vezes: $contagem")
        }

        println("\n")
    }
    println("\nDigite qualquer tecla para voltar...")
    readLine()
}
