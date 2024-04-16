import java.io.File

class Produtos (val produtosFilename: String){
    private val dados = mutableMapOf<String, List<String>>()

    fun load(){
        val file = File(produtosFilename)
        val lines = file.readLines().drop(n = 1)

        lines.forEach{ line ->
            var dadosP = line.split(",")
            var cod_Produto = dadosP[0]
            var infoProdutos = dadosP.drop(n = 1).map { it.trim().toString() }

            dados[cod_Produto] = infoProdutos
        }
        //TESTE DE DADOS DO CSV
//        for (it in dados){
//            println(it)
//        }

    }
}