fun main() {
    println("Bem Vindo ao SuperMarket!")

    // Caminho para o ficheiro CSV de clientes (Por enquanto apenas funciona assim)
    //val caminho: String = "C:\\Users\\didic\\Documents\\ctesp\\2ºSemestre\\POO_Project\\Supermarket1\\src\\"
    val caminho: String ="C:\\Users\\diogochipelo05\\OneDrive - Universidade de Aveiro\\POO\\ProjetoFinal\\POO_Project\\Supermarket1\\src\\"

    var opc: Int
    do {
        println("--------MENU----------")
        println("- 1- Login Clientes  -")
        println("- 2- Criar Conta     -")
        println("- 3- Administração   -")
        println("-                    -")
        println("- 0- Sair            -")
        println("----------------------")
        println("")
        println("Digite a opcção que deseja!")

        opc = readLine()?.toInt() ?: 0

        when (opc) {
            1 -> loginCliente(caminho)
            2 -> criarCliente(caminho)
            3 -> adm(caminho)
        }
    } while (opc != 0)
    println("Adeus! Obrigado por visitar o nosso Supermarket!")
}

fun cliente(){
    //Menu para o cliente após fazer login


}

fun loginCliente(caminho: String) {
    val clientesBD = Clientes(clientesFilename = caminho + "Clientes.csv")
    clientesBD.load()

    var email: String
    var password: String

    println("Email?")
    email = readLine() ?: ""
    println("Password?")
    password = readLine() ?: ""

    if (clientesBD.verificarConta(email, password)) {
        println("Login bem sucedido!")
        var opc2:Int = 100
        do {
            println("------MENU------")
            println("- 1- Produtos  -")
            println("- 2- Carrinho  -")
            println("- 3- Faturas   -")
            println("-              -")
            println("- 0- Sair      -")
            println("----------------")

            when(opc2){
                1 -> produtos(caminho)
                2 -> carrinho()
                3 -> faturas()
            }
        } while(opc2!=0)

    } else {
        println("Email ou senha incorretos.")
    }
}

fun criarCliente(caminho: String) {
    val clientesBD = Clientes(clientesFilename = caminho + "Clientes.csv")
    clientesBD.load()

    var nome: String
    var nif: String
    var morada: String
    var email: String
    var password: String
    var dados1 = mutableListOf<String>()

    println("---- Criar Conta ----")
    println("nome?")
    nome = readLine() ?: ""
    dados1.add(nome)

    println("nif?")
    nif = readLine() ?: ""
    dados1.add(nif)

    println("morada?")
    morada = readLine() ?: ""
    dados1.add(morada)

    println("Email?")
    email = readLine() ?: ""
    dados1.add(email)

    println("Password?")
    password = readLine() ?: ""
    dados1.add(password)

    clientesBD.criarConta(dados1)

}

//Temporariamente os produtos estão aqui depois tem de se alterar para aparecer nos clientes
fun adm() {
    var opc1:String =""
    do{
        println("Qual a chave para acessar a área administrador? Para voltar 'SAIR'")
        opc1 = readLine() ?: ""
        if (opc1 == "SAIR") {
            break
        }
    } while (opc1!="ADM")

}

fun produtos(caminho: String){
    val produtosBD = Produtos(produtosFilename = caminho + "Produtos.csv")
    produtosBD.load()
}

fun carrinho(){

}

fun faturas(){

}

fun addProduct(caminho: String){

}