import java.io.File
import java.io.IOException

class Compras(private var fileCompras: String) {
    private val dadosCompras = mutableMapOf<String, List<String>>()
    private val dadosPorData = mutableMapOf<String, MutableMap<String,List<String>>>()
    private val dadosPorVezes = mutableMapOf<String, MutableMap<String,Int>>()


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
            val data = line2.getOrNull(1)?.substring(0, 7) // Asumiendo que la fecha está en la segunda posición y tiene el formato "AAAA-MM"
            val keyCompra = line.key

            val cliente = line2.getOrNull(0).toString()
            val IdFinal = cliente + keyCompra // Para evitar errores de clave única, creamos una variable con el cliente y la compra

            val produtos = line2.drop(2) // Saltamos las primeras dos posiciones que contienen el cliente y la fecha
            val clienteProduto = mapOf(IdFinal to produtos)

            if (data != null) {
                if (dadosPorData.containsKey(data)) {
                    // Si el año-mes existe, agregamos al mapa existente el cliente y los productos
                    dadosPorData[data]?.putAll(clienteProduto)
                } else {
                    // Si el año-mes no existe, creamos un nuevo mapa para ese año-mes y agregamos el cliente y los productos
                    dadosPorData[data] = mutableMapOf(IdFinal to produtos)
                }
            }
        }
        return dadosPorData
    }


    // vezesClientesCompraramNumMes


    // versao feita por Diogo
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

    fun buscarPorNumeroProductosComprados(): Map<String, Int> {
        val numProductosComprados = mutableMapOf<String, Int>()
        dadosCompras.values.forEach { productos ->
            productos.drop(3).forEach { producto ->
                numProductosComprados[producto] = numProductosComprados.getOrDefault(producto, 0) + 1
            }
        }
        return numProductosComprados
    }

    fun pesquisarProdutoNoAnoMes(anoMes: String): List<String>? {
        val comprasPorData = dadosCompras.filter { it.value[2].startsWith(anoMes) }
        return comprasPorData.values.flatten().toList()
    }
}
