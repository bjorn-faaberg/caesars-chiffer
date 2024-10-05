import java.io.File

data class ValidatedParameters(
    val inputFile : File,
    val outputFile : File,
    val shiftCount : Int,
)
