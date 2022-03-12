package minesweeper

import java.lang.IndexOutOfBoundsException
import java.lang.NumberFormatException
import kotlin.random.Random

const val FIELD_SIZE = 9
const val LOWER_BOUND = 0
const val UPPER_BOUND = FIELD_SIZE - 1

class Minesweeper {
    private var firstTurn = true
    private var mineCount = 0
    private val field = Array(FIELD_SIZE) { Array(FIELD_SIZE) { Cell() } }
    fun play() {
        var theEnd = false
//        val numberOfCells = FIELD_SIZE * FIELD_SIZE

        println("How many mines do you want on the field?")
        mineCount = readLine()!!.toInt()
//        if (mineCount == null || mineCount >= numberOfCells) {
//            println("The amount of mines should be a number. The number should be less than $numberOfCells")
//            return
//        }
        printField()

        while (!theEnd) {
            println("Set/unset mine marks or claim a cell as free:")
            try {
                val(stringX, stringY, command) = readln().split(' ')
                val x = stringX.toInt() - 1
                val y = stringY.toInt() - 1
                theEnd = when (command) {
                    "mine" -> {
                        markCell(x, y)
                    }
                    "free" -> {
                        if (firstTurn) {
                            setMines(x, y)
                            fillField()
                            firstTurn = false
                        }

                        exploreCell(x, y)
                    }
                    else -> {
                        println("Unknown command.")
                        println("Valid commands are: free, mine.")
                        false
                    }
                }

            } catch (e1: IndexOutOfBoundsException) {
                println("The correct input should look like 'X Y command'.")
                println("X and Y should be between 1 and ${FIELD_SIZE}.")
                println("Valid commands are: free, mine.")
                continue
            } catch (e2: NumberFormatException) {
                println("The coordinate should be a number")
                continue
            }
        }
    }

    private fun printField() {
        val fieldRange = 0 until FIELD_SIZE
        var top = ""
        for (n in fieldRange) top += (n+1).toString()
        println(" │123456789│")
        println("—│—————————│")
        for (y in fieldRange) {
            print("${y + 1}│")
            for (x in fieldRange) {
                print(field[y][x].getPlaceholder())
            }
            println("│")
        }
        println("—│—————————│")
    }

    private fun markCell(x: Int, y: Int): Boolean {
        field[y][x].state = CellState.changeCellState(field[y][x].state, Command.MARK)
        if (field[y][x].value == CellValue.MINE && field[y][x].state == CellState.MARKED) {
            mineCount--
        }
        if (field[y][x].value == CellValue.MINE && field[y][x].state == CellState.UNMARKED) {
            mineCount++
        }
        printField()
        if (mineCount == 0) {
            println("Congratulations! You found all the mines!")
            return true
        }
        return false
    }

    private fun exploreCell(x: Int, y: Int): Boolean {

        if (field[y][x].value == CellValue.MINE) {
            showMines()
            printField()
            println("You stepped on a mine and failed!")
            return true
        }
        if (field[y][x].value == CellValue.EMPTY) {
            exploreNeighboringCells(y, x)
        }
        field[y][x].state = CellState.changeCellState(field[y][x].state, Command.EXPLORE)
        printField()
        return false
    }

    private fun exploreNeighboringCells(y: Int, x: Int) {
        if (field[y][x].value != CellValue.EMPTY) return
        if (field[y][x].state == CellState.EXPLORED) return
        field[y][x].state = CellState.EXPLORED
        if (isTopLeftCorner(y, x)) {
            exploreNeighboringCells(y + 1, x)
            exploreNeighboringCells(y, x + 1)
            exploreNeighboringCells(y + 1, x + 1)
            return
        }
        if (isTopRightCorner(y, x)) {
            exploreNeighboringCells(y, x - 1)
            exploreNeighboringCells(y + 1, x)
            exploreNeighboringCells(y + 1, x - 1)
            return
        }
        if (isBottomLeftCorner(y, x)) {
            exploreNeighboringCells(y-1, x)
            exploreNeighboringCells(y,x+1)
            exploreNeighboringCells(y - 1, x + 1)
            return
        }
        if (isBottomRightCorner(y, x)) {
            exploreNeighboringCells(y-1,x)
            exploreNeighboringCells(y, x-1)
            exploreNeighboringCells(y - 1, x - 1)
            return
        }
        if (isTopSide(y, x)) {
            exploreNeighboringCells(y,x-1)
            exploreNeighboringCells(y+1,x-1)
            exploreNeighboringCells(y+1, x)
            exploreNeighboringCells(y+1, x+1)
            exploreNeighboringCells(y, x + 1)
            return
        }
        if (isBottomSide(y, x)) {
            exploreNeighboringCells(y, x-1)
            exploreNeighboringCells(y-1, x-1)
            exploreNeighboringCells(y-1, x)
            exploreNeighboringCells(y-1, x+1)
            exploreNeighboringCells(y, x + 1)
            return
        }
        if (isLeftSide(y, x)) {
            exploreNeighboringCells(y-1, x)
            exploreNeighboringCells(y-1, x+1)
            exploreNeighboringCells(y, x+1)
            exploreNeighboringCells(y+1, x+1)
            exploreNeighboringCells(y+1, x)
            return
        }
        if (isRightSide(y, x)) {
            exploreNeighboringCells(y-1,x)
            exploreNeighboringCells(y-1,x-1)
            exploreNeighboringCells(y,x-1)
            exploreNeighboringCells(y+1,x-1)
            exploreNeighboringCells(y+1,x)
            return
        }
            exploreNeighboringCells(y-1,x-1)
            exploreNeighboringCells(y-1,x)
            exploreNeighboringCells(y-1,x+1)
            exploreNeighboringCells(y,x+1)
            exploreNeighboringCells(y+1,x+1)
            exploreNeighboringCells(y+1,x)
            exploreNeighboringCells(y+1,x-1)
            exploreNeighboringCells(y,x-1)
    }

    private fun showMines() {
        for (y in 0 until FIELD_SIZE) {
            for (x in 0 until FIELD_SIZE) {
                if (field[y][x].value == CellValue.MINE) {
                    field[y][x].state = CellState.EXPLORED
                }
            }
        }
    }

    private fun setMines(x: Int, y: Int) {
        var count = mineCount
        while(count != 0) {
            val newX = Random.nextInt(0, FIELD_SIZE)
            val newY = Random.nextInt(0, FIELD_SIZE)
            if(newX == x && newY == y) continue
            if (field[newY][newX].value == CellValue.MINE) continue
            field[newY][newX].value = CellValue.MINE
            count--
        }
    }

    private fun fillField() {
        for (y in 0 until FIELD_SIZE) {
            for (x in 0 until FIELD_SIZE) {
                if (field[y][x].value == CellValue.MINE) continue
                if (isTopLeftCorner(x, y)) {
                    field[y][x].value = countMines(arrayOf(field[y + 1][x], field[y][x + 1], field[y + 1][x + 1]))
                    continue
                }
                if (isTopRightCorner(x, y)) {
                    field[y][x].value = countMines(arrayOf(field[y][x - 1], field[y + 1][x], field[y + 1][x - 1]))
                    continue
                }
                if (isBottomLeftCorner(x, y)) {
                    field[y][x].value = countMines(arrayOf(field[y-1][x], field[y][x+1], field[y - 1][x + 1]))
                    continue
                }
                if (isBottomRightCorner(x, y)) {
                    field[y][x].value = countMines(arrayOf(field[y-1][x], field[y][x-1], field[y - 1][x - 1]))
                    continue
                }
                if (isTopSide(x, y)) {
                    field[y][x].value = countMines(arrayOf(
                        field[y][x-1],
                        field[y+1][x-1],
                        field[y+1][x],
                        field[y+1][x+1],
                        field[y][x + 1]
                    ))
                    continue
                }
                if (isBottomSide(x, y)) {
                    field[y][x].value = countMines(arrayOf(
                        field[y][x-1],
                        field[y-1][x-1],
                        field[y-1][x],
                        field[y-1][x+1],
                        field[y][x + 1]
                    ))
                    continue
                }
                if (isLeftSide(x, y)) {
                    field[y][x].value = countMines(arrayOf(
                        field[y-1][x],
                        field[y-1][x+1],
                        field[y][x+1],
                        field[y+1][x+1],
                        field[y+1][x]
                    ))
                    continue
                }
                if (isRightSide(x, y)) {
                    field[y][x].value = countMines(arrayOf(
                        field[y-1][x],
                        field[y-1][x-1],
                        field[y][x-1],
                        field[y+1][x-1],
                        field[y+1][x]
                    ))
                    continue
                }
                field[y][x].value = countMines(arrayOf(
                    field[y-1][x-1],
                    field[y-1][x],
                    field[y-1][x+1],
                    field[y][x+1],
                    field[y+1][x+1],
                    field[y+1][x],
                    field[y+1][x-1],
                    field[y][x-1]
                ))
            }
        }
    }

    private fun countMines(cells: Array<Cell>): CellValue {
        val c = cells.count { it.value == CellValue.MINE }
        return CellValue.convertDigitToCellValue(c)
    }


    private fun isTopLeftCorner(x: Int, y: Int) = x == LOWER_BOUND && y == LOWER_BOUND
    private fun isTopRightCorner(x: Int, y: Int) = x == UPPER_BOUND && y == LOWER_BOUND
    private fun isBottomLeftCorner(x: Int, y: Int) = x == LOWER_BOUND && y == UPPER_BOUND
    private fun isBottomRightCorner(x: Int, y: Int) = x == UPPER_BOUND && y == UPPER_BOUND
    private fun isTopSide(x: Int, y: Int) = x != LOWER_BOUND && x != UPPER_BOUND && y == LOWER_BOUND
    private fun isBottomSide(x: Int, y: Int) = x != LOWER_BOUND && x != UPPER_BOUND && y == UPPER_BOUND
    private fun isLeftSide(x: Int, y: Int) = x == LOWER_BOUND && y != LOWER_BOUND && y != UPPER_BOUND
    private fun isRightSide(x: Int, y: Int) = x == UPPER_BOUND && y != LOWER_BOUND && y != UPPER_BOUND
}