package minesweeper

import kotlin.random.Random

const val FIELD_SIZE = 9
const val LOWER_BOUND = 0
const val UPPER_BOUND = FIELD_SIZE - 1

enum class Cells(val placeholder: Char) {
    EMPTY('.'),
    EMPTY_MARKED('*'),
    MINE('.'),
    MINE_MARKED('*'),
    DIGIT_ONE('1'),
    DIGIT_TWO('2'),
    DIGIT_THREE('3'),
    DIGIT_FOUR('4'),
    DIGIT_FIVE('5'),
    DIGIT_SIX('6'),
    DIGIT_SEVEN('7'),
    DIGIT_EIGHT('8');

    companion object {
        fun convertDigitToCell(digit: Int): Cells {
            return when (digit) {
                1 -> DIGIT_ONE
                2 -> DIGIT_TWO
                3 -> DIGIT_THREE
                4 -> DIGIT_FOUR
                5 -> DIGIT_FIVE
                6 -> DIGIT_SIX
                7 -> DIGIT_SEVEN
                8 -> DIGIT_EIGHT
                else -> EMPTY
            }
        }

        fun isDigit(cell: Cells): Boolean {
            val digits = arrayOf(DIGIT_ONE, DIGIT_TWO, DIGIT_THREE, DIGIT_FOUR, DIGIT_FIVE, DIGIT_SIX, DIGIT_SEVEN, DIGIT_EIGHT)
            return digits.contains(cell)
        }
    }
}

fun main() {

    val field = arrayOf(
        arrayOf(Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY),
        arrayOf(Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY),
        arrayOf(Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY),
        arrayOf(Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY),
        arrayOf(Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY),
        arrayOf(Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY),
        arrayOf(Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY),
        arrayOf(Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY),
        arrayOf(Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY, Cells.EMPTY),
    )

    println("How many mines do you want on the field?")
    var mineCount = readLine()!!.toInt()
    field.setMines(mineCount)
    field.fill()
    field.print(FIELD_SIZE)
    do {
        println("Set/delete mine marks (x and y coordinates):")
        val (x, y) = readLine()!!.split(' ').map { it.toInt() - 1 }
        if (Cells.isDigit(field[y][x])) {
            println("There is a number here!")
            continue
        } else {
            mineCount += field.applySelection(x, y)
            field.print(FIELD_SIZE)
            println(mineCount)
        }
    } while (mineCount != 0)
    println("Congratulations! You found all the mines!")
}

fun Array<Array<Cells>>.applySelection(x: Int, y: Int): Int {
    var result = 0
    when (this[y][x]) {
        Cells.EMPTY -> this[y][x] = Cells.EMPTY_MARKED
        Cells.EMPTY_MARKED -> this[y][x] = Cells.EMPTY
        Cells.MINE -> {
            this[y][x] = Cells.MINE_MARKED
            result = -1
        }
        else -> {
            this[y][x] = Cells.MINE
            result = 1
        }
    }
    return result
}

fun Array<Array<Cells>>.setMines(mineCount: Int) {
    var count = mineCount
    while(count != 0) {
        val newX = Random.nextInt(0, FIELD_SIZE)
        val newY = Random.nextInt(0, FIELD_SIZE)
        if (this[newY][newX] == Cells.MINE) continue
        this[newY][newX] = Cells.MINE
        count--
    }
}

fun Array<Array<Cells>>.fill() {
    for (y in 0 until FIELD_SIZE) {
        for (x in 0 until FIELD_SIZE) {
            if (this[y][x] == Cells.MINE) continue
            if (isTopLeftCorner(x, y)) {
                this[y][x] = countMines(arrayOf(this[y + 1][x], this[y][x + 1], this[y + 1][x + 1]))
                continue
            }
            if (isTopRightCorner(x, y)) {
                this[y][x] = countMines(arrayOf(this[y][x - 1], this[y + 1][x], this[y + 1][x - 1]))
                continue
            }
            if (isBottomLeftCorner(x, y)) {
                this[y][x] = countMines(arrayOf(this[y-1][x], this[y][x+1], this[y - 1][x + 1]))
                continue
            }
            if (isBottomRightCorner(x, y)) {
                this[y][x] = countMines(arrayOf(this[y-1][x], this[y][x-1], this[y - 1][x - 1]))
                continue
            }
            if (isTopSide(x, y)) {
                this[y][x] = countMines(arrayOf(
                    this[y][x-1],
                    this[y+1][x-1],
                    this[y+1][x],
                    this[y+1][x+1],
                    this[y][x + 1]
                ))
                continue
            }
            if (isBottomSide(x, y)) {
                this[y][x] = countMines(arrayOf(
                    this[y][x-1],
                    this[y-1][x-1],
                    this[y-1][x],
                    this[y-1][x+1],
                    this[y][x + 1]
                ))
                continue
            }
            if (isLeftSide(x, y)) {
                this[y][x] = countMines(arrayOf(
                    this[y-1][x],
                    this[y-1][x+1],
                    this[y][x+1],
                    this[y+1][x+1],
                    this[y+1][x]
                ))
                continue
            }
            if (isRightSide(x, y)) {
                this[y][x] = countMines(arrayOf(
                    this[y-1][x],
                    this[y-1][x-1],
                    this[y][x-1],
                    this[y+1][x-1],
                    this[y+1][x]
                ))
                continue
            }
            this[y][x] = countMines(arrayOf(
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

fun countMines(cells: Array<Cells>): Cells {
    val c = cells.count { it == Cells.MINE }
    return Cells.convertDigitToCell(c)
}

fun Array<Array<Cells>>.print(fieldSize: Int) {
    println(" │123456789│")
    println("—│—————————│")
    val fieldRange = 0 until fieldSize
    for (y in fieldRange) {
        print("${y + 1}│")
        for (x in fieldRange) {
            if (this[y][x] == Cells.MINE) {
                print('.')
            } else {
                print(this[y][x].placeholder)
            }
        }
        println("│")
    }
    println("—│—————————│")
}

fun isTopLeftCorner(x: Int, y: Int) = x == LOWER_BOUND && y == LOWER_BOUND
fun isTopRightCorner(x: Int, y: Int) = x == UPPER_BOUND && y == LOWER_BOUND
fun isBottomLeftCorner(x: Int, y: Int) = x == LOWER_BOUND && y == UPPER_BOUND
fun isBottomRightCorner(x: Int, y: Int) = x == UPPER_BOUND && y == UPPER_BOUND
fun isTopSide(x: Int, y: Int) = x != LOWER_BOUND && x != UPPER_BOUND && y == LOWER_BOUND
fun isBottomSide(x: Int, y: Int) = x != LOWER_BOUND && x != UPPER_BOUND && y == UPPER_BOUND
fun isLeftSide(x: Int, y: Int) = x == LOWER_BOUND && y != LOWER_BOUND && y != UPPER_BOUND
fun isRightSide(x: Int, y: Int) = x == UPPER_BOUND && y != LOWER_BOUND && y != UPPER_BOUND