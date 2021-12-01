import java.io.File

fun readPuzzle(fileName: String): List<String> =
    File("/Users/nick.moresco/Code/advent/2021/src/main/resources/$fileName").useLines { it.toList() }
