package service

import ValidatedParameters

class EncodeFile(
    private var shiftCharacters: ShiftCharacters,
) {
    fun encodeFile(validatedParameters: ValidatedParameters) {
        with(validatedParameters) {
            println("Lets encode $inputFile")
            val reader = inputFile.bufferedReader()
            val writeBuffer = outputFile.bufferedWriter()
            var line: String?
            do {
                line = reader.readLine()?.also {
                    writeBuffer.write(shiftCharacters.shiftCharacters(it, shiftCount))
                    writeBuffer.newLine()
                }
            } while (line != null)
            writeBuffer.close()
        }
    }
}
