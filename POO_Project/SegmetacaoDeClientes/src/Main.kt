fun main() {
    val caminho1: String = "C:\\Users\\didic\\Documents\\ctesp\\2ºSemestre\\POO_Project\\SegmetacaoDeClientes\\src\\"
    println("Segmentação de Clientes")


    val clientesBD = Clientes(clientesFilename = caminho1 + "Clientes.csv")
    clientesBD.load()

}




//Problemas
//P1 Como eu crio os grupos? (De forma automatica)?
//P2 Como eu aloco um cliente num grupo?

//Exemplo:
// cliente faz 6 compras num mes mas compra pouco, outro faz 4 compras e é apenas comida e
// outro faz apenas uma compra paratodo o mes. Entao cada um deles estará num grupo, caso já exista um grupo
// semelhante ele será adicionado a esse grupo