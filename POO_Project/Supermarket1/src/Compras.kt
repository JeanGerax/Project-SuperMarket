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
        for (line in lines) {
            var line2 = line.value
            var data = line2.getOrNull(1)
            var data2 = data?.substring(0,7)

            
            val cliente = line2.getOrNull(0).toString()
            var produtos = line2.drop(n=2)
            val produtos1 = line.value.getOrNull(2)?.let { listOf(it) } ?: emptyList()
            val initialData1 = mapOf(
                cliente to produtos
            )

            val initialData2 = mutableMapOf(
                cliente to produtos
            )

            if (dadosPorData.containsKey(data2)) {
                //Se o ano-mes existir adiciona aos dados ja existintes outro cliente e produtos
                dadosPorData[data2!!]?.putAll(initialData1)

            } else {
                //Se o ano-mes nao existir adiciona juntamente com o cliente e produtos
                dadosPorData[data2!!] = initialData2

            }
        }
        return dadosPorData
    }
}