package service

import java.io.File

class EncodeFile {
    fun encodeFile(shiftCount: Int, inputFilename: String, outputFileName: String, decode: Boolean) {
        println("Lets encode $inputFilename")
        val reader = File(inputFilename).bufferedReader()
        val outputFile = File(outputFileName)
        val writeBuffer = outputFile.bufferedWriter()
        var line: String?
        do {
            line = reader.readLine()
            line?.let {
                writeBuffer.write(it)
                writeBuffer.newLine()
            }
        } while (line != null)
        writeBuffer.close()
    }
}
