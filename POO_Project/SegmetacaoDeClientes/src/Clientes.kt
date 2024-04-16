import java.io.File

class Clientes(private val clientesFilename: String) {
    private val dados = mutableMapOf<String, List<String>>()

    fun load(){
        val file = File(clientesFilename)
        val lines = file.readLines().drop(n = 1)

        lines.forEach{ line ->
            var dadosC = line.split(",")
            var cod_Cliente = dadosC[0]
            var infoClientes = dadosC.drop(n = 1).map { it.trim().toString() }

            dados[cod_Cliente] = infoClientes
        }
    }
}