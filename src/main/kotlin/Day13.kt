class Day13 : Puzzle {

    data class Fold(val coord: Int, val isVertical: Boolean)

    override fun solvePartOne() {
        val input = readInput()

        var map = input.first

        val width = input.first.keys.maxOf { it.first }
        val height = input.first.keys.maxOf { it.second }

        input.second.first().let { fold ->
            map = fold(map.toMutableMap(), width, height, fold)
        }

        println(map.values.size)
    }

    override fun solvePartTwo() {
        val input = readInput()

        var map = input.first
        val folds = input.second

        var width = map.keys.maxOf { it.first }
        var height = map.keys.maxOf { it.second }

        folds.forEach { fold ->
            map = fold(map.toMutableMap(), width, height, fold)
            width = map.keys.maxOf { it.first }
            height = map.keys.maxOf { it.second }
        }

        for (row in 0..height) {
            for (col in 0..width) {
                print(map.getOrDefault(col to row, '.'))
            }
            println()
        }
    }

    private fun fold(
        map: MutableMap<Pair<Int, Int>, Char>,
        width: Int,
        height: Int,
        fold: Fold
    ): Map<Pair<Int, Int>, Char> {
        if (fold.isVertical) {
            for (row in fold.coord..height) {
                for (col in 0..width) {
                    if (map.containsKey(col to row)) {
                        map.remove(col to row)
                        map[col to row - (2 * (row - fold.coord))] = '#'
                    }
                }
            }
        }
        else {
            for (row in 0..height) {
                for (col in fold.coord..width) {
                    if (map.containsKey(col to row)) {
                        map.remove(col to row)
                        map[col - (2 * (col - fold.coord)) to row] = '#'
                    }
                }
            }
        }

        return map
    }

    private fun readInput(): Pair<Map<Pair<Int, Int>, Char>, List<Fold>> {
        val map = HashMap<Pair<Int, Int>, Char>()
        val folds = mutableListOf<Fold>()
        readPuzzle("13-1.txt").filterNot { it.trim().isEmpty() }.forEach { line ->
            if (line.startsWith("fold")) {
                val split = line.split('=')
                folds.add(Fold(split[1].toInt(), split[0].last() == 'y'))
            } else {
                val coords = line.split(',')
                map[coords[0].toInt() to coords[1].toInt()] = '#'
            }
        }

        return Pair(map, folds)
    }
}
