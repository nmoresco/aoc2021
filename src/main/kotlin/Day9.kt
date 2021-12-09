class Day9 : Puzzle {

    private var directions = listOf(Pair(1, 0), Pair(-1, 0), Pair(0, 1), Pair(0, -1))

    override fun solvePartOne() {
        val map = readInput()

        val lowPoints = mutableListOf<Int>()

        for (row in map.indices) {
            for (col in map[row].indices) {
                val point = map[row][col]

                // check north
                if (row > 0 && point >= map[row - 1][col]) {
                    continue
                }

                // check east
                if (col < map[row].size - 1 && point >= map[row][col + 1]) {
                    continue
                }

                // check south
                if (row < map.size - 1 && point >= map[row + 1][col]) {
                    continue
                }

                // check west
                if (col > 0 && point >= map[row][col - 1]) {
                    continue
                }

                lowPoints.add(point)
            }
        }

        println(lowPoints.sumOf { it + 1 })
    }

    override fun solvePartTwo() {
        val map = readInput()
        val basins = mutableListOf<Int>()

        val visited = mutableSetOf<Pair<Int, Int>>()
        val currentRun = mutableSetOf<Pair<Int, Int>>()

        for (row in map.indices) {
            for (col in map[row].indices) {
                if (!visited.contains(Pair(row, col))) {
                    // We're in a new area
                    basins.add(currentRun.size)
                    currentRun.clear()
                    currentRun.addAll(dfs(map, Pair(row, col), currentRun))
                    visited.addAll(currentRun)
                }
            }
        }

        println(basins.filter { it > 0 }.sorted().reversed().take(3).reduce { acc, int -> int * acc })
    }

    private fun dfs(
        map: List<List<Int>>,
        point: Pair<Int, Int>,
        visited: MutableSet<Pair<Int, Int>>
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

            if (!visited.contains(Pair(newRow, newCol))) {
                if (newRow >= 0 && newRow < map.size && newCol >= 0 && newCol < map[row].size) {
                    visited.addAll(dfs(map, Pair(newRow, newCol), visited))
                }
            }
        }

        return visited
    }

    private fun readInput(): List<List<Int>> {
        return readPuzzle("9-1.txt").map { line ->
            line.map { it.toString().toInt() }
        }
    }

}

