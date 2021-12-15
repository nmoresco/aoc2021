import java.io.File

var directions = listOf(Pair(1, 0), Pair(-1, 0), Pair(0, 1), Pair(0, -1))
var directionsWithDiagonals = listOf(Pair(1, 0), Pair(-1, 0), Pair(0, 1), Pair(0, -1), Pair(1, 1), Pair(-1, 1), Pair(1, -1), Pair(-1, -1))

fun readPuzzle(fileName: String): List<String> =
    File("/Users/nick.moresco/Code/advent/2021/src/main/resources/$fileName").useLines { it.toList() }

// Will we need this? We'll seeeee
fun <T> dfs(
    map: List<List<T>>,
    point: Pair<Int, Int>,
    baseCase: (Pair<Int, Int>) -> Boolean,
    visited: MutableSet<Pair<Int, Int>> = mutableSetOf()
): Set<Pair<Int, Int>> {

    val row = point.first
    val col = point.second

    if (baseCase(point)) {
        return emptySet()
    }

    visited.add(Pair(row, col))

    directions.forEach {
        val newRow = row + it.first
        val newCol = col + it.second

        if (!visited.contains(Pair(newRow, newCol)) && checkBounds(map, newRow, newCol)) {
            visited.addAll(dfs(map, Pair(newRow, newCol), baseCase, visited))
        }
    }

    return visited
}

fun <T> checkBounds(
    map: List<List<T>>,
    newRow: Int,
    newCol: Int
) = newRow >= 0 && newRow < map.size && newCol >= 0 && newCol < map[newRow].size

fun <T> printMatrix(matrix: List<List<T>>) {
    for (row in matrix) {
        for (col in row) {
            print("$col")

            if (col is String && col.isEmpty()) {
                print(". ")
            }
        }
        println()
    }
}
