package search_algorithms

var matrix: Array<IntArray> = arrayOf(intArrayOf(), intArrayOf(), intArrayOf(), intArrayOf())

fun main(args: Array<String>) {
    parseData()
}

fun parseData() {
    var line = readLine()
    var i = 0
    while (line != null && i < 4) {
        val values = line.split(' ').map { it.toInt() }.toIntArray()
        matrix[i] = values
        line = readLine()
        i++
    }
    when (line?.toInt()) {
        0 -> moveLeft()
        1 -> moveUp()
        2 -> moveRight()
        3 -> moveDown()
        else -> ""
    }

    for (i in 0..3) {
        for (j in 0..3) {
            if (j != 0) {
                print(" ")
            }
            print(matrix[i][j])
        }
        println()
    }


}

fun moveLeft() {
    for (i in 0..3) {
        var skipNext = false
        for (j in 3 downTo 1) {
            if (skipNext) {
                skipNext = false
            } else if (matrix[i][j] != 0) {
                if (matrix[i][j - 1] == 0) {
                    matrix[i][j - 1] = matrix[i][j]
                } else if (matrix[i][j - 1] == matrix[i][j]) {
                    skipNext = true
                    matrix[i][j - 1] += matrix[i][j]
                    matrix[j][i] = 0
                }
            }
        }
    }
}

fun moveUp() {
    for (i in 0..3) {
        var skipNext = false
        for (j in 3 downTo 1) {
            if (skipNext) {
                skipNext = false
            } else if (matrix[i][j] != 0) {
                if (matrix[j - 1][i] == 0) {
                    matrix[j - 1][i] = matrix[i][j]
                } else if (matrix[j - 1][i] == matrix[i][j]) {
                    skipNext = true
                    matrix[j - 1][i] += matrix[i][j]
                    matrix[j][i] = 0
                }
            }
        }
    }
}

fun moveRight() {
    for (i in 0..3) {
        var skipNext = false
        for (j in 0..2) {
            if (skipNext) {
                skipNext = false
            } else if (matrix[i][j] != 0) {
                if (matrix[i][j + 1] == 0) {
                    matrix[i][j + 1] = matrix[i][j]
                } else if (matrix[i][j + 1] == matrix[i][j]) {
                    skipNext = true
                    matrix[i][j + 1] += matrix[i][j]
                    matrix[j][i] = 0
                }
            }
        }
    }
}

fun moveDown() {
    for (i in 0..3) {
        var skipNext = false
        for (j in 0..2) {
            if (skipNext) {
                skipNext = false
            } else if (matrix[i][j] != 0) {
                if (matrix[j + 1][i] == 0) {
                    matrix[j + 1][i] = matrix[i][j]
                } else if (matrix[j + 1][i] == matrix[i][j]) {
                    skipNext = true
                    matrix[j + 1][i] += matrix[i][j]
                    matrix[j][i] = 0
                }
            }
        }
    }
}
