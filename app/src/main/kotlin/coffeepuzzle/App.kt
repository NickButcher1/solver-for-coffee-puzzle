package coffeepuzzle

class App {
    private val pieceData = PieceData()

    private fun addPieceRight(inputList: Solution, rightCellIdx: Int): Solution {
        val outputSolution = Solution(inputList.depth + 1)

        (0 until inputList.ids.size).forEach { idx ->
            val rightEdge = pieceData.tiles[inputList.oris[idx][rightCellIdx]][inputList.ids[idx][rightCellIdx]][1]
            (0..35).forEach { newPieceId ->
                if (!inputList.ids[idx].contains(newPieceId)) {
                    (0..3).forEach { newOrientation ->
                        val newLeftEdge = pieceData.tiles[newOrientation][newPieceId][3]
                        if (newLeftEdge == rightEdge) {
                            outputSolution.ids.add((inputList.ids[idx] + newPieceId).toMutableList())
                            outputSolution.oris.add((inputList.oris[idx] + newOrientation).toMutableList())
                        }
                    }
                }
            }
        }

        return outputSolution
    }

    private fun addPieceBelow(inputList: Solution, belowCellIdx: Int): Solution {
        val outputSolution = Solution(inputList.depth + 1)

        (0 until inputList.ids.size).forEach { idx ->
            val bottomEdge = pieceData.tiles[inputList.oris[idx][belowCellIdx]][inputList.ids[idx][belowCellIdx]][2]
            (0..35).forEach { newPieceId ->
                if (!inputList.ids[idx].contains(newPieceId)) {
                    (0..3).forEach { newOrientation ->
                        val newTopEdge = pieceData.tiles[newOrientation][newPieceId][0]
                        if (newTopEdge == bottomEdge) {
                            outputSolution.ids.add((inputList.ids[idx] + newPieceId).toMutableList())
                            outputSolution.oris.add((inputList.oris[idx] + newOrientation).toMutableList())
                        }
                    }
                }
            }
        }

        return outputSolution
    }

    private fun addPieceBelowRight(inputList: Solution, belowCellIdx: Int, rightCellIdx: Int): Solution {
        val outputSolution = Solution(inputList.depth + 1)

        (0 until inputList.ids.size).forEach { idx ->
            val rightEdge = pieceData.tiles[inputList.oris[idx][rightCellIdx]][inputList.ids[idx][rightCellIdx]][1]
            val bottomEdge = pieceData.tiles[inputList.oris[idx][belowCellIdx]][inputList.ids[idx][belowCellIdx]][2]

            (0..35).forEach { newPieceId ->
                if (!inputList.ids[idx].contains(newPieceId)) {
                    (0..3).forEach { newOrientation ->
                        val newLeftEdge = pieceData.tiles[newOrientation][newPieceId][3]
                        val newTopEdge = pieceData.tiles[newOrientation][newPieceId][0]
                        if (newLeftEdge == rightEdge && newTopEdge == bottomEdge) {
                            outputSolution.ids.add((inputList.ids[idx] + newPieceId).toMutableList())
                            outputSolution.oris.add((inputList.oris[idx] + newOrientation).toMutableList())
                        }
                    }
                }
            }
        }

        return outputSolution
    }

    private fun addRowBelow(inputSolutions: Solution, firstCellIdx: Int): Solution {
        var solutions = addPieceBelow(inputSolutions, firstCellIdx)

        (1..5).forEach { idx ->
            solutions = addPieceBelowRight(solutions, firstCellIdx + idx, firstCellIdx + 5 + idx)
        }

        return solutions
    }

    fun solve() {
        // Place pieces into cells in this order.
        //  0  1  2  3  4  5
        //  6  7  8  9 10 11
        // 12 13 14 15 16 17
        // 18 19 20 21 22 23
        // 24 25 26 27 28 29
        // 30 31 32 33 34 35

        // Start with a list of all pieces in all orientations in the top left corner.
        var solutions = Solution(1)
        (0..3).forEach { orientation ->
            val tilesForOrientation = pieceData.tiles[orientation]
            (0 until tilesForOrientation.size).forEach { pieceId ->
                solutions.ids.add(mutableListOf(pieceId))
                solutions.oris.add(mutableListOf(orientation))
            }
        }

        // First row.
        (0..4).forEach { idx ->
            solutions = addPieceRight(solutions, idx)
        }

        // Subsequent rows.
        solutions = addRowBelow(solutions, 0)
        solutions = addRowBelow(solutions, 6)
        solutions = addRowBelow(solutions, 12)
        solutions = addRowBelow(solutions, 18)
        solutions = addRowBelow(solutions, 24)

        require(solutions.ids.size == 4) { "Expect one solution, but there are four rotations." }

        // Map IDs from zero-based to one-based.
        val solutionIds = solutions.ids[0].map { it + 1 }.toList()
        println("Pieces: $solutionIds")
        println("Orientations: ${solutions.oris[0]}")
    }
}

fun main() {
    App().solve()
}
