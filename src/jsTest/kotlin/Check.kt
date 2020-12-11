import kotlin.test.*
import com.example.base64.*

class Check {

    @Test
    fun main() {
        decodeFromString("YXNkZg==")
            .then { v -> println("Decoded:$v") }
            .catch { v -> println("Rejected:$v") }
    }

}