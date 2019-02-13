package com.example.a2048clone

import android.content.Context

// Game is a singleton object that instantiates a Tile matrix.
// Also has a method for assisting Tile movement.
object Game
{
    // Matrix holding the Tiles
    private var matrix : Array< Array<Tile> >

    // Initialize matrix
    init
    {
        // Create Tiles
        val t00 = Tile(0,0)
        val t01 = Tile(0,1)
        val t02 = Tile(0,2)
        val t03 = Tile(0,3)
        val t10 = Tile(1,0)
        val t11 = Tile(1,1)
        val t12 = Tile(1,2)
        val t13 = Tile(1,3)
        val t20 = Tile(2,0)
        val t21 = Tile(2,1)
        val t22 = Tile(2,2)
        val t23 = Tile(2,3)
        val t30 = Tile(3,0)
        val t31 = Tile(3,1)
        val t32 = Tile(3,2)
        val t33 = Tile(3,3)

        // Create rows
        val row0 = arrayOf(t00,t01,t02,t03)
        val row1 = arrayOf(t10,t11,t12,t13)
        val row2 = arrayOf(t20,t21,t22,t23)
        val row3 = arrayOf(t30,t31,t32,t33)

        // Create matrix
        matrix = arrayOf(row0,row1,row2,row3)
    }

    // Move the tile in a direction passed as an argument. The value of the previous tile before this
    // one is passed in as well, or if there was no previous tile then it is the DEFAULT_VALUE.
    // Calls Move() on the next tile as well, causing a chain reaction in the direction of the movement.
    fun move(tile: Tile, direction : Direction, incomingValue : Int = 0)
    {
        // Calculate new value
        tile.value += incomingValue

        // UI Update

        // Return if this tile has the default value
        // Saves time going over empty tiles that don't need to move
        if(tile.value == DEFAULT_VALUE) { return }

        // Get next tile using direction
        val nextTile = nextTile(tile, direction)

        // Return if there is no tile to move to
        // Based on Game.nextTile returning a bad Tile with -1 values if no Tile found
        if(nextTile.coorX == -1) { return }

        // Return if the tiles can't combine
        if(nextTile.value != 0 && nextTile.value != tile.value) { return }

        // Animate Go \\

        // Save value to send to next tile and set this tile's value to the default value
        val sentValue = tile.value
        tile.value = DEFAULT_VALUE

        // UI Update

        // Animate Back \\

        // Call move on the next tile
        move(nextTile, direction, sentValue)
    }

    // Return the next tile from the matrix based on the previous tile
    // Returns a tile with -1 coordinates and values if the tile passed in is
    // and edge tile.
    private fun nextTile(tile: Tile, direction: Direction) : Tile
    {
        val newY : Int
        val newX : Int

        // Find the new coordinates of the Tile
        when(direction)
        {
            Direction.LEFT -> {
                newY = tile.coorY
                newX = tile.coorX - 1
            }
            Direction.RIGHT -> {
                newY = tile.coorY
                newX = tile.coorX + 1
            }
            Direction.UP -> {
                newY = tile.coorY - 1
                newX = tile.coorX
            }
            Direction.DOWN -> {
                newY = tile.coorY + 1
                newX = tile.coorX
            }
        }

        // Return bad Tile if the Tile is off the edge
        if(newY > 3 || newY < 0 || newX > 3 || newX < 0) { return Tile(-1,-1,-1) }

        // Return the valid Tile
        return matrix[newY][newX]
    }

    // Swipe functions

    fun leftSwipe(context : Context)
    {
        context as MainActivity
        context.setTestText("left")
    }
    fun rightSwipe(context : Context)
    {
        context as MainActivity
        context.setTestText("right")
    }
    fun upSwipe(context : Context)
    {
        context as MainActivity
        context.setTestText("up")
    }
    fun downSwipe(context : Context)
    {
        context as MainActivity
        context.setTestText("down")
    }
}
