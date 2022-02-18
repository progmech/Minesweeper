package minesweeper

import kotlin.random.Random

const val FIELD_SIZE = 9

fun main() {
    val fieldRange = 0 until FIELD_SIZE
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
    var mineCount = readLine()!!.toInt()

    while(mineCount != 0) {
        val newX = Random.nextInt(0, FIELD_SIZE)
        val newY = Random.nextInt(0, FIELD_SIZE)
        if (field[newY][newX] == 'X') continue
        field[newY][newX] = 'X'
        mineCount--
    }

    for (y in fieldRange) {
        for (x in fieldRange) {
            print(field[y][x])
        }
        println("")
    }
}