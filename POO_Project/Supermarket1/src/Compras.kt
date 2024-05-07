import java.io.File

class Compras(private var fileCompras: String) {
    private val dadosCompras = mutableMapOf<String, List<String>>()
    private val dadosPorData = mutableMapOf<String, MutableMap<String,List<String>>>()
    private val dadosPorVezes = mutableMapOf<String, MutableMap<String,List<String>>>()


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


    fun dividirPorAnoMes():MutableMap<String, MutableMap<String,List<String>>> {

        val lines = load()
        for (line in lines) {
            var line2 = line.value
            var data = line2.getOrNull(1)?.substring(0,7)
            var data2 = data?.substring(0,7)
            var keyCompra = line.key

            val cliente = line2.getOrNull(0).toString()
            //Para não haver erro de chave unica, é criada uma variavel com o cliente e a compra
            var IdFinal = cliente + keyCompra

            var produtos = line2.drop(n=2)
            val clienteProduto = mutableMapOf(
                IdFinal to produtos
            )

            if (dadosPorData.containsKey(data)) {
                //Se o ano-mes existir adiciona aos dados ja existintes outro cliente e produtos
                dadosPorData[data!!]?.putAll(clienteProduto)

            } else {
                //Se o ano-mes nao existir adiciona juntamente com o cliente e produtos
                dadosPorData[data!!] = clienteProduto
            }
        }
        return dadosPorData
    }



//    fun dividirPorIdasAoSuper(){
//    }
}