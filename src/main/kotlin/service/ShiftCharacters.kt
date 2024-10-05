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
    fun shiftCharacters(line: String, shiftCount: Int): String =
        line.map { char ->
            if (char.code in start..end) {
                ((char.code + shiftCount) % end).toChar()
            } else {
                char
            }
        }.joinToString("")
}
