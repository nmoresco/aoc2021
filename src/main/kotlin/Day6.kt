class Day6 : Puzzle {

    override fun solvePartOne() {
        var state = readInput()

        for (i in 0..79) {
            state = simulate(state)
        }

        println(state.values.sum())
    }

    override fun solvePartTwo() {
        var state = readInput()

        for (i in 0..255) {
            state = simulate(state)
        }

        println(state.values.sum())
    }

    private fun simulate(state: Map<Int, Long>): Map<Int, Long> {
        return state.flatMap { entry: Map.Entry<Int, Long> ->
            if (entry.key == 0) {
                // We introduce new fish here at 6 and 8. Add them to the existing pool at state[7] since the 7's are due to become 6's
                listOf(Pair(6, entry.value + (state[7] ?: 0)), Pair(8, entry.value))
            } else if (entry.key == 7 && state.containsKey(0)) {
                // We handled it already above
                listOf(null)
            } else {
                listOf(Pair(entry.key - 1, entry.value))
            }
        }.filterNotNull().toMap()
    }
}


// Data structure is the phase mapped to the number of fish in that phase
private fun readInput(): Map<Int, Long> {
    val map = HashMap<Int, Long>()

    readPuzzle("6-1.txt")
        .first()
        .split(",")
        .map { it.toInt() }
        .forEach {
            map[it] = map.getOrPut(it) { 0 } + 1
        }

    return map
}

