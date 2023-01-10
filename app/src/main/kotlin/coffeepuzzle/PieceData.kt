package coffeepuzzle

import java.io.File

class PieceData {
    private val inputLines = File("../input/tiles.txt").useLines { it.toList() }

    /**
     * For tile
     *     AB
     *     DC
     * the four edges in orientation 0 are:
     *     top = AB
     *     right = BC
     *     bottom = DC
     *     left = AD
     * and in orientation 1 (clockwise 90deg):
     *     top = DA
     *     right = AB
     *     bottom = CB
     *     left = DC
     *
     * Lookup a tile with tiles[orientation][id][edge]
     */
    val tiles: List<MutableList<List<Int>>>

    init {
        val orientation0tilesList: MutableList<List<Int>> = mutableListOf()
        val orientation1tilesList: MutableList<List<Int>> = mutableListOf()
        val orientation2tilesList: MutableList<List<Int>> = mutableListOf()
        val orientation3tilesList: MutableList<List<Int>> = mutableListOf()
        val orientation4tilesList: MutableList<List<Int>> = mutableListOf()
        val orientation5tilesList: MutableList<List<Int>> = mutableListOf()
        val orientation6tilesList: MutableList<List<Int>> = mutableListOf()
        val orientation7tilesList: MutableList<List<Int>> = mutableListOf()
        tiles = listOf(
            orientation0tilesList,
            orientation1tilesList,
            orientation2tilesList,
            orientation3tilesList,
            orientation4tilesList,
            orientation5tilesList,
            orientation6tilesList,
            orientation7tilesList
        )

        inputLines.indices.forEach { pieceId ->
            val pieceChars = inputLines[pieceId]
            val char1 = (pieceChars[0] - 'A')
            val char2 = (pieceChars[1] - 'A')
            val char3 = (pieceChars[2] - 'A')
            val char4 = (pieceChars[3] - 'A')
            // 12
            // 43
            orientation0tilesList.add(
                listOf(
                    c2(char1, char2),
                    c2(char2, char3),
                    c2(char4, char3),
                    c2(char1, char4)
                )
            )
            // 41
            // 32
            orientation3tilesList.add(
                listOf(
                    c2(char4, char1),
                    c2(char1, char2),
                    c2(char3, char2),
                    c2(char4, char3)
                )
            )
            // 34
            // 21
            orientation2tilesList.add(
                listOf(
                    c2(char3, char4),
                    c2(char4, char1),
                    c2(char2, char1),
                    c2(char3, char2)
                )
            )
            // 23
            // 14
            orientation1tilesList.add(
                listOf(
                    c2(char2, char3),
                    c2(char3, char4),
                    c2(char1, char4),
                    c2(char2, char1)
                )
            )
            // 21
            // 34
            orientation4tilesList.add(
                listOf(
                    c2(char2, char1),
                    c2(char1, char4),
                    c2(char3, char4),
                    c2(char2, char3)
                )
            )
            // 32
            // 41
            orientation7tilesList.add(
                listOf(
                    c2(char3, char2),
                    c2(char2, char1),
                    c2(char4, char1),
                    c2(char3, char4)
                )
            )
            // 43
            // 12
            orientation6tilesList.add(
                listOf(
                    c2(char4, char3),
                    c2(char3, char2),
                    c2(char1, char2),
                    c2(char4, char1)
                )
            )
            // 14
            // 23
            orientation5tilesList.add(
                listOf(
                    c2(char1, char4),
                    c2(char4, char3),
                    c2(char2, char3),
                    c2(char1, char2)
                )
            )
        }
    }

    private fun c2(i1: Int, i2: Int) = 26 * i1 + i2
}
