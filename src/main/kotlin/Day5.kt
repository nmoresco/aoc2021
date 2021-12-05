class Day5 : Puzzle {

    data class Point(val x: Int, val y: Int)

    override fun solvePartOne() {
        val lines = readInput().filter {
            it.first.x == it.second.x || it.first.y == it.second.y
        }

        val map = HashMap<Point, Int>()

        lines.forEach { line ->
            walkLine(line, map)
        }

        println(map.values.count { it > 1 })
    }

    override fun solvePartTwo() {
        val lines = readInput()

        val map = HashMap<Point, Int>()

        lines.forEach { line ->
            walkLine(line, map)
        }

        println(map.values.count { it > 1 })
    }

    private fun walkLine(segment: Pair<Point, Point>, map: HashMap<Point, Int>) {
        val dx = (segment.second.x - segment.first.x).coerceIn(-1, 1)
        val dy = (segment.second.y - segment.first.y).coerceIn(-1, 1)

        var curX = segment.first.x
        var curY = segment.first.y
        map[Point(curX, curY)] = map.getOrPut(Point(curX, curY)) { 0 } + 1

        while (curX != segment.second.x || curY != segment.second.y) {
            curX += dx
            curY += dy
            map[Point(curX, curY)] = map.getOrPut(Point(curX, curY)) { 0 } + 1
        }
    }

    private fun readInput(): List<Pair<Point, Point>> {
        return readPuzzle("5-1.txt")
            .flatMap { line ->
                line.split("->")
                    .map { point ->
                        val split: List<String> = point.trim().split(",")
                        Point(split[0].toInt(), split[1].toInt())
                    }
                    .windowed(2, 2)
                    .map { Pair(it[0], it[1]) }
            }
    }

}
