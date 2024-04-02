fun main() {
    println("Bem Vindo ao SuperMarket!")

    // Caminho para o ficheiro CSV de clientes (Por enquanto apenas funciona assim)
    val caminho: String = "C:\\Users\\didic\\Documents\\ctesp\\2ºSemestre\\POO_Project\\Supermarket1\\src\\"

    var opc: Int
    do {
        println("--------MENU----------")
        println("- 1- Login Clientes  -")
        println("- 2- Criar Conta     -")
        println("- 3- Administração   -")
        println("----------------------")
        println("")
        println("Digite um número diferente de zero (ou 0 para sair): ")

        opc = readLine()?.toInt() ?: 0

        when (opc) {
            1 -> loginCliente(caminho)
            2 -> criarCliente(caminho)
            3 -> adm()
        }
    } while (opc != 0)
    println("Adeus! Obrigado por visitar o nosso Supermarket!")
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

fun adm() {

}
