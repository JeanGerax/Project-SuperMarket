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

        opc = try {
            readLine()?.toIntOrNull() ?: -1
        } catch (e: NumberFormatException) {
            -1
        }

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
                if (!anoMes.isNullOrEmpty()) {
                    val produtosNoAnoMes: MutableMap<String, List<String>>? = file.pesquisarProdutoNoAnoMes(anoMes)
                    if (produtosNoAnoMes != null) {
                        comprasAnoMes(anoMes, produtosNoAnoMes)
                    } else {
                        println("Nenhum dado encontrado para $anoMes")
                    }
                } else {
                    println("Entrada inválida.")
                }
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

fun todosDadosPorData(listaPorData: MutableMap<String, MutableMap<String, List<String>>>){
    for (line in listaPorData) {
        val ano = try {
            line.key.substring(0, 4)
        } catch (e: StringIndexOutOfBoundsException) {
            println("Erro ao obter o ano: ${e.message}")
            return
        }
        val Mes = try {
            line.key.substring(5, 7)
        } catch (e: StringIndexOutOfBoundsException) {
            println("Erro ao obter o mês: ${e.message}")
            return
        }
        println("\nCompras na data: $Mes - $ano\n")

        val innerMap = line.value
        for (cliente in innerMap) {

            val clienteId = try {
                cliente.key.substring(0, 4)
            } catch (e: StringIndexOutOfBoundsException) {
                println("Erro ao extrair o cliente ID: ${e.message}")
                continue
            }
            val produtos = cliente.value

            println(" Cliente  $clienteId")
            println("Produtos  $produtos\n")
        }
    }
    print("Digite qualquer tecla para voltar...")
    var opcTeste = readLine()
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
    print("\nDigite qualquer tecla para voltar...")
    readLine()
}

fun mostrarPorNumProdComprados(listaPorData: MutableMap<String, MutableMap<Int, List<String>>>) {
    for (line in listaPorData) {
        val ano = try {
            line.key.substring(0, 4)
        } catch (e: StringIndexOutOfBoundsException) {
            println("Erro ao extrair o cliente ID: ${e.message}")
            continue
        }
        val Mes = try {
            line.key.substring(5, 7)
        } catch (e: StringIndexOutOfBoundsException) {
            println("Erro ao extrair o cliente ID: ${e.message}")
            continue
        }
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
    print("Digite qualquer tecla para voltar...")
    var opcTeste = readLine()
}

fun buscarPorNumeroProductosComprados(listaPorData: MutableMap<String, MutableMap<String,Int>>){
    println("\nNúmero de produtos comprados:")
    for (line in listaPorData) {
        val ano = try {
            line.key.substring(0, 4)
        } catch (e: StringIndexOutOfBoundsException) {
            println("Erro ao extrair o cliente ID: ${e.message}")
            continue
        }
        val Mes = try {
            line.key.substring(5, 7)
        } catch (e: StringIndexOutOfBoundsException) {
            println("Erro ao extrair o cliente ID: ${e.message}")
            continue
        }
        println("---------------------------------------")
        println("\nData: $Mes - $ano\n")
        val prodQuant = line.value
        for (x in prodQuant) {
            println("${x.key} foi comprado ${x.value} ${if (x.value == 1) "vez" else "vezes"}")
        }
    }

    print("Digite qualquer tecla para voltar...")
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
    print("Digite qualquer tecla para voltar...")
    var opcTeste = readLine()
}

fun mostrarSuperPesq(dadosSuper: MutableMap<Int, MutableMap<Int, MutableMap<Int, MutableMap<Int, List<String>>>>>){
    for (line in dadosSuper) {
        val anoc = try {
            line.key
        } catch (e: StringIndexOutOfBoundsException) {
            println("Erro ao extrair o cliente ID: ${e.message}")
            continue
        }
        val anod = try {
            line.value
        } catch (e: StringIndexOutOfBoundsException) {
            println("Erro ao extrair o cliente ID: ${e.message}")
            continue
        }
        println("\n\n-----------------ANO: ${anoc}----------------\n")
        for (x in anod){
            val mesc = try {
                x.key
            } catch (e: StringIndexOutOfBoundsException) {
                println("Erro ao extrair o cliente ID: ${e.message}")
                continue
            }
            val mesd = try {
                x.value
            } catch (e: StringIndexOutOfBoundsException) {
                println("Erro ao extrair o cliente ID: ${e.message}")
                continue
            }
            println("_______MES: ${mesc}_______")
            for (y in mesd){

                val diac = try {
                    y.key
                } catch (e: StringIndexOutOfBoundsException) {
                    println("Erro ao extrair o cliente ID: ${e.message}")
                    continue
                }
                val diad = try {
                    y.value
                } catch (e: StringIndexOutOfBoundsException) {
                    println("Erro ao extrair o cliente ID: ${e.message}")
                    continue
                }
                println("   DIA: ${diac}")
                for (z in diad){
                    val cliente = z.key.toString().substring(0,4)
                    println("\t  CLIENTE: ${cliente}")
                    println("\t  PRODUTOS: ${z.value}\n")
                }
            }
            println("\n")
        }
    }
    do {
        print("Pretende fazer uma pesquisa? \n   1-Sim \n   0-Não")
        print("\n\nOpc: ")
        val opcTeste = readLine()

        if (opcTeste != "0" && opcTeste != "1") {
            println("Opc inválida! Opções válidas 1 ou 0")
            continue
        }

        if (opcTeste == "1") {
            println("\n\nInsira o Ano, Mes e dia nas seguintes opções. \nSe não pretender dizer uma delas deixar em branco")

            println("\nAno? ")
            val Ano = readLine()?.toIntOrNull()
            println("\nMes? ")
            val Mes = readLine()?.toIntOrNull()
            println("\nDia? ")
            val Dia = readLine()?.toIntOrNull()

            if (Ano != null && dadosSuper.containsKey(Ano)) {
                println("\n\n-----------------ANO: ${Ano}----------------\n")
                val anoData = dadosSuper[Ano]
                if (Mes != null && anoData?.containsKey(Mes) == true) {
                    println("_______MES: ${Mes}_______")
                    val mesData = anoData[Mes]
                    if (Dia != null && mesData?.containsKey(Dia) == true) {
                        println("   DIA: ${Dia}")
                        val diaData = mesData[Dia]
                        for (e in diaData!!) {
                            val cliente = e.key.toString().substring(0,4)
                            println("\t  CLIENTE: ${cliente}")
                            println("\t  PRODUTOS: ${e.value}\n")
                        }
                    } else if (Dia == null) {
                        for ((diac, diad) in mesData!!) {
                            println("   DIA: ${diac}")
                            for (z in diad) {
                                val cliente = z.key.toString().substring(0,4)
                                println("\t  CLIENTE: ${cliente}")
                                println("\t  PRODUTOS: ${z.value}\n")
                            }
                        }
                    }
                } else if (Mes == null) {
                    for ((mesc, mesd) in anoData!!) {
                        println("_______MES: ${mesc}_______")
                        for ((diac, diad) in mesd) {
                            println("   DIA: ${diac}")
                            for (z in diad) {
                                val cliente = z.key.toString().substring(0,4)
                                println("\t  CLIENTE: ${cliente}")
                                println("\t  PRODUTOS: ${z.value}\n")
                            }
                        }
                    }
                }
            } else if (Ano == null) {
                for ((anoc, anod) in dadosSuper) {
                    println("\n\n-----------------ANO: ${anoc}----------------\n")
                    for ((mesc, mesd) in anod) {
                        println("_______MES: ${mesc}_______")
                        for ((diac, diad) in mesd) {
                            println("   DIA: ${diac}")
                            for (z in diad) {
                                val cliente = z.key.toString().substring(0,4)
                                println("\t  CLIENTE: ${cliente}")
                                println("\t  PRODUTOS: ${z.value}\n")
                            }
                        }
                    }
                }
            }
        } else {
            println("\n------A sair da super pesquisa------")
        }

    } while (opcTeste != "0")
}

fun limparConsole() {
    for (i in 1..40) {
        println()
    }
}

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

fun findCsvFiles(directoryPath: String): List<File> {
    val directory = File(directoryPath)
    return directory.listFiles { file ->
        file.isFile && file.extension.equals("csv", ignoreCase = true)
    }?.toList() ?: emptyList()
}