fun main(args: Array<String>) {
    val puzzle: Puzzle = Day10()

    if (args.isNotEmpty() && args[0] == "p2") {
        puzzle.solvePartTwo()
    }
    else {
        puzzle.solvePartOne()
    }
}
