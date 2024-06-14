import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CsvOrganizer(private val inputFilePath: String) {

    private val outputFileName = "C:\\Users\\gerem\\Downloads\\SupermarketDJ\\Supermarket1\\Supermarket\\src\\main\\kotlin\\ficheiros\\dados_organizados.csv"

   
    fun readCsv(): List<List<String>> {
        return File(inputFilePath).useLines { lines ->
            lines.map { line ->
               
                line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)".toRegex())
                   
                    .map { it.trim().removeSurrounding("\"").replace("/", "-") }
            }.toList()
        }
    }

    
    private fun organizeData(data: List<List<String>>): List<List<String>> {
        val header = data.first()
        val body = data.drop(1).filter { it.isNotEmpty() && it[0].isNotEmpty() }
        return listOf(header) + body.sortedBy { it.first().toIntOrNull() }
    }

    
    fun writeNewCsv() {
        val outputFile = File(outputFileName)
        if (outputFile.exists()) {
            outputFile.delete()
        }
        val data = readCsv()
        val organizedData = organizeData(data)
        writeCsv(organizedData, outputFile)
    }

   
    private fun writeCsv(organizedData: List<List<String>>, outputFile: File) {
        try {
            outputFile.bufferedWriter().use { writer ->
               
                val header = listOf("ID", "ID_Cliente", "Data", "Produto")
                writer.write(header.joinToString(","))
               
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

   
    private fun isValidData(data: List<List<String>>): Boolean {
        // Verifique se cada linha tem o número correto de campos.
        val expectedNumFields = 4 // Número previsto de campos
        return data.all { it.size == expectedNumFields }
    }

   
    private fun cleanData(data: List<List<String>>): List<List<String>> {
        val dateFormatterInput = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val dateFormatterOutput = DateTimeFormatter.ofPattern("yyyy-MM-dd")

       
        val idOccurrences = mutableMapOf<Int, Int>()

        return data.mapIndexed { index, line ->
          
            val mutableLine = line.toMutableList()

           
            val dataFieldIndex = 2
            if (mutableLine.size > dataFieldIndex) {
                val dataField = mutableLine[dataFieldIndex]
                val parsedDate = try {
                    LocalDate.parse(dataField, dateFormatterInput)
                } catch (e: Exception) {
                    null
                }
                if (parsedDate != null) {
                   
                    mutableLine[dataFieldIndex] = parsedDate.format(dateFormatterOutput)
                }
            }

           
            val idIndex = 0
            if (line.size > idIndex) {
                val id = line[idIndex].toIntOrNull()
                if (id != null) {
                    
                    val occurrences = idOccurrences.getOrDefault(id, 0)
                    idOccurrences[id] = occurrences + 1

                    
                    if (occurrences > 0) {
                       
                        mutableLine[idIndex] = findUniqueID(id, idOccurrences)
                    }
                }
            }

           
            mutableLine.toList()
        }
    }

   
    private fun findUniqueID(originalID: Int, idOccurrences: MutableMap<Int, Int>): String {
        var newID = originalID
        while (idOccurrences.containsKey(newID)) {
            newID++
        }
        idOccurrences[newID] = 1
        return newID.toString()
    }
}
