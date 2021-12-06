import java.io.File

fun readPuzzle(fileName: String): List<String> =
    File("/Users/nick.moresco/Code/advent/2021/src/main/resources/$fileName").useLines { it.toList() }

fun <T> printMatrix(matrix: List<List<T>>) {
    for (row in matrix) {
        for (col in row) {
            print("$col ")

            if (col is String && col.isEmpty()) {
                print(". ")
            }
        }
        println()
    }
}
