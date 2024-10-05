package service

import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.config.inject.ConfigProperty

@ApplicationScoped
class ShiftCharacters(
    @ConfigProperty(name = "shift.start")
    val start: Int,
    @ConfigProperty(name = "shift.end")
    val end: Int,
) {

// First attempt, seemed to work, when testing with BMP because I did not trigger wrap around
//    fun shiftCharacters(line: String, shiftCount: Int): String {
//        val modulus = end - start + 1 // Adding 1 to since the charset start with 0
//        return line.map { char ->
//            if (char.code in start..end) {
//                ((char.code + shiftCount) % modulus).toChar()
//            } else {
//                char
//            }
//        }.joinToString("")
//    }

    fun shiftCharacters(line: String, shiftCount: Int): String {
        val characterSetLength = end - start + 1 // Adding 1 so we don't forget the first character
        return line.map { char ->
            if (char.code in start..end) {
                // Convert the Unicode code point to a 0-based range
                val transformCharCodeToZeroBased = char.code - start

                // Apply the shift value. The result could now be negative or larger than the zero-based character set range.
                val shiftedIndex = transformCharCodeToZeroBased + shiftCount

                // Using modulus of characterSetLength to get the remainder, either a positive or negative number
                // that will be within the zero-based character set
                val wrappedIndex = shiftedIndex % characterSetLength

                // By adding the character set length we ensure that the result is non-negative,
                // but it will be above the zero-based character set, if the shiftCount is positive.
                // By taking modulus of characterSetLength again, we get the remainder within the zero-based character set
                val nonNegativeCharCodeWithinZeroBasedCharacterSet = (wrappedIndex + characterSetLength) % characterSetLength

                // Convert the zero-based range back to Unicode code point by adding the start value for the character set
                val unicodeBasedShiftedCharCode = nonNegativeCharCodeWithinZeroBasedCharacterSet + start

                // Convert the Unicode code point to a Char
                unicodeBasedShiftedCharCode.toChar()
            } else {
                char
            }
        }.joinToString("")
    }
}
