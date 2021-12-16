class Day16 : Puzzle {

    private val encoding = mapOf(
        '0' to "0000",
        '1' to "0001",
        '2' to "0010",
        '3' to "0011",
        '4' to "0100",
        '5' to "0101",
        '6' to "0110",
        '7' to "0111",
        '8' to "1000",
        '9' to "1001",
        'A' to "1010",
        'B' to "1011",
        'C' to "1100",
        'D' to "1101",
        'E' to "1110",
        'F' to "1111"
    )

    interface Packet {
        val version: Int
        val size: Int
        val id: Int
    }

    data class LiteralPacket(
        override val version: Int,
        override val id: Int,
        override val size: Int,
        val number: Long
    ) : Packet

    data class OperatorPacket(
        override val version: Int,
        override val id: Int,
        override val size: Int,
        val subPackets: List<Packet>
    ) : Packet

    override fun solvePartOne() {
        val packet = readPacket(readInput())
        println(sumVersions(packet))
    }

    override fun solvePartTwo() {
        println(evaluatePacket(readPacket(readInput())))
    }

    private fun sumVersions(packet: Packet): Int {
        return when (packet) {
            is LiteralPacket -> {
                packet.version
            }
            is OperatorPacket -> {
                packet.version + packet.subPackets.sumOf { sumVersions(it) }
            }
            else -> {
                throw Exception("Unknown packet type yo")
            }
        }
    }

    private fun readPacket(content: String): Packet {
        val version = content.take(3).toInt(2)
        val id = content.drop(3).take(3).toInt(2)

        val remainder = content.drop(6)

        return when (id) {
            4 -> {
                val (number, size) = readLiteralPacket(remainder)
                LiteralPacket(version, id, size + 6, number)
            }
            else -> {
                val (subPackets, size) = readOperatorPacket(remainder)
                OperatorPacket(version, id, size + 6, subPackets)
            }
        }
    }

    private fun readLiteralPacket(content: String): Pair<Long, Int> {
        val numbers = content.windowed(5, 5).takeWhile { it.first() == '1' }
        // The one that begins with 0 marks termination. We take it, but none after that.
        val lastNumber = content.windowed(5, 5).first { it.first() == '0' }

        val literal = (numbers + lastNumber).joinToString("") { it.drop(1) }

        return Pair(literal.toLong(2), (numbers.size + 1) * 5)
    }

    private fun readOperatorPacket(content: String): Pair<List<Packet>, Int> {
        val lengthTypeId = content.first()
        val packets: MutableList<Packet> = mutableListOf()
        var amountParsed = 0

        when (lengthTypeId) {
            '0' -> {
                val totalLength = content.drop(1).take(15).toInt(2)
                amountParsed += 16

                while (amountParsed < totalLength + 16) {
                    val packet = readPacket(content.drop(amountParsed))
                    amountParsed += packet.size
                    packets.add(packet)
                }
            }

            '1' -> {
                val numSubPackets = content.drop(1).take(11).toInt(2)
                amountParsed += 12

                for (i in 0 until numSubPackets) {
                    val packet = readPacket(content.drop(amountParsed))
                    amountParsed += packet.size
                    packets.add(packet)
                }
            }

            else -> {
                throw Exception("Invalid lengthType")
            }
        }

        return Pair(packets, amountParsed)
    }

    private fun evaluatePacket(packet: Packet): Long {
        when (packet) {
            is LiteralPacket -> {
                return when (packet.id) {
                    4 -> packet.number
                    else -> throw Exception("found literal packet with wrong id!")
                }
            }
            is OperatorPacket -> {
                return when (packet.id) {
                    // sum
                    0 -> packet.subPackets.sumOf { evaluatePacket(it) }
                    // product
                    1 -> packet.subPackets.map { evaluatePacket(it) }.reduce { acc, num -> acc * num }
                    // min
                    2 -> packet.subPackets.minOf { evaluatePacket(it) }
                    // max
                    3 -> packet.subPackets.maxOf { evaluatePacket(it) }
                    // greater-than
                    5 -> {
                        assert(packet.subPackets.size == 2)
                        if (evaluatePacket(packet.subPackets[0]) > evaluatePacket(packet.subPackets[1])) 1 else 0
                    }
                    // less-than
                    6 -> {
                        assert(packet.subPackets.size == 2)
                        if (evaluatePacket(packet.subPackets[0]) < evaluatePacket(packet.subPackets[1])) 1 else 0
                    }
                    // equal-to
                    7 -> {
                        assert(packet.subPackets.size == 2)
                        if (evaluatePacket(packet.subPackets[0]) == evaluatePacket(packet.subPackets[1])) 1 else 0
                    }
                    else -> throw Exception("found operator packet with wrong id!")
                }
            }
            else -> throw Exception("Unknown packet type yo")
        }
    }

    private fun readInput(): String {
        return readPuzzle("16-1.txt").first().map { encoding[it] }.joinToString("")
    }
}

