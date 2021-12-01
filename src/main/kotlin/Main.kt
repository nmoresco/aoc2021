fun main(args: Array<String>) {
    val puzzle: Puzzle = Day1()

    if (args.size > 1 && args[0] == "p2") {
        puzzle.solvePartOne()
    }
    else {
        puzzle.solvePartTwo()
    }
}
