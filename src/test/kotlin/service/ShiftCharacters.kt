package service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ShiftCharactersTest {

    private val bmpStart = 0
    private val bmpEnd = 65535

    @Test
    fun `shiftCharacters with BMP encodes and decodes common characters with shift 4`() {
        val originalPhrase = "AbC"
        val shift = 4
        val shiftCharacters = ShiftCharacters(bmpStart, bmpEnd)
        val encoded = shiftCharacters.shiftCharacters(originalPhrase, shift)
        val decoded = shiftCharacters.shiftCharacters(encoded, shift.unaryMinus())
        val expected = "EfG"
        assertThat(encoded).isEqualTo(expected)
        assertThat(decoded).isEqualTo(originalPhrase)
    }

    @Test
    fun `shiftCharacters with BMP encodes and decodes exotic with shift 8`() {
        val originalPhrase = "Мой распорядок дня"
        val shift = 8
        val shiftCharacters = ShiftCharacters(bmpStart, bmpEnd)
        val encoded = shiftCharacters.shiftCharacters(originalPhrase, shift)
        val decoded = shiftCharacters.shiftCharacters(encoded, shift.unaryMinus())

        assertThat(encoded).isNotEqualTo(originalPhrase)
        assertThat(decoded).isEqualTo(originalPhrase)
    }

    @Test
    fun `shiftCharacters with BMP encodes and decodes json with shift 3`() {
        val originalPhrase = """
            {
                "name": "John",
                "age": 30,
                "car": null
            }
        """.trimIndent()
        val shift = 3
        val shiftCharacters = ShiftCharacters(bmpStart, bmpEnd)
        val encoded = shiftCharacters.shiftCharacters(originalPhrase, shift)
        val decoded = shiftCharacters.shiftCharacters(encoded, shift.unaryMinus())

        assertThat(encoded).isNotEqualTo(originalPhrase)
        assertThat(decoded).isEqualTo(originalPhrase)
    }

    @Test
    fun `shiftCharacters with reduced char-set not starting with 0 encodes and decodes normal characters shift 4`() {
        val originalPhrase = "ABCXYZ"
        val shift = 4
        val start = 'A'.code
        val end = 'Z'.code
        val shiftCharacters = ShiftCharacters(start, end)
        val encoded = shiftCharacters.shiftCharacters(originalPhrase, shift)
        val expected = "EFGBCD"
        assertThat(encoded).isEqualTo(expected)
        val decoded = shiftCharacters.shiftCharacters(encoded, -shift)
        assertThat(decoded).isEqualTo(originalPhrase)
    }
    @Test
    fun `shiftCharacters with reduced char-set not starting with 0 encodes and decodes normal characters shift larger than the character set size`() {
        val originalPhrase = "ABCXYZ"
        val start = 'A'.code
        val end = 'Z'.code
        val shift = ((end - start + 1) * 3) + 4
        val shiftCharacters = ShiftCharacters(start, end)
        val encoded = shiftCharacters.shiftCharacters(originalPhrase, shift)
        val expected = "EFGBCD"
        assertThat(encoded).isEqualTo(expected)
        val decoded = shiftCharacters.shiftCharacters(encoded, -shift)
        assertThat(decoded).isEqualTo(originalPhrase)
    }

    @Test
    fun `shiftCharacters with reduced char-set not starting with 0 encodes and decodes normal characters shift negative beoynd the size of the character set`() {
        val originalPhrase = "ABCXYZ"
        val start = 'A'.code
        val end = 'Z'.code
        val shift = ((end - start + 1) * 3) - 4
        val shiftCharacters = ShiftCharacters(start, end)
        val encoded = shiftCharacters.shiftCharacters(originalPhrase, shift)
        val expected = "WXYTUV"
        assertThat(encoded).isEqualTo(expected)
        val decoded = shiftCharacters.shiftCharacters(encoded, -shift)
        assertThat(decoded).isEqualTo(originalPhrase)
    }
}
