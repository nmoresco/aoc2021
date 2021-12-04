class Day4 : Puzzle {

    data class Puzzle(val numbers: List<Int>, val boards: List<Board>)

    data class Board(val board: List<List<Space>>)

    data class Space(val number: Int, var marked: Boolean)

    override fun solvePartOne() {
        val puzzle = readInput()

        puzzle.numbers.forEach { number ->
            puzzle.boards.forEach { board ->
                markBoard(number, board)
                if (hasBoardWon(board)) {
                    println(calcScore(number, board))
                    return
                }
            }
        }
    }

    override fun solvePartTwo() {
        val puzzle = readInput()

        var boards = puzzle.boards

        puzzle.numbers.forEach { number ->
            boards = boards.filterNot { board -> hasBoardWon(board) }

            boards.forEach { board ->
                markBoard(number, board)
                if (boards.size == 1 && hasBoardWon(board)) {
                    println(calcScore(number, board))
                    return
                }
            }

        }
    }

    private fun markBoard(number: Int, board: Board) {
        board.board.forEach { line ->
            line.find { it.number == number }?.marked = true
        }
    }

    private fun hasBoardWon(board: Board): Boolean {
        // Check rows
        if (board.board.any { line -> line.all { space -> space.marked } }) {
            return true
        }

        // Check columns...in a less fancy way.
        for (col in 0..4) {
            for (row in 0..4) {
                if (!board.board[row][col].marked) {
                    break
                }
                if (row == 4) {
                    return true
                }
            }
        }

        return false
    }

    private fun calcScore(number: Int, board: Board): Int {
        return number * board.board.sumOf { line -> line.filterNot { it.marked }.sumOf { it.number } }
    }

    private fun readInput(): Puzzle {
        val lines = readPuzzle("4-1.txt")
            .filterNot { it.isEmpty() }
            .map { it.trim().replace("  ", " ") }

        // First line is the numbers
        val numbers = lines[0]
            .split(",")
            .map { it.toInt() }

        // After that, every 5 lines is a board
        val boards = lines
            .drop(1)
            .windowed(5, 5)
            .map { board ->
                Board(board.map { line ->
                    line.split(" ").map { Space(it.toInt(), false) }
                })
            }

        return Puzzle(numbers, boards)
    }
}
