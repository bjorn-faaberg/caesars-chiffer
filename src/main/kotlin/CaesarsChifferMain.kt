import io.quarkus.runtime.QuarkusApplication
import io.quarkus.runtime.annotations.QuarkusMain
import service.EncodeFile
import java.io.File

@QuarkusMain
class CaesarsChifferMain : QuarkusApplication {
    val encodeFile = EncodeFile()
    override fun run(vararg args: String): Int {
        var decode = false
        var inputFile: String? = null
        var outputFile: String? = null
        var shiftCount: Int? = null

        when (args.size) {
            5, 6 -> {
                for (i in args.indices) {
                    when (args[i]) {
                        "--decode" -> decode = true
                        "--inputfile" -> inputFile = args.getOrNull(i + 1)
                        "--outputfile" -> outputFile = args.getOrNull(i + 1)
                        "--shiftcount" -> shiftCount = args.getOrNull(i + 1)?.toIntOrNull()
                    }
                }
            }

            else -> {
                parameterValidationOutput()
                println("Usage: --inputfile <inputfile> --outputfile <outputfile> [--decode]")
                return 1
            }
        }
        if (shiftCount == null || inputFile == null || outputFile == null || (decode && args.size == 5)) {
            parameterValidationOutput()
            return 1
        } else {
            val file = File(inputFile)
            if (!file.exists()) {
                println("inputfile $inputFile not found")
                return 1
            }
            encodeFile.encodeFile(shiftCount, inputFile, outputFile, decode)
        }
        return 0
    }

    private fun parameterValidationOutput() =
        println("Usage: --shiftcount <shiftcount as integer> --inputfile <inputfile> --outputfile <outputfile> [--decode]")
}
