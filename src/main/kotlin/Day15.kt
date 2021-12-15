import java.lang.Integer.MAX_VALUE
import java.util.PriorityQueue
import kotlin.math.abs

class Day15 : Puzzle {

    data class Point(val row: Int, val col: Int)

    override fun solvePartOne() {
        val grid = readInput()

        val path: List<Point> = aStar(grid, Point(0, 0), Point(grid.size - 1, grid[0].size - 1), heuristic)

        println(path.drop(1).sumOf { grid[it.row][it.col] })
    }

    override fun solvePartTwo() {
        val grid = readInputP2()

        val path: List<Point> = aStar(grid, Point(0, 0), Point(grid.size - 1, grid[0].size - 1), heuristic)

        println(path.drop(1).sumOf { grid[it.row][it.col] })
    }

    private fun aStar(
        grid: List<List<Int>>,
        start: Point,
        dest: Point,
        heuristic: (Point, Point) -> Int
    ): List<Point> {

        // estimatedScores[n] represents our current best guess as to
        // how short a path from start to finish can be if it goes through n.
        val estimatedScores: MutableMap<Point, Int> = hashMapOf()
        estimatedScores[start] = heuristic(start, dest)

        val openSet = PriorityQueue<Point> { p1, p2 ->
            if ((estimatedScores[p1] ?: MAX_VALUE) > (estimatedScores[p2] ?: MAX_VALUE)) 1 else -1
        }

        openSet.add(start)

        val cameFrom: MutableMap<Point, Point> = hashMapOf()

        // For node n, scores[n] is the cost of the cheapest path from start to n currently known.
        val scores: MutableMap<Point, Int> = hashMapOf()
        scores[start] = 0

        while (openSet.isNotEmpty()) {
            val current = openSet.remove()

            if (current == dest) {
                return reconstructPath(cameFrom, current)
            }

            directions.forEach { direction ->
                val neighbor = Point(current.row + direction.first, current.col + direction.second)

                if (checkBounds(grid, neighbor.row, neighbor.col)) {
                    val tentativeGScore: Int = scores[current]!! + grid[neighbor.row][neighbor.col]

                    if (tentativeGScore < scores.getOrDefault(neighbor, MAX_VALUE)) {
                        // This path to neighbor is better than any previous one. Record it!
                        cameFrom[neighbor] = current
                        scores[neighbor] = tentativeGScore
                        estimatedScores[neighbor] = tentativeGScore + heuristic(neighbor, dest)

                        if (!openSet.contains(neighbor)) {
                            openSet.add(neighbor)
                        }
                    }

                }
            }
        }

        // Open set is empty but goal was never reached
        throw Exception("Couldn't find the exit")
    }

    private fun reconstructPath(cameFrom: Map<Point, Point>, endPoint: Point): List<Point> {
        val totalPath = mutableListOf(endPoint)
        var current = endPoint
        while (cameFrom.containsKey(current)) {
            current = cameFrom[current]!!
            totalPath.add(0, current)
        }

        return totalPath
    }

    // Manhattan distance
    private val heuristic = { current: Point, dest: Point ->
        (abs(current.row - dest.row) + abs(current.col - dest.col))
    }

    private fun readInput(): List<List<Int>> {
        return readPuzzle("15-1.txt").map { line ->
            line.map { it.toString().toInt() }
        }
    }

    private fun readInputP2(): List<List<Int>> {
        val lines = mutableListOf<MutableList<Int>>()
        for (i in 0 until 5) {
            lines.addAll(readPuzzle("15-1.txt").map { line ->
                line.map {
                    var increment = it.toString().toInt() + i
                    if (increment > 9) {
                        increment %= 9
                    }
                    increment
                }.toMutableList()
            })
        }

        return lines.map { line ->
            val newLine = mutableListOf<Int>()
            for (i in 0 until 5) {
                newLine.addAll(line.map {
                    var increment = it.toString().toInt() + i
                    if (increment > 9) {
                        increment %= 9
                    }
                    increment
                })
            }
            newLine
        }
    }
}

