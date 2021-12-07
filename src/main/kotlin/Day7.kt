import kotlin.math.abs

class Day7 : Puzzle {

    override fun solvePartOne() {
        val numbers = readInput()

        val max = numbers.maxOrNull()
        val min = numbers.minOrNull()

        val possibilities = (min!!..max!!).toList()

        val solution = possibilities.minOfOrNull { target ->
            numbers.sumOf { abs(target - it) }
        }

        println(solution)
    }

    override fun solvePartTwo() {
        val numbers = readInput()

        val max = numbers.maxOrNull()
        val min = numbers.minOrNull()

        val possibilities = (min!!..max!!).toList()

        val solution = possibilities.minOfOrNull { target ->
            numbers.sumOf {
                val distance = abs(target - it)
                // It's a triangle number!
                (distance * (distance + 1)) / 2
            }
        }

        println(solution)
    }

    private fun readInput(): List<Int> {
        return readPuzzle("7-1.txt").first().split(",").map { it.toInt() }
    }
}

