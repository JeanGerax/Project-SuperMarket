import java.io.File
import java.io.IOException

class Compras(private var fileCompras: String) {
    private val dadosCompras = mutableMapOf<String, List<String>>()
    private val dadosPorData = mutableMapOf<String, MutableMap<String,List<String>>>()
    private val dadosPorVezes = mutableMapOf<String, MutableMap<String,Int>>()
    private val dadosPorQuantProd = mutableMapOf<String, MutableMap<Int,List<String>>>()
    private val dadosSuperPesq = mutableMapOf<Int, MutableMap<Int,MutableMap<Int, MutableMap<Int, List<String>>>>>()

    fun load():MutableMap<String, List<String>> {
        val file = File(fileCompras)
        val lines = file.readLines().drop(n = 1)

        lines.forEach { line ->
            var dados = line.split(",")
            var codCompra = dados[0]
            var infCompras = dados.drop(n=1).map { it.trim().toString() }

            dadosCompras[codCompra] = infCompras
        }
        return dadosCompras
    }

    fun dividirPorAnoMes(): MutableMap<String, MutableMap<String, List<String>>> {
        val lines = load()
        for (line in lines) {
            val line2 = line.value
            val data = line2.getOrNull(1)?.substring(0, 7)
            val keyCompra = line.key

            val cliente = line2.getOrNull(0).toString()
            val IdFinal = cliente + keyCompra

            val produtos = line2.drop(2)
            val clienteProduto = mapOf(IdFinal to produtos)
            if (data != null) {
                if (dadosPorData.containsKey(data)) {
                    dadosPorData[data]?.putAll(clienteProduto)
                } else {
                    dadosPorData[data] = mutableMapOf(IdFinal to produtos)
                }
            }
        }

        return dadosPorData
    }

    fun mostrarPorIdasAoSupermercadoNoAnoMes_v2(): MutableMap<String, MutableMap<String, Int>> {
        val lines = load()
        for (line in lines) {
            val line2 = line.value
            val data = line2.getOrNull(1)?.substring(0, 7)
            val cliente = line2.getOrNull(0).toString()

            if (data != null) {
                if (dadosPorVezes.containsKey(data)) {
                    val clientesPorData = dadosPorVezes[data]!!
                    if (clientesPorData.containsKey(cliente)) {
                        clientesPorData[cliente] = clientesPorData.getValue(cliente) + 1
                    } else {
                        clientesPorData[cliente] = 1
                    }
                } else {
                    dadosPorVezes[data] = mutableMapOf(cliente to 1)
                }
            } else {
                println("Data não existe!")
            }
        }
        return dadosPorVezes
    }

    fun mostrarPorNumeroProductosComprados(): MutableMap<String, MutableMap<Int, List<String>>>{
        val lines = load()
        for (line in lines) {
            val line2 = line.value
            val data = line2.getOrNull(1)?.substring(0, 7)
            val cliente = line2.getOrNull(0).toString()
            val produtos = line2.drop(2)
            val numProd = produtos.size

            if (data != null) {
                if (dadosPorQuantProd.containsKey(data)) {
                    val clientesPorData = dadosPorQuantProd[data]!!
                    if (clientesPorData.containsKey(numProd)) {
                        var clientesOfNumProd: List<String> = clientesPorData[numProd]!!
                        clientesOfNumProd = clientesOfNumProd + cliente
                        clientesPorData[numProd] = clientesOfNumProd
                    } else {
                        clientesPorData[numProd] = listOf(cliente)
                    }
                } else {
                    val clientesProd = listOf(cliente)
                    val numProdCliente = mutableMapOf(numProd to clientesProd)
                    dadosPorQuantProd[data] = numProdCliente
                }
            } else {
                println("Data não existe!")
            }
        }
        return dadosPorQuantProd
    }

    fun buscarPorNumeroProductosComprados(): MutableMap<String, MutableMap<String,Int>> {
        val numProductosComprados_v2 = mutableMapOf<String, MutableMap<String,Int>>()
        val lines = load()
        for (line in lines) {
            val line2 = line.value
            val data = line2.getOrNull(1)?.substring(0, 7)
            val produtos = line2.drop(2)

            for (produto in produtos) {

                if (data != null) {
                    if (numProductosComprados_v2.containsKey(data)) {
                        val clientesPorData = numProductosComprados_v2[data]!!
                        if (clientesPorData.containsKey(produto)) {
                            clientesPorData[produto] = clientesPorData.getValue(produto) + 1
                        } else {
                            clientesPorData[produto] = 1
                        }
                    } else {
                        numProductosComprados_v2[data] = mutableMapOf(produto to 1)
                    }
                } else {
                    println("Data não existe!")
                }
            }
        }
        return numProductosComprados_v2
    }

    fun pesquisarProdutoNoAnoMes(anoMes: String): MutableMap<String, List<String>>? {
        val dados = dividirPorAnoMes()
        val comprasPorData_v2 = dados["$anoMes"]
        return comprasPorData_v2
    }

    fun superPesq():MutableMap<Int, MutableMap<Int, MutableMap<Int, MutableMap<Int, List<String>>>>>{
        val lines = load()
        for (line in lines) {
            val line2 = line.value
            val ano = line2.getOrNull(1)?.substring(0, 4)!!.toInt()
            val mes = line2.getOrNull(1)?.substring(5, 7)!!.toInt()
            val dia = line2.getOrNull(1)?.substring(8, 10)!!.toInt()

            val keyCompra = line.key
            val cliente = line2.getOrNull(0).toString()
            var IdFinal = cliente + keyCompra
            val produtos = line2.drop(2)
            val clienteProduto = mutableMapOf(IdFinal.toInt() to produtos)

            if (dadosSuperPesq.containsKey(ano)) {
                val dados2 = dadosSuperPesq[ano]
                if (dados2!!.containsKey(mes)) {
                    val dados3 = dados2[mes]
                    if (dados3!!.containsKey(dia)) {
                        dados3[dia]?.putAll(clienteProduto)
                        dados2[mes]?.putAll(dados3)
                        dadosSuperPesq[ano]?.putAll(dados2)
                    } else {
                        var dados4 = mutableMapOf<Int, MutableMap<Int, List<String>>> ()
                        dados4[dia] = mutableMapOf(IdFinal.toInt() to produtos)

                        dados2[mes]?.putAll(dados4)
                        dadosSuperPesq[ano]?.putAll(dados2)
                    }
                }else {
                    var dados5 = mutableMapOf<Int, MutableMap<Int, List<String>>> ()
                    dados5[dia] = mutableMapOf(IdFinal.toInt() to produtos)

                    var dados6 = mutableMapOf<Int, MutableMap<Int, MutableMap<Int, List<String>>>> ()
                    dados6[mes] = dados5

                    dadosSuperPesq[ano]?.putAll(dados6)
                }

            } else {
                var dados6 = mutableMapOf<Int, MutableMap<Int, List<String>>> ()
                dados6[dia] = mutableMapOf(IdFinal.toInt() to produtos)

                var dados7 = mutableMapOf<Int, MutableMap<Int, MutableMap<Int, List<String>>>> ()
                dados7[mes] = dados6

                dadosSuperPesq[ano] = dados7
            }
        }
        return dadosSuperPesq
    }
}