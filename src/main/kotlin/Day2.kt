class Day2 : Puzzle() {

    override fun solvePartOne() {
        val commands = readInput()

        var distance = 0
        var depth = 0

        commands.forEach { command ->
            when (command.first) {
                "forward" -> distance += command.second
                "up" -> depth -= command.second
                "down" -> depth += command.second
            }
        }

        println(distance * depth)
    }

    override fun solvePartTwo() {
        val commands = readInput()

        var distance = 0
        var depth = 0
        var aim = 0

        commands.forEach { command ->
            when (command.first) {
                "forward" -> {
                    distance += command.second
                    depth += (aim * command.second)
                }
                "up" -> aim -= command.second
                "down" -> aim += command.second
            }
        }

        println(distance * depth)
    }

    private fun readInput(): List<Pair<String, Int>> {
        val commands = readPuzzle("1-2.txt")
            .flatMap { line ->
                line.split(" ")
                    .windowed(2) { Pair(it[0], Integer.parseInt(it[1])) }
            }
        return commands
    }

}
