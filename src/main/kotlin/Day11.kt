class Day11 : Puzzle {

    private var directions =
        listOf(Pair(1, 0), Pair(-1, 0), Pair(0, 1), Pair(0, -1), Pair(1, 1), Pair(-1, 1), Pair(1, -1), Pair(-1, -1))


    override fun solvePartOne() {
        var jellys = readInput()
        var numFlashes = 0

        for (i in 0..1) {
            val nextStep = jellys.map { row ->
                row.map { it + 1 }.toMutableList()
            }

            val visited = mutableSetOf<Pair<Int, Int>>()

            for (row in jellys.indices) {
                for (col in jellys[row].indices) {
                    if (nextStep[row][col] > 9) {
                        bfs(nextStep, row to col, visited)
                    }
                }
            }

            jellys = nextStep.map { row -> row.map { if (it > 9) 0 else it }}

            printMatrix(jellys)
            println("==============")
        }

        println(numFlashes)
    }

    override fun solvePartTwo() {
    }

    private fun bfs(
        jellys: List<MutableList<Int>>,
        point: Pair<Int, Int>,
        visited: MutableSet<Pair<Int, Int>> = mutableSetOf()
    ) {

        val queue: MutableList<Pair<Int, Int>> = mutableListOf(point)
        while (queue.isNotEmpty()) {
            // Dequeue a node from queue
            val node = queue.removeAt(0)

            if (!visited.contains(node)) {
                if (jellys[node.first][node.second] > 9) {
                    directions.forEach {
                        if (checkBounds(jellys, node.first + it.first, node.second + it.second)) {
                            jellys[node.first + it.first][node.second + it.second]++
                            queue.add(node.first + it.first to node.second + it.second)
                        }
                    }
                }
                // Mark the dequeued node as visited
                visited.add(node)
            }
        }
    }

    private fun <T> checkBounds(
        map: List<List<T>>,
        newRow: Int,
        newCol: Int
    ) = newRow >= 0 && newRow < map.size && newCol >= 0 && newCol < map[newRow].size

    private fun readInput(): List<List<Int>> {
        return readPuzzle("11-1.txt").map { line ->
            line.map { it.digitToInt() }
        }
    }
}

