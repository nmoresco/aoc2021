import kotlin.math.ceil
import kotlin.streams.toList

class Day3 : Puzzle {

    override fun solvePartOne() {
        val list = readInput()
        var gamma = ""
        var epsilon = ""

        for (i in 0 until list[0].size) {
            if (onesAreMoreCommon(list, i)) {
                gamma += '1'
                epsilon += '0'
            } else {
                gamma += '0'
                epsilon += '1'
            }
        }

        val gammaDecimal = Integer.parseInt(gamma, 2)
        val epsilonDecimal = Integer.parseInt(epsilon, 2)

        println(gammaDecimal * epsilonDecimal)
    }

    override fun solvePartTwo() {
        val list = readInput()

        var oxygenList = list
        var i = 0
        while (oxygenList.size > 1) {
            val mostCommon = if (onesAreMoreCommon(oxygenList, i)) 1 else 0
            oxygenList = oxygenList.filter { it[i] == mostCommon }
            i++
        }

        var co2List = list
        i = 0
        while (co2List.size > 1) {
            val mostCommon = if (onesAreMoreCommon(co2List, i)) 0 else 1
            co2List = co2List.filter { it[i] == mostCommon }
            i++
        }

        // Convert from Int array -> String -> decimal
        val oxygen = oxygenList[0].map { it.toString() }.reduce { acc, char -> acc + char }.let { Integer.parseInt(it, 2) }
        val co2 = co2List[0].map { it.toString() }.reduce { acc, char -> acc + char }.let { Integer.parseInt(it, 2) }

        println(oxygen * co2)
    }

    private fun onesAreMoreCommon(list: List<List<Int>>, index: Int): Boolean {
        return list.count { it[index] == 1 } >= ceil((list.size / 2.0))
    }

    private fun readInput(): List<List<Int>> {
        return readPuzzle("3-1.txt")
            .map { line -> line.chars().toList().map { it - 48 } }
    }

}
