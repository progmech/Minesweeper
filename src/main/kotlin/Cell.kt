package minesweeper

class Cell {
    var value: CellValue = CellValue.EMPTY
    var state: CellState = CellState.UNMARKED
    fun getPlaceholder(): Char {
        return when (state) {
            CellState.MARKED -> '*'
            CellState.UNMARKED -> '.'
            else -> when (value) {
                CellValue.EMPTY -> '/'
                CellValue.MINE -> 'X'
                else -> valueToDigit(value)
            }
        }
    }

    private fun valueToDigit(v: CellValue): Char {
        return when (v) {
            CellValue.DIGIT_ONE -> '1'
            CellValue.DIGIT_TWO -> '2'
            CellValue.DIGIT_THREE -> '3'
            CellValue.DIGIT_FOUR -> '4'
            CellValue.DIGIT_FIVE -> '5'
            CellValue.DIGIT_SIX -> '6'
            CellValue.DIGIT_SEVEN -> '7'
            CellValue.DIGIT_EIGHT -> '8'
            else -> '0'
        }
    }
}