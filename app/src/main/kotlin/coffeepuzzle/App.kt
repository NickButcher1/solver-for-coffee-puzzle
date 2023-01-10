package coffeepuzzle

class App(private val numOrientations: Int) {
    private val pieceData = PieceData()

    private fun addPieceRight(inputList: Solution, rightCellIdx: Int): Solution {
        val outputSolution = Solution(inputList.depth + 1)

        (0 until inputList.ids.size).forEach { idx ->
            val inputIds = inputList.ids[idx]
            val inputOris = inputList.oris[idx]

            val rightEdge = pieceData.tiles[inputOris[rightCellIdx]][inputIds[rightCellIdx]][1]

            (0..35).forEach { newPieceId ->
                if (!inputIds.contains(newPieceId)) {
                    (0 until numOrientations).forEach { newOrientation ->
                        if (pieceData.tiles[newOrientation][newPieceId][3] == rightEdge) {
                            outputSolution.ids.add((inputIds + newPieceId).toMutableList())
                            outputSolution.oris.add((inputOris + newOrientation).toMutableList())
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
            val inputIds = inputList.ids[idx]
            val inputOris = inputList.oris[idx]

            val bottomEdge = pieceData.tiles[inputOris[belowCellIdx]][inputIds[belowCellIdx]][2]

            (0..35).forEach { newPieceId ->
                if (!inputIds.contains(newPieceId)) {
                    (0 until numOrientations).forEach { newOrientation ->
                        if (pieceData.tiles[newOrientation][newPieceId][0] == bottomEdge) {
                            outputSolution.ids.add((inputIds + newPieceId).toMutableList())
                            outputSolution.oris.add((inputOris + newOrientation).toMutableList())
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
            val inputIds = inputList.ids[idx]
            val inputOris = inputList.oris[idx]

            val rightEdge = pieceData.tiles[inputOris[rightCellIdx]][inputIds[rightCellIdx]][1]
            val bottomEdge = pieceData.tiles[inputOris[belowCellIdx]][inputIds[belowCellIdx]][2]

            (0..35).forEach { newPieceId ->
                if (!inputIds.contains(newPieceId)) {
                    (0 until numOrientations).forEach { newOrientation ->
                        if (pieceData.tiles[newOrientation][newPieceId][3] == rightEdge &&
                            pieceData.tiles[newOrientation][newPieceId][0] == bottomEdge
                        ) {
                            outputSolution.ids.add((inputIds + newPieceId).toMutableList())
                            outputSolution.oris.add((inputOris + newOrientation).toMutableList())
                        }
                    }
                }
            }
        }

        return outputSolution
    }

    private fun addRowBelow(inputSolutions: Solution, firstCellIdx: Int): Solution {
        var solutions = addPieceBelow(inputSolutions, firstCellIdx)
        // println("DEPTH: ${solutions.depth}, ${solutions.ids.size}")

        (1..5).forEach { idx ->
            solutions = addPieceBelowRight(solutions, firstCellIdx + idx, firstCellIdx + 5 + idx)
            // println("DEPTH: ${solutions.depth}, ${solutions.ids.size}")
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
        var totalSolutions = 0
        val allSolutions = Solution(35)

        (0..35).forEach { pieceId ->
            (0 until numOrientations).forEach { orientation ->
                // println("Try piece ID: $pieceId, orientation $orientation")
                var solutions = Solution(1)

                solutions.ids.add(mutableListOf(pieceId))
                solutions.oris.add(mutableListOf(orientation))

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

                // require(solutions.ids.size == 4) { "Expect one solution, but there are four rotations." }

                // Map IDs from zero-based to one-based.
                totalSolutions += solutions.ids.size
                if (solutions.ids.size != 0) {
                    println("Found ${solutions.ids.size} more solutions")
                    allSolutions.ids.addAll(solutions.ids)
                    allSolutions.oris.addAll(solutions.oris)
                }
            }
        }

        println("Total solutions for $numOrientations orientations: $totalSolutions")
        allSolutions.ids.indices.forEach { idx ->
            val solutionIds = allSolutions.ids[idx].map { it + 1 }.toList()
            println("Pieces: $solutionIds")
            println("Orientations: ${allSolutions.oris[0]}")
        }
    }
}

fun main() {
    App(4).solve()
    App(8).solve()
}
