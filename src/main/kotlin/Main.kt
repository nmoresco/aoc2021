fun main(args: Array<String>) {
    val puzzle: Puzzle = Day12()

    if (args.isNotEmpty() && args[0] == "p2") {
        puzzle.solvePartTwo()
    }
    else {
        puzzle.solvePartOne()
    }
}
