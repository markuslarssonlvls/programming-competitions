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
        0 -> {
            move(true,false, false)
            move(true,false, false)
            move(true,false, false)
            move(true,false, false)
            move(false,false, false)
            move(true,false, false)
            move(true,false, false)
            move(true,false, false)
            move(true,false, false)
        }
        1 -> {
            move(true,true, false)
            move(true,true, false)
            move(true,true, false)
            move(true,true, false)
            move(false,true, false)
            move(true,true, false)
            move(true,true, false)
            move(true,true, false)
            move(true,true, false)
        }
        2 -> {
            move(true,false, true)
            move(true,false, true)
            move(true,false, true)
            move(true,false, true)
            move(false,false, true)
            move(true,false, true)
            move(true,false, true)
            move(true,false, true)
            move(true,false, true)
        }
        3 -> {
            move(true,true, true)
            move(true,true, true)
            move(true,true, true)
            move(true,true, true)
            move(false,true, true)
            move(true,true, true)
            move(true,true, true)
            move(true,true, true)
            move(true,true, true)
        }
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

fun moveLeft(removeZeros: Boolean) {
    for (i in 0..3) {
        for (j in 0..2) {
            if (removeZeros) {
                if (matrix[i][j] == 0) {
                    matrix[i][j] = matrix[i][j + 1]
                    matrix[i][j + 1] = 0
                }
            } else {
                if (matrix[i][j + 1] == matrix[i][j]) {
                    matrix[i][j] += matrix[i][j + 1]
                    matrix[i][j + 1] = 0
                }
            }
        }
    }
}

fun moveRight(removeZeros: Boolean) {
    for (i in 0..3) {
        for (j in 0..2) {
            if (removeZeros) {
                if (matrix[i][3 - j] == 0) {
                    matrix[i][3 - j] = matrix[i][3 - j - 1]
                    matrix[i][3 - j - 1] = 0
                }
            } else {
                if (matrix[i][3 - j - 1] == matrix[i][3 - j]) {
                    matrix[i][3 - j] += matrix[i][3 - j - 1]
                    matrix[i][3 - j - 1] = 0
                }
            }
        }
    }
}

fun moveDown(removeZeros: Boolean) {
    for (i in 0..3) {
        for (j in 0..2) {
            if (removeZeros) {
                if (matrix[3 - j][i] == 0) {
                    matrix[3 - j][i] = matrix[3 - j - 1][i]
                    matrix[3 - j - 1][i] = 0
                }
            } else {
                if (matrix[3 - j - 1][i] == matrix[3 - j][i]) {
                    matrix[3 - j][i] += matrix[3 - j - 1][i]
                    matrix[3 - j - 1][i] = 0
                }
            }
        }
    }
}

fun moveUp(removeZeros: Boolean) {
    for (i in 0..3) {
        for (j in 0 .. 2) {
            if (removeZeros) {
                if (matrix[j][i] == 0) {
                    matrix[j][i] = matrix[j + 1][i]
                    matrix[j + 1][i] = 0
                }
            } else {
                if (matrix[j + 1][i] == matrix[j][i]) {
                    matrix[j][i] += matrix[j + 1][i]
                    matrix[j + 1][i] = 0
                }
            }
        }
    }
}

fun move(removeZeros: Boolean, vertical: Boolean, reverse: Boolean) {
    for (i in 0..3) {
        for (j in 0..2) {
            val xxx = if (reverse ) 3 - j else j
            val xxxDiff = if(reverse ) -1 else 1

            val x1 = xxx
            val x2 = xxx + xxxDiff

            var k = x1
            var l = j
            var k2 = x2
            var l2 = j
            if (vertical) {
                val temp = l
                k = l
                l = temp
                val temp2 = l
                k2 = l2
                l2 = temp2
            }

            if (removeZeros) {
                if (matrix[k][l] == 0) {
                    matrix[k][l] = matrix[k2][l2]
                    matrix[k2][l2] = 0
                }
            } else {
                if (matrix[k2][l2] == matrix[k][l]) {
                    matrix[k][l] += matrix[k2][l2]
                    matrix[k2][l2] = 0
                }
            }
        }
    }
}
