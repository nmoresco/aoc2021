class Day1 : Puzzle {

    override fun solvePartOne() {
        val nums = readPuzzle("1-1.txt").map { Integer.parseInt(it) }

        printNumIncreases(nums)
    }

    override fun solvePartTwo() {
        val nums = readPuzzle("1-1.txt").map { Integer.parseInt(it) }

        printNumIncreases(nums.windowed(3).map { it[0] + it[1] + it[2] })
    }

    private fun printNumIncreases(nums: List<Int>) {
        // windowed provides a sliding view of the list, very handy!
        println(nums.windowed(2).count { it[0] < it[1] })
    }
}
