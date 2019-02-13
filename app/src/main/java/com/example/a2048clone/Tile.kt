package com.example.a2048clone

/**
 * Tile class
 * represents the spaces on the game board
 * Keeps its coordinates and a current value (initialized to 0)
 */

// Tile holds coordinates and a value
class Tile(val coorY: Int, val coorX: Int, var value: Int = DEFAULT_VALUE)
