package minesweeper
fun main(args: Array<String>) {
    val field = arrayOf(
        arrayOf('.', '.', '.', '.', '.', '.', '.', '.', '.'),
        arrayOf('.', '.', '.', '.', '.', '.', '.', '.', '.'),
        arrayOf('.', '.', '.', '.', '.', '.', '.', '.', '.'),
        arrayOf('.', '.', '.', '.', '.', '.', '.', '.', '.'),
        arrayOf('.', '.', '.', '.', '.', '.', '.', '.', '.'),
        arrayOf('.', '.', '.', '.', '.', '.', '.', '.', '.'),
        arrayOf('.', '.', '.', '.', '.', '.', '.', '.', '.'),
        arrayOf('.', '.', '.', '.', '.', '.', '.', '.', '.'),
        arrayOf('.', '.', '.', '.', '.', '.', '.', '.', '.'),
    )
    var mineCount = 10
    for (y in 0..8) {
        for (x in 0..8) {
            if (mineCount != 0) {
                if (x % 2 == 0 && y % 2 == 0) {
                    mineCount--
                    field[y][x] = 'X'
                }
            }
        }
    }
    for (y in 0..8) {
        for (x in 0..8) {
            print(field[x][y])
        }
        println("")
    }
}