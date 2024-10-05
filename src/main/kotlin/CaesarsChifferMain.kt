import io.quarkus.runtime.QuarkusApplication
import io.quarkus.runtime.annotations.QuarkusMain


@QuarkusMain
class HelloWorldMain : QuarkusApplication {
    @Throws(Exception::class)
    override fun run(vararg args: String): Int {
        println("Hello " + args[0])
        return 0
    }
}
