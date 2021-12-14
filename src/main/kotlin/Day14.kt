import kotlin.math.max

class Day14 : Puzzle {

    data class Polymers(val template: String, val pairs: Map<String, Char>)

    override fun solvePartOne() {
        val input = readInput()
        var polymer = input.template

        for (i in 0 until 10) {
            val next = polymer
                .windowed(2, 1)
                .joinToString("") { sequence ->
                    "" + sequence[0] + (input.pairs[sequence] ?: throw Exception("Sequence not found! $sequence"))
                }
            polymer = next + polymer.last()
        }

        val sortedPolymers: List<List<Char>> = polymer.groupBy { it }.values.sortedBy { it.size }
        println(sortedPolymers.last().size - sortedPolymers.first().size)
    }

    override fun solvePartTwo() {
        val input = readInput()
        val polymer = input.template

        val letterFrequencies: MutableMap<Char, Long> = polymer
            .groupBy { it }
            .mapValues { it.value.size.toLong() }
            .toMutableMap()

        var pairFrequencies: Map<String, Long> = polymer
            .windowed(2, 1)
            .map { "" + it[0] + it[1] }
            .groupBy { it }
            .mapValues { it.value.size.toLong() }

        for (i in 0 until 40) {
            val nextFrequencies = pairFrequencies.toMutableMap()

            pairFrequencies.forEach { (pair, num) ->
                val newElement = input.pairs[pair] ?: throw Exception("Sequence not found! $pair")
                // Add the new element to the map for each time the original pair appeared
                letterFrequencies[newElement] = letterFrequencies.getOrPut(newElement) {0} + num

                // The original pair gets blown away.
                nextFrequencies[pair] = max(nextFrequencies.getOrPut(pair) {0} - num, 0)

                // Add the new pairs to the map for each time the original pair appeared.
                nextFrequencies["" + pair[0] + newElement] = nextFrequencies.getOrPut("" + pair[0] + newElement) {0} + num
                nextFrequencies["" + newElement + pair[1]] = nextFrequencies.getOrPut("" + newElement + pair[1]) {0} + num
            }

            pairFrequencies = nextFrequencies
        }

        val sortedPolymers = letterFrequencies.entries.sortedBy { it.value }
        println(sortedPolymers.last().value - sortedPolymers.first().value)
    }

    private fun readInput(): Polymers {
        val lines = readPuzzle("14-1.txt").filterNot { it.isEmpty() }

        val template = lines.first()

        val pairs = lines.drop(1)
            .flatMap { line ->
                line.split("->")
                    .windowed(2, 2)
                    .map { it[0].trim() to it[1].trim()[0] }
            }
            .associate { it }

        return Polymers(template, pairs)
    }
}
