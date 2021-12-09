class Day9 : Puzzle {

    private var directions = listOf(Pair(1, 0), Pair(-1, 0), Pair(0, 1), Pair(0, -1))

    override fun solvePartOne() {
        val map = readInput()

        val lowPoints = calculateLowPoints(map)

        println(lowPoints.map { map[it.first][it.second] }.sumOf { it + 1 })
    }

    private fun calculateLowPoints(map: List<List<Int>>): List<Pair<Int, Int>> {
        val lowPoints = mutableListOf<Pair<Int, Int>>()

        for (row in map.indices) {
            for (col in map[row].indices) {
                val point = map[row][col]
                val isLowPoint = directions.all {
                    val newRow = row + it.first
                    val newCol = col + it.second

                    if (!checkBounds(map, newRow, newCol)) {
                        true
                    } else {
                        point < map[newRow][newCol]
                    }
                }

                if (isLowPoint) {
                    lowPoints.add(Pair(row, col))
                }
            }
        }
        return lowPoints
    }

    override fun solvePartTwo() {
        val map = readInput()
        val lowPoints = calculateLowPoints(map)

        val basins = lowPoints.map {
            dfs(map, it).size
        }

        println(basins.sorted().reversed().take(3).reduce { acc, int -> int * acc })
    }

    private fun dfs(
        map: List<List<Int>>,
        point: Pair<Int, Int>,
        visited: MutableSet<Pair<Int, Int>> = mutableSetOf()
    ): Set<Pair<Int, Int>> {

        val row = point.first
        val col = point.second
        val height = map[row][col]

        if (height == 9) {
            return emptySet()
        }

        visited.add(Pair(row, col))

        directions.forEach {
            val newRow = row + it.first
            val newCol = col + it.second

            if (!visited.contains(Pair(newRow, newCol)) && checkBounds(map, newRow, newCol)) {
                visited.addAll(dfs(map, Pair(newRow, newCol), visited))
            }
        }

        return visited
    }

    private fun <T> checkBounds(
        map: List<List<T>>,
        newRow: Int,
        newCol: Int
    ) = newRow >= 0 && newRow < map.size && newCol >= 0 && newCol < map[newRow].size

    private fun readInput(): List<List<Int>> {
        return readPuzzle("9-1.txt").map { line ->
            line.map { it.toString().toInt() }
        }
    }

}

