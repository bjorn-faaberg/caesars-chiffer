import io.quarkus.runtime.QuarkusApplication
import io.quarkus.runtime.annotations.QuarkusMain
import jakarta.inject.Inject
import service.EncodeFile
import java.io.File

@QuarkusMain
class CaesarsChifferMain(
    @Inject
    private var encodeFile: EncodeFile,
) : QuarkusApplication {
    override fun run(vararg args: String): Int {
        validateArgs(args)?.let { validatedParameters ->
            if (!validatedParameters.inputFile.exists()) {
                println("inputfile ${validatedParameters.inputFile} not found")
                return 1
            }
            encodeFile.encodeFile(validatedParameters)
        }
        return 0
    }

    private fun validateArgs(args: Array<out String>): ValidatedParameters? {
        var decode = false
        var inputFile: String? = null
        var outputFile: String? = null
        var shiftCount: Int? = null

        when (args.size) {
            6, 7 -> {
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
                return null
            }
        }
        return if (shiftCount == null || inputFile == null || outputFile == null || (decode && args.size == 6)) {
            parameterValidationOutput()
            return null
        } else ValidatedParameters(
            inputFile = File(inputFile),
            outputFile = File(outputFile),
            shiftCount = if (decode) shiftCount.unaryMinus() else shiftCount
        )
    }

    private fun parameterValidationOutput() =
        println("Usage: --shiftcount <shiftcount as integer> --inputfile <inputfile> --outputfile <outputfile> [--decode]")
}
