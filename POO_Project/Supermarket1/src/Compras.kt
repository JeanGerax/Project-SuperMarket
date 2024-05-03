import java.io.File

class Compras(private var fileCompras: String) {
    private val dadosCompras = mutableMapOf<String, List<String>>()
    private val dadosPorData = mutableMapOf<String, MutableMap<String,List<String>>>()



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

    fun separarClientes(){

    }



    fun extrairAnoMes():MutableMap<String, MutableMap<String,List<String>>> {

        val lines = load()

        for ((chave, lista) in lines) {
            var data = lista.getOrNull(1)
            var data2 = data?.substring(0,7)
            var cliente = lista.getOrNull(0)
            var produtos = lista.getOrNull(3)

            if (dadosPorData.containsKey(data2)) {
                println("Presente o valor $data2")

            } else {
                println("Não presente o valor $data2. Adicionando ao mapa.")
                dadosPorData[data2!!] = mutableMapOf(cliente to listOf(produtos?))
            }
            //println("Chave: $chave, Data: ${data?.substring(0, 7)}")
        }



        return dadosPorData
    }

    fun main2() {
        val listaPorData = extrairAnoMes()
        println("Ano e mês: $listaPorData")
    }
}