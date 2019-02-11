package com.example.a2048clone

/**
 * Tile class
 * represents the spaces on the game board
 * Keeps its coordinates and a current value (initialized to 0)
 */

// Default value of tile
const val DEFAULT_VALUE = 0

// Enum for swipe directions
enum class Direction
{
    UP, DOWN, LEFT, RIGHT
}

// Tile holds coordinates and a value
class Tile(val coorY: Int, val coorX: Int, var value: Int = DEFAULT_VALUE)
