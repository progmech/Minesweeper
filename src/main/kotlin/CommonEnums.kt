package minesweeper

enum class Command {
    EXPLORE,
    MARK
}

enum class CellValue {
    EMPTY,
    MINE,
    DIGIT_ONE,
    DIGIT_TWO,
    DIGIT_THREE,
    DIGIT_FOUR,
    DIGIT_FIVE,
    DIGIT_SIX,
    DIGIT_SEVEN,
    DIGIT_EIGHT;

    companion object {
        fun convertDigitToCellValue(digit: Int): CellValue {
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
    }
}

enum class CellState() {
    MARKED,
    UNMARKED,
    EXPLORED;

    companion object {
        fun changeCellState(currentState: CellState, command: Command): CellState {
            return if (command == Command.MARK) {
                when(currentState) {
                    MARKED -> UNMARKED
                    UNMARKED -> MARKED
                    else -> EXPLORED
                }
            } else {
                EXPLORED
            }
        }
    }
}