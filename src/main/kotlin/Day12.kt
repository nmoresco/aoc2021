class Day12 : Puzzle {


    override fun solvePartOne() {
        val nodes = readInput()

        val paths = findPaths(nodes, "start", "end", mutableListOf("start"),
            { node: String, _: List<String>, visited: Set<String> ->
                // Uppercase and unvisited nodes are allowed
                node.all { it.isUpperCase() } || !visited.contains(node)
            })

        println(paths.size)
    }

    override fun solvePartTwo() {
        val nodes = readInput()

        val paths = findPaths(nodes, "start", "end", mutableListOf("start"),
            { node: String, path: List<String>, visited: Set<String> ->
                // Uppercase and unvisited nodes are allowed, plus exactly one set of lowercase nodes (not including start)
                node.all { it.isUpperCase() } || !visited.contains(node) || (node != "start" && !pathContainsSmallCaveTwice(path))
            })

        println(paths.size)
    }

    private fun findPaths(
        map: Map<String, List<String>>,
        source: String,
        dest: String,
        path: List<String>,
        isNodeValid: (String, List<String>, Set<String>) -> Boolean,
        visited: Set<String> = mutableSetOf()
    ): List<List<String>> {

        if (source == dest) {
            return listOf(path)
        }

        val paths: MutableList<List<String>> = mutableListOf()

        map[source]?.forEach { node ->
            if (isNodeValid(node, path, visited)) {
                paths.addAll(findPaths(map, node, dest, path + listOf(node), isNodeValid, visited + listOf(source)))
            }
        }

        return paths
    }

    private fun pathContainsSmallCaveTwice(path: List<String>): Boolean {
        val smallCaves: List<String> = path.filter { node -> node.all { it.isLowerCase() } }
        return (smallCaves.distinct().size != smallCaves.size)
    }

    private fun readInput(): Map<String, MutableList<String>> {
        val graph: HashMap<String, MutableList<String>> = hashMapOf()

        readPuzzle("12-1.txt")
            .flatMap { line ->
                line.split("-")
                    .windowed(2, 2)
                    .map {
                        it[0] to it[1]
                    }
            }.forEach { pair ->
                // Its a non directed graph; you can move in both directions.
                graph.getOrPut(pair.first) { mutableListOf() }.add(pair.second)
                graph.getOrPut(pair.second) { mutableListOf() }.add(pair.first)
            }

        return graph
    }
}

