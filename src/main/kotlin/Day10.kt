import java.util.ArrayDeque

class Day10 : Puzzle {

    private val corruptPointValues = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
    private val completePointValues = mapOf('(' to 1, '[' to 2, '{' to 3, '<' to 4)

    override fun solvePartOne() {
        val lines = readInput()

        println(lines.sumOf {
            checkLineSyntax(it) ?: throw(IllegalStateException("We got a character we didn't expect."))
        })
    }


    override fun solvePartTwo() {
        val lines = readInput()

        val scores = lines
            .filter { checkLineSyntax(it) == 0 }
            .map { completeLine(it) }
            .sorted()

        println(scores[scores.size / 2])
    }

    private fun checkLineSyntax(line: List<Char>): Int? {
        val stack = ArrayDeque<Char>()

        line.forEach { char ->
            when (char) {
                '(' -> stack.push(char)
                '{' -> stack.push(char)
                '<' -> stack.push(char)
                '[' -> stack.push(char)
                ')' -> {
                    val opening = stack.pop()
                    if (opening != '(') {
                        return corruptPointValues[')']
                    }
                }
                '}' -> {
                    val opening = stack.pop()
                    if (opening != '{') {
                        return corruptPointValues['}']
                    }
                }
                '>' -> {
                    val opening = stack.pop()
                    if (opening != '<') {
                        return corruptPointValues['>']
                    }
                }
                ']' -> {
                    val opening = stack.pop()
                    if (opening != '[') {
                        return corruptPointValues[']']
                    }
                }
            }
        }

        return 0
    }

    private fun completeLine(line: List<Char>): Long {
        val stack = ArrayDeque<Char>()

        line.forEach { char ->
            when (char) {
                '(' -> stack.push(char)
                '{' -> stack.push(char)
                '<' -> stack.push(char)
                '[' -> stack.push(char)
                ')' -> stack.pop()
                '}' -> stack.pop()
                '>' -> stack.pop()
                ']' -> stack.pop()
            }
        }

        var score = 0L

        while (!stack.isEmpty()) {
            score *= 5
            score += completePointValues[stack.pop()] ?: 0
        }

        return score
    }

    private fun readInput(): List<List<Char>> {
        return readPuzzle("10-1.txt").map { line ->
            line.toCharArray().toList()
        }
    }
}

