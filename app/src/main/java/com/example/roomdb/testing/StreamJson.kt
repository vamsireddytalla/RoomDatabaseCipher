import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.File

fun main() {
    val objectMapper = ObjectMapper()
    val jsonFactory = JsonFactory()
    val jsonParser: JsonParser = jsonFactory.createParser(File("\\C:\\Users\\User\\Downloads\\large-file.json"))

    while (jsonParser.nextToken() != null) {
        val node: JsonNode = objectMapper.readTree(jsonParser)
        // Process each JSON node
        println("Node: $node")
    }

    jsonParser.close()
}
