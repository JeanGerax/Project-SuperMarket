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
        //TESTE DE DADOS DO CSV
//        for (it in dados){
//            println(it)
//        }
    }

    //Verificar se conta existe
    fun verificarConta(email: String, password: String): Boolean {
        val clienteEncontrado = dados.filterValues { it.containsAll(listOf(email, password)) }
        return clienteEncontrado.isNotEmpty()
    }

    //Criar conta
    fun criarConta(dados1:List<String> ){
//        dados += dados1
        println(dados)

        var comprimento: Int = dados.size
        if (comprimento > 0) {
            val ultimaKey = dados.keys.elementAt(comprimento - 1)
        }
        comprimento += 1
        dados += (comprimento.toString() to dados1)
        println(dados)

        atualizarCSV()
    }

    //Atualizar no csv com os novos dados
    private fun atualizarCSV() {
        val file = File(clientesFilename)
        if (!file.exists()) {
            println("Ficheiro CSV não encontrado.")
            return
        }

        try {
            val writer = file.bufferedWriter()

            // Se o arquivo csv estiver vazio é adicionado o cabecalho
            if (file.length() == 0L) {
                writer.write("Cod_Cliente, Nome, NIF, Morada, Email, Password")
                writer.newLine()
            }

            // Guardar os dados
            for ((chave, valores) in dados) {
                val linha = "$chave,${valores.joinToString(",")}"
                writer.write("$linha\n")
            }

            writer.close()
            println("Dados atualizados com sucesso.")
        } catch (e: Exception) {
            println("Erro ao atualizar: ${e.message}")
        }
    }

}