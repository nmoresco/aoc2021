class Day8 : Puzzle {

    private val segmentLength = mapOf(
        2 to 1, 4 to 4, 3 to 7, 7 to 8
    )

    override fun solvePartOne() {
        println(readInput().map { it.second }.flatten().count { segmentLength.containsKey(it.size) })
    }

    override fun solvePartTwo() {
        val lines = readInput()

        val sum = lines.sumOf { line ->
            // Let's first identify the unique length segments (1, 4, 7, 8)
            val segments: MutableMap<Int, Set<Char>> = line.first.associateBy { segmentLength[it.size] ?: -1 }.toMutableMap()

            // Consider all the 6-length segments (0, 6, 9)
            val sixLengthSegments = line.first.filter { it.size == 6 }.toMutableList()

            // 6 is the only 6-length segment that doesn't completely overlap with 1
            segments[6] = sixLengthSegments.first { segments[1]!!.subtract(it).isNotEmpty() }
            sixLengthSegments.remove(segments[6])

            // After that, 4 and 9 totally overlap, so we can identify which is 9 that way
            segments[9] = sixLengthSegments.first { segments[4]!!.subtract(it).isEmpty() }
            sixLengthSegments.remove(segments[9])

            // The remaining one is 0
            segments[0] = sixLengthSegments.first()

            // Now let's consider all the 5-length segments (2, 3, 5)
            val fiveLengthSegments = line.first.filter { it.size == 5 }.toMutableList()

            // 2 is the only 5-length segment that doesn't completely overlap with 9
            segments[2] = fiveLengthSegments.first { it.subtract(segments[9]!!).isNotEmpty() }
            fiveLengthSegments.remove(segments[2])

            // After that, 5 and 6 totally overlap.
            segments[5] = fiveLengthSegments.first { it.subtract(segments[6]!!).isEmpty() }
            fiveLengthSegments.remove(segments[5])

            // And the final one is 3!
            segments[3] = fiveLengthSegments.first()

            // We have them all! Let's translate the output and sum em up.
            val reversed: Map<Set<Char>, Int> = segments.entries.associateBy({ it.value }) { it.key }
            val map = line.second.map { '0' + reversed[it]!! }
            map.joinToString("").toInt()
        }

        println(sum)
    }

    private fun readInput(): List<Pair<List<Set<Char>>, List<Set<Char>>>> {
        return readPuzzle("8-1.txt").map { line ->
            val split = line.split("|")
            Pair(split[0].trim().split(" ").map { it.toSet() },
                split[1].trim().split(" ").map { it.toSet() })
        }
    }

}

