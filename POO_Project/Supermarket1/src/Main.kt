fun main() {
    println("Bem Vindo ao SuperMarket!")

    // Caminho para o arquivo CSV de clientes (Por enquanto apenas funciona assim)
    val caminho: String = "C:\\Users\\didic\\Documents\\ctesp\\2ºSemestre\\POO_Project\\Supermarket1\\src\\"

    var opc: Int
    do {
        println("-------MENU---------")
        println("- 1- Clientes      -")
        println("- 2- Administração -")
        println("--------------------")
        println("")
        println("Digite um número diferente de zero (ou 0 para sair): ")

        opc = readLine()?.toInt() ?: 0

        when (opc) {
            1 -> loginCliente(caminho)
            2 -> adm()
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

fun adm() {

}
