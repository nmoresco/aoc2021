fun main(args: Array<String>) {
    val puzzle: Puzzle = Day13()

    if (args.isNotEmpty() && args[0] == "p2") {
        puzzle.solvePartTwo()
    }
    else {
        puzzle.solvePartOne()
    }
}
