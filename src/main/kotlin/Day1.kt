class Day1 : Puzzle() {

    override fun solvePartOne() {
        val lines = readPuzzle("1-1.txt").map { Integer.parseInt(it) }

        findNumIncreases(lines)
    }

    override fun solvePartTwo() {
        val lines = readPuzzle("1-1.txt").map { Integer.parseInt(it) }

        val triples = mutableListOf<Int>()
        for (i in 0 until lines.size - 2) {
            triples.add(lines[i] + lines[i + 1] + lines[i + 2])
        }

        findNumIncreases(triples)
    }

    private fun findNumIncreases(lines: List<Int>) {
        var curNum = 0
        var count = -1

        lines.forEach {
            if (it > curNum) {
                count++
            }
            curNum = it
        }

        println(count)
    }
}
