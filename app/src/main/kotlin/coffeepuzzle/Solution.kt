package coffeepuzzle

/**
 * Represents a partial or complete solution.
 */
data class Solution(
    val depth: Int,
    val ids: MutableList<MutableList<Int>> = mutableListOf(),
    val oris: MutableList<MutableList<Int>> = mutableListOf()
)