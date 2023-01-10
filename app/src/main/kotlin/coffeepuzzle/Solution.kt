package coffeepuzzle

/**
 * Represents a partial or complete solution.
 */
data class Solution(
    val depth: Int,
    val ids: MutableList<IntArray> = mutableListOf(),
    val oris: MutableList<IntArray> = mutableListOf()
)
