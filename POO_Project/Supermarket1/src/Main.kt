import java.io.File
import java.io.IOException

var caminho = "src\\main\\kotlin\\ficheiros\\"

fun main() {
    var opc: Int = 100

    println("Segmentação de Clientes de um Supermercado!\n")
    perguntarFicheiro()

    do {
        limparConsole()

        val file = try {

            var ficheiro = "dados_organizados.csv"
            // var file = Compras(fileCompras = "C:\\Users\\gerem\\OneDrive\\Escritorio\\U.A\\SuperMarket\\SuperMarket1\\src\\compras.csv")
            Compras(fileCompras = "$caminho$ficheiro")
        } catch (e: IOException) {
            println("Erro ao carregar o ficheiro CSV: ${e.message}")
            return
        }


        println("------------------------Menu----------------------")
        println("| 1 - Todos os Dados por Ano-Mes                 |")
        println("| 2 - Idas ao Supermercado no Ano-Mes            |")
        println("| 3 - Total de Produtos Comprados no Ano-Mes     |")
        println("| 4 - Frequência de Compra de Produto no Ano-Mes |")
        println("| 5 - Pesuisar por Ano-Mes                       |")
        println("| 6 - Super Pesquisa                             |")
        println("| 7 - Mudar de Ficheiro                          |")
        println("| 0 - Sair                                       |")
        println("--------------------------------------------------")
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
            3 -> {
                val listaPorData = file.mostrarPorNumeroProductosComprados()
                mostrarPorNumProdComprados(listaPorData)
            }
            4 -> {
                val listaPorData = file.buscarPorNumeroProductosComprados()
                buscarPorNumeroProductosComprados(listaPorData)
            }
            5 -> {
                println("Digite o ano e mês (no formato AAAA-MM) para pesquisar produtos:")
                print("Ano-Mês: ")
                val anoMes = readLine()!!.trim()
                val produtosNoAnoMes: MutableMap<String, List<String>> = file.pesquisarProdutoNoAnoMes(anoMes)!!
                comprasAnoMes(anoMes, produtosNoAnoMes)
            }
            6 -> {
                val dadosSuper = file.superPesq()
                mostrarSuperPesq(dadosSuper)
            }
            7 -> {
                perguntarFicheiro()
            }
            0 -> {
                println("\n\n--------------------Adeus!------------------------")
            }
            else -> {
                println("Opção inválida! Tente novamente.")
            }
        }
    } while (opc != 0)
}


//Todos os Dados por Ano-Mes
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


//Idas ao Supermercado no Ano-Mes
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


//Total de Produtos Comprados no Ano-Mes
fun mostrarPorNumProdComprados(listaPorData: MutableMap<String, MutableMap<Int, List<String>>>) {
    for (line in listaPorData) {
        val ano = line.key.substring(0, 4)
        val Mes = line.key.substring(5, 7)
        println("---------------------------------------")
        println("\nData: $Mes - $ano\n")

        val QuantCli = line.value
        for (cliente in QuantCli) {

            val QuantComprada = cliente.key
            val clientes = cliente.value
            val NumCli = cliente.value.size
            println("Compraram apenas $QuantComprada produtos: \n")
            println("Clientes:")
            for (line2 in clientes){
                println("   $line2")
            }
            println("\n")
        }
    }
    
    println("Digite qualquer tecla para voltar...")
    var opcTeste = readLine()
}


//Frequência de Compra de Produto no Ano-Mes
fun buscarPorNumeroProductosComprados(listaPorData: MutableMap<String, MutableMap<String,Int>>){
    println("\nNúmero de produtos comprados:")
    for (line in listaPorData) {
        val ano = line.key.substring(0, 4)
        val Mes = line.key.substring(5, 7)
        println("---------------------------------------")
        println("\nData: $Mes - $ano\n")
        val prodQuant = line.value
        for (x in prodQuant) {
            println("${x.key} foi comprado ${x.value} ${if (x.value == 1) "vez" else "vezes"}")
        }
    }

    println("Digite qualquer tecla para voltar...")
    var opcTeste = readLine()
}


//Pesuisar por Ano-Mes
fun comprasAnoMes(anoMes: String ,prdutosNoAnoMes: MutableMap<String, List<String>>){
    println("Compras feitas na data $anoMes:")
    for(line in prdutosNoAnoMes){
        val clienteId = line.key.substring(0,4)
        val produtos = line.value
        println(" Cliente  $clienteId")
        println("Produtos  $produtos\n")
    }
    println("Digite qualquer tecla para voltar...")
    var opcTeste = readLine()
}


//Super Pesquisa
fun mostrarSuperPesq(dadosSuper: MutableMap<Int, MutableMap<Int, MutableMap<Int, MutableMap<Int, List<String>>>>>){
    for (line in dadosSuper) {
        var anoc = line.key
        var anod = line.value
        println("ANO: ${anoc}")
        for (x in anod){
            var mesc = x.key
            var mesd = x.value
            println("MES: ${mesc}")
            for (y in mesd){

                var diac = y.key
                var diad = y.value
                println("DIA: ${diac}")
                for (z in diad){
                    val cliente = z.key.toString().substring(0,4)
                    println("CLIENTE: ${cliente}")
                    println("PRODUTOS: ${z.value}\n")

                }
            }
        }
    }

    println("Digite qualquer tecla para voltar...")
    var opcTeste = readLine()
}


//Limpar Console
fun limparConsole() {
    for (i in 1..40) {
        println()
    }
}


//Pedir ficheiro
fun perguntarFicheiro(){
    limparConsole()
    val csvFiles = findCsvFiles(caminho)

    if (csvFiles.isEmpty()) {
        println("Não foram encontrados arquivos CSV na pasta atual.")
    } else {
        println("\n\nQual ficheiro pretende usar?")
        println("------------------------------------")
        var opc3: Int = 0
        csvFiles.forEach {
            opc3 += 1
            println("     $opc3-${it.name}")
        }
        println("------------------------------------")
    }

    var opcFicheiro: Int = -1
    //Executa até ser uma opção valida
    while (opcFicheiro !in 0 until csvFiles.size) {
        print("Ficheiro: ")
        try {
            opcFicheiro = readLine()?.toInt()?.minus(1) ?: -1
        } catch (e: NumberFormatException) {
            opcFicheiro = -1
        }

        if (opcFicheiro !in 0 until csvFiles.size) {
            println("Opção inválida. Tente novamente.")
        }
    }

    var nomeFicheiro = csvFiles[opcFicheiro].toString()
    nomeFicheiro = nomeFicheiro.substring(4, nomeFicheiro.length - 4)
    println(nomeFicheiro)

    var inputFilePath = "src\\$nomeFicheiro.csv"

    if (inputFilePath.isNullOrBlank()) {
        println("Ficheiro Inválido.")
        return
    }

    val organizer = CsvOrganizer(inputFilePath)
    organizer.processCsv()
}


//Procurar todos os ficheiros .csv
fun findCsvFiles(directoryPath: String): List<File> {
    val directory = File(directoryPath)
    return directory.listFiles { file ->
        file.isFile && file.extension.equals("csv", ignoreCase = true)
    }?.toList() ?: emptyList()
}
