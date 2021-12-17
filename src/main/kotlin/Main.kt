import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val puzzle: Puzzle = Day17()

    val time = measureTimeMillis {
        if (args.isNotEmpty() && args[0] == "p2") {
            puzzle.solvePartTwo()
        } else {
            puzzle.solvePartOne()
        }
    }

    println("\nTook $time ms")
}
