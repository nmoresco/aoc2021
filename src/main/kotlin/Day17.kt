import kotlin.math.abs

class Day17 : Puzzle {

    data class Rect(val x1: Int, val x2: Int, val y1: Int, val y2: Int)

    data class Projectile(val location: Pair<Int, Int>, val velocity: Pair<Int, Int>)

    override fun solvePartOne() {
        val rect = readInput()

        val velocityHits: MutableMap<Pair<Int, Int>, Int> = calculateHits(rect)

        println(velocityHits.keys)
        println(velocityHits.values.maxOrNull())
    }

    override fun solvePartTwo() {
        val rect = readInput()

        val velocityHits: MutableMap<Pair<Int, Int>, Int> = calculateHits(rect)

        println(velocityHits.keys.size)
    }

    private fun calculateHits(rect: Rect): MutableMap<Pair<Int, Int>, Int> {
        val velocityHits: MutableMap<Pair<Int, Int>, Int> = hashMapOf()

        // We're assuming the target is to the right and below us.
        for (x in 0..rect.x2) {
            for (y in rect.y1 until abs(rect.y1)) {
                var projectile = Projectile(0 to 0, x to y)
                var maxY = 0

                while (projectile.location.second > rect.y1) {
                    projectile = step(projectile)

                    if (projectile.location.second > maxY) {
                        maxY = projectile.location.second
                    }

                    if (checkIntersection(projectile, rect)) {
                        velocityHits[x to y] = maxY
                        break
                    }
                }
            }
        }

        return velocityHits
    }

    private fun step(projectile: Projectile): Projectile {
        val newX = projectile.location.first + projectile.velocity.first
        val newY = projectile.location.second + projectile.velocity.second

        val newDX = if (projectile.velocity.first > 0) {
            projectile.velocity.first - 1
        } else if (projectile.velocity.first < 0) {
            projectile.velocity.first + 1
        } else {
            0
        }

        val newDy = projectile.velocity.second - 1

        return Projectile(newX to newY, newDX to newDy)
    }

    private fun checkIntersection(projectile: Projectile, rect: Rect): Boolean {
        return (rect.x1 <= projectile.location.first && projectile.location.first <= rect.x2) &&
                (rect.y1 <= projectile.location.second && projectile.location.second <= rect.y2)
    }

    private fun readInput(): Rect {
        val regex = Regex("target area: x=(-?\\d+)..(-?\\d+), y=(-?\\d+)..(-?\\d+)")

        val matchResults = regex.find(readPuzzle("17-1.txt").first())?.groupValues ?: listOf()
        assert(matchResults.size == 5)

        return Rect(matchResults[1].toInt(), matchResults[2].toInt(), matchResults[3].toInt(), matchResults[4].toInt())
    }
}

