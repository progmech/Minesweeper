package minesweeper

import kotlin.random.Random

const val FIELD_SIZE = 9
const val LOWER_BOUND = 0
const val UPPER_BOUND = FIELD_SIZE - 1

fun main() {

    val field = arrayOf(
        CharArray(FIELD_SIZE) {'.'},
        CharArray(FIELD_SIZE) {'.'},
        CharArray(FIELD_SIZE) {'.'},
        CharArray(FIELD_SIZE) {'.'},
        CharArray(FIELD_SIZE) {'.'},
        CharArray(FIELD_SIZE) {'.'},
        CharArray(FIELD_SIZE) {'.'},
        CharArray(FIELD_SIZE) {'.'},
        CharArray(FIELD_SIZE) {'.'}
    )

    println("How many mines do you want on the field?")
    val mineCount = readLine()!!.toInt()
    field.setMines(mineCount)
    field.fill()
    field.print(FIELD_SIZE)
}

fun Array<CharArray>.setMines(mineCount: Int) {
    var count = mineCount
    while(count != 0) {
        val newX = Random.nextInt(0, FIELD_SIZE)
        val newY = Random.nextInt(0, FIELD_SIZE)
        if (this[newY][newX] == 'X') continue
        this[newY][newX] = 'X'
        count--
    }
}

fun Array<CharArray>.fill() {
    for (y in 0 until FIELD_SIZE) {
        for (x in 0 until FIELD_SIZE) {
            if (this[y][x] == 'X') continue
            if (isTopLeftCorner(x, y)) {
                this[y][x] = countMines(charArrayOf(this[y + 1][x], this[y][x + 1], this[y + 1][x + 1]))
                continue
            }
            if (isTopRightCorner(x, y)) {
                this[y][x] = countMines(charArrayOf(this[y][x - 1], this[y + 1][x], this[y + 1][x - 1]))
                continue
            }
            if (isBottomLeftCorner(x, y)) {
                this[y][x] = countMines(charArrayOf(this[y-1][x], this[y][x+1], this[y - 1][x + 1]))
                continue
            }
            if (isBottomRightCorner(x, y)) {
                this[y][x] = countMines(charArrayOf(this[y-1][x], this[y][x-1], this[y - 1][x - 1]))
                continue
            }
            if (isTopSide(x, y)) {
                this[y][x] = countMines(charArrayOf(
                    this[y][x-1],
                    this[y+1][x-1],
                    this[y+1][x],
                    this[y+1][x+1],
                    this[y][x + 1]
                ))
                continue
            }
            if (isBottomSide(x, y)) {
                this[y][x] = countMines(charArrayOf(
                    this[y][x-1],
                    this[y-1][x-1],
                    this[y-1][x],
                    this[y-1][x+1],
                    this[y][x + 1]
                ))
                continue
            }
            if (isLeftSide(x, y)) {
                this[y][x] = countMines(charArrayOf(
                    this[y-1][x],
                    this[y-1][x+1],
                    this[y][x+1],
                    this[y+1][x+1],
                    this[y+1][x]
                ))
                continue
            }
            if (isRightSide(x, y)) {
                this[y][x] = countMines(charArrayOf(
                    this[y-1][x],
                    this[y-1][x-1],
                    this[y][x-1],
                    this[y+1][x-1],
                    this[y+1][x]
                ))
                continue
            }
            this[y][x] = countMines(charArrayOf(
                this[y-1][x-1],
                this[y-1][x],
                this[y-1][x+1],
                this[y][x+1],
                this[y+1][x+1],
                this[y+1][x],
                this[y+1][x-1],
                this[y][x-1]
            ))
        }
    }
}

fun countMines(cells: CharArray): Char {
    val c = cells.count { it == 'X' }
    return if (c == 0) {
        '.'
    } else {
        c.digitToChar()
    }
}

fun Array<CharArray>.print(fieldSize: Int) {
    val fieldRange = 0 until fieldSize
    for (y in fieldRange) {
        for (x in fieldRange) {
            print(this[y][x])
        }
        println("")
    }
}

fun isTopLeftCorner(x: Int, y: Int) = x == LOWER_BOUND && y == LOWER_BOUND
fun isTopRightCorner(x: Int, y: Int) = x == UPPER_BOUND && y == LOWER_BOUND
fun isBottomLeftCorner(x: Int, y: Int) = x == LOWER_BOUND && y == UPPER_BOUND
fun isBottomRightCorner(x: Int, y: Int) = x == UPPER_BOUND && y == UPPER_BOUND
fun isTopSide(x: Int, y: Int) = x != LOWER_BOUND && x != UPPER_BOUND && y == LOWER_BOUND
fun isBottomSide(x: Int, y: Int) = x != LOWER_BOUND && x != UPPER_BOUND && y == UPPER_BOUND
fun isLeftSide(x: Int, y: Int) = x == LOWER_BOUND && y != LOWER_BOUND && y != UPPER_BOUND
fun isRightSide(x: Int, y: Int) = x == UPPER_BOUND && y != LOWER_BOUND && y != UPPER_BOUND