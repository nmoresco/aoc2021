class Day11 : Puzzle {

    private var directions = directionsWithDiagonals

    override fun solvePartOne() {
        var jellys = readInput()
        var numFlashes = 0

        for (i in 0..99) {
            val pair = step(jellys)
            jellys = pair.first
            numFlashes += pair.second
        }

        println(numFlashes)
    }

    override fun solvePartTwo() {
        var jellys = readInput()
        var numFlashes = 0
        var step = 0

        while (numFlashes != jellys[0].size * jellys.size) {
            val pair = step(jellys)
            jellys = pair.first
            numFlashes = pair.second
            step++
        }

        println(step)
    }

    private fun step(jellys: List<List<Int>>): Pair<List<List<Int>>, Int> {
        var numFlashes = 0
        val nextStep = jellys.map { row ->
            row.map { it + 1 }.toMutableList()
        }

        var foundFlash = true

        // Keep going until we don't get any new flashes
        while (foundFlash) {
            foundFlash = false
            val nextFlashes = propagateFlashes(nextStep)
            if (nextFlashes > 0) {
                foundFlash = true
                numFlashes += nextFlashes
            }
        }

        return Pair(nextStep, numFlashes)
    }

    private fun propagateFlashes(jellys: List<MutableList<Int>>): Int {
        var numFlashes = 0
        for (row in jellys.indices) {
            for (col in jellys[row].indices) {
                if (jellys[row][col] > 9) {
                    directions.forEach {
                        if (checkBounds(jellys, row + it.first, col + it.second)
                            && jellys[row + it.first][col + it.second] > 0) {
                            jellys[row + it.first][col + it.second]++
                        }
                    }
                    numFlashes++
                    jellys[row][col] = 0
                }
            }
        }

        return numFlashes
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

