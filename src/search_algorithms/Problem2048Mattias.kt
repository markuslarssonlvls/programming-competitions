package search_algorithms


fun main(args: Array<String>) {
    val boardAndMove = parse()
    val board = boardAndMove.board
    val move = boardAndMove.move

    var fromX: Int = 0
    var toX: Int = 3
    var fromY: Int = 0
    var toY: Int = 3
    var direction = 1
    when (move) {
        // Left
        0 -> {
            toX = 3
        }
        // Up
        1 -> {
            toY = 3
        }
        // Right
        2 -> {
            fromX = 3
            direction = -1
        }
        // Down
        3 -> {
            fromY = 3
            direction = -1
        }
    }

    var newBoard = Array(4) { y ->
        Array(4) { x ->
            board[y][x]
        }
    }
    var prevBoard = Array<Array<Int>>(4) {
        Array(4) {
            1
        }
    }

    while (!newBoard.contentEquals(prevBoard)) {
        prevBoard = newBoard.copyOf()
        if (move == 1 || move == 3) {
            for (x in fromX until toX) {
                for (y in fromY until toY) {
                    if (newBoard[y][x] == newBoard[y + direction][x]) {
                        newBoard[y][x] = newBoard[y][x] + newBoard[y + direction][x]
                        newBoard[y + direction][x] = 0
                    } else if (newBoard[x][y] == 0) {
                        newBoard[y][x] = newBoard[y + direction][x]
                        newBoard[y + direction][x] = 0
                    }
                }
            }
        } else {
            for (y in fromY until toY) {
                for (x in fromX until toX) {
                    if (newBoard[y][x] == newBoard[y][x + direction]) {
                        newBoard[y][x] = newBoard[y][x + direction] + newBoard[y][x + direction]
                        newBoard[y][x + direction] = 0
                    } else if (newBoard[x][y] == 0) {
                        newBoard[y][x] = newBoard[y][x + direction]
                        newBoard[y][x + direction] = 0
                    }
                }
            }
        }
    }

    newBoard.forEach {
        print(it.joinToString(" ") + "\n")
    }
}


fun parse(): BoardAndMove {
    var result = ""
    result += readLine() + "\n"
    result += readLine() + "\n"
    result += readLine() + "\n"
    result += readLine() + "\n"
    result += readLine() + "\n"

    print(result)

    val rows = result.split("\n")
    val board = mutableListOf<List<Int>>()

    board += (rows[0].split(" ").map { it.toInt() })
    board += rows[1].split(" ").map { it.toInt() }
    board += rows[2].split(" ").map { it.toInt() }
    board += rows[3].split(" ").map { it.toInt() }

    val move = rows[4].toInt()

    return BoardAndMove(board, move)
}

class BoardAndMove(val board: List<List<Int>>, val move: Int)