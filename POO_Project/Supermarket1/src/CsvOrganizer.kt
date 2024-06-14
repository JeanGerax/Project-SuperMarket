import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CsvOrganizer(private val inputFilePath: String) {

    private val outputFileName = "src\\main\\kotlin\\ficheiros\\dados_organizados.csv"

    // Lê o ficheiro CSV e devolve uma lista de listas de cadeias de caracteres
    fun readCsv(): List<List<String>> {
        return File(inputFilePath).useLines { lines ->
            lines.map { line ->
                // Divida a linha utilizando uma expressão regular que trate corretamente as vírgulas invertidas e as vírgulas
                line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)".toRegex())
                    // Remova as vírgulas invertidas à volta de cada campo e normalize o formato da data.
                    .map { it.trim().removeSurrounding("\"").replace("/", "-") }
            }.toList()
        }
    }

    // Organize os dados CSV
    private fun organizeData(data: List<List<String>>): List<List<String>> {
        val header = data.first()
        val body = data.drop(1).filter { it.isNotEmpty() && it[0].isNotEmpty() }
        return listOf(header) + body.sortedBy { it.first().toIntOrNull() }
    }

    // Grava os dados organizados num novo ficheiro CSV, eliminando qualquer ficheiro existente com o mesmo nome.
    fun writeNewCsv() {
        val outputFile = File(outputFileName)
        if (outputFile.exists()) {
            outputFile.delete()
        }
        val data = readCsv()
        val organizedData = organizeData(data)
        writeCsv(organizedData, outputFile)
    }

    // Escreva os dados organizados num ficheiro CSV.
    private fun writeCsv(organizedData: List<List<String>>, outputFile: File) {
        try {
            outputFile.bufferedWriter().use { writer ->
                // Escrever os títulos
                val header = listOf("ID", "ID_Cliente", "Data", "Produto")
                writer.write(header.joinToString(","))
                //writer.newLine()
                // Escribir los datos organizados
                organizedData.forEach { line ->
                    writer.write(line.joinToString(","))
                    writer.newLine()
                }
            }
            println("Los datos organizados han sido guardados en: ${outputFile.absolutePath}")
        } catch (e: Exception) {
            println("Error al escribir el archivo CSV: ${e.message}")
        }
    }

    // Processa o ficheiro CSV: lê, organiza e escreve os dados
    fun processCsv() {
        val data = readCsv()
        if (!isValidData(data)) {
            println("Los datos de entrada no están bien organizados. Organizando los datos...")
            val cleanedData = cleanData(data)
            val organizedData = organizeData(cleanedData)
            writeCsv(organizedData, File(outputFileName))
        } else {
            writeNewCsv()
        }
    }

    // Verifica a validade dos dados de entrada
    private fun isValidData(data: List<List<String>>): Boolean {
        // Verifique se cada linha tem o número correto de campos.
        val expectedNumFields = 4 // Número previsto de campos
        return data.all { it.size == expectedNumFields }
    }

    // Efectua a limpeza e normalização dos dados
    private fun cleanData(data: List<List<String>>): List<List<String>> {
        val dateFormatterInput = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val dateFormatterOutput = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        // mapa para manter o registo dos IDs e das suas ocorrências
        val idOccurrences = mutableMapOf<Int, Int>()

        return data.mapIndexed { index, line ->
            // Converta a lista de cadeias de caracteres numa lista mutável
            val mutableLine = line.toMutableList()

            // Normalize o formato da data (de "dd-MM-aaaa" para "aaaa-MM-dd")
            val dataFieldIndex = 2
            if (mutableLine.size > dataFieldIndex) {
                val dataField = mutableLine[dataFieldIndex]
                val parsedDate = try {
                    LocalDate.parse(dataField, dateFormatterInput)
                } catch (e: Exception) {
                    null
                }
                if (parsedDate != null) {
                    // Formate a data no novo formato que pretende.
                    mutableLine[dataFieldIndex] = parsedDate.format(dateFormatterOutput)
                }
            }

            // Verifique e corrija os duplicados no campo ID
            val idIndex = 0
            if (line.size > idIndex) {
                val id = line[idIndex].toIntOrNull()
                if (id != null) {
                    // Aumente a ocorrência do ID atual
                    val occurrences = idOccurrences.getOrDefault(id, 0)
                    idOccurrences[id] = occurrences + 1

                    // Corrija o ID se houver uma ocorrência anterior
                    if (occurrences > 0) {
                        // Atribua um novo ID único com base na organização, da mais pequena para a maior
                        mutableLine[idIndex] = findUniqueID(id, idOccurrences)
                    }
                }
            }

            // Devolver lista mutável convertida de novo em lista imutável
            mutableLine.toList()
        }
    }

    // Encontre um novo ID único com base na organização mais baixa para a mais alta.
    private fun findUniqueID(originalID: Int, idOccurrences: MutableMap<Int, Int>): String {
        var newID = originalID
        while (idOccurrences.containsKey(newID)) {
            newID++
        }
        idOccurrences[newID] = 1
        return newID.toString()
    }
}