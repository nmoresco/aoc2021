fun main(args: Array<String>) {
    val puzzle: Puzzle = Day7()

    if (args.isNotEmpty() && args[0] == "p2") {
        puzzle.solvePartTwo()
    }
    else {
        puzzle.solvePartOne()
    }
}
