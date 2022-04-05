package connectfour

class Board(private val rows: Int = 6, private val cols:Int = 7, val firstPlayer:String, val secondPlayer:String,
            private val numOfGames:Int = 1) {
    private val space = " "
    private var board = Array(rows) { Array(cols) { space } }
    var moveCount = 0
    var isGameOver = false
    var firstPlayerScore = 0
    var secondPlayerScore = 0
    var isDraw = false
    var gameCount = 1
    var isSingleGame = numOfGames == 1


    fun resetBoard(){
        board = Array(rows) { Array(cols) { space } }
    }

    fun printGameBoard() {
        for(i in 1..rows+2) {
            when(i){
                1 -> {
                    for (j in 1..cols) {
                        print(" $j")
                        when(j) {
                            cols -> println(" ")
                        }
                    }
                }
                rows + 2 -> {
                    for (j in 1 .. cols){
                        when(j) {
                            1 -> print("╚")
                            else -> print("═╩")
                        }
                    }
                    println("═╝")
                }
                else ->{
                    for (j in 1..cols){
                        print("║${board[i-2][j-1]}")
                        when(j) {
                            cols -> println("║")
                        }
                    }
                }
            }
        }
    }

    fun placePiece(colChoice:Int, piece: String): Boolean {
        var (colIsFull, rowPlacement) = isMoveValid(colChoice, piece)
        if (colIsFull) {
            println("Column ${colChoice + 1} is full")
            return false
        } else {
            moveCount++
            printGameBoard()
            if (moveCount >= 6) {
                var isWinningMove = checkWin(rowPlacement, colChoice)
                if (isWinningMove) {
                    if(piece == "o") {
                        firstPlayerScore += 2
                        println("Player $firstPlayer won")
                    } else {
                        secondPlayerScore += 2
                        println("Player $secondPlayer won")
                    }
                    if(!isSingleGame){
                        println("Score")
                        println("$firstPlayer: $firstPlayerScore $secondPlayer: $secondPlayerScore")
                    }
                    if (gameCount < numOfGames) {
                        resetGame()
                    } else {
                        isGameOver = true
                    }
                    gameCount++
                    return true
                }
                isDraw = moveCount == rows * cols
                if(isDraw) {
                    println("It is a draw")
                    firstPlayerScore++
                    secondPlayerScore++
                    if(!isSingleGame){
                        println("Score")
                        println("$firstPlayer: $firstPlayerScore $secondPlayer: $secondPlayerScore")
                    }
                    if (gameCount < numOfGames) resetGame() else isGameOver = true
                    gameCount++

                }
            }
        }
        return true
    }

    fun isMoveValid(col:Int, piece: String): Pair<Boolean, Int> {
        for (i in board.lastIndex downTo 0) {
            if (board[i][col] == space) {
                board[i][col] = piece
                return Pair(false, i)
            }
        }
        return Pair(true, 0)
    }

    fun resetGame() {
        resetBoard()
        isGameOver = false
        moveCount = 0
    }

    fun checkWin(row: Int, col: Int): Boolean{
        val height = board.size
        val width = board[0].size
        val piece = board[row][col]
        if(col + 3 < width &&
            piece == board[row][col+1] &&
            piece == board[row][col+2] &&
            piece == board[row][col+3]) {
            return true
        }
        if(col - 3 >= 0 &&
            piece == board[row][col-1] &&
            piece == board[row][col-2] &&
            piece == board[row][col-3]) {
            return true
        }
        if(row + 3 < height) {
            if(piece == board[row + 1][col] &&
                piece == board[row + 2][col] &&
                piece == board[row + 3][col]) {
                return true
            }
            if(col + 3 < width &&
                piece == board[row+1][col+1] &&
                piece == board[row+2][col+2] &&
                piece == board[row+3][col+3]){
                return true
            }
            if(col - 3 >= 0 &&
                piece == board[row+1][col-1] &&
                piece == board[row+2][col-2] &&
                piece == board[row+3][col-3]){
                return true
            }
        }
        if(row - 3 >= 0) {
            if(piece == board[row - 1][col] &&
                piece == board[row - 2][col] &&
                piece == board[row - 3][col]) {
                return true
            }
            if(col + 3 < width &&
                piece == board[row-1][col+1] &&
                piece == board[row-2][col+2] &&
                piece == board[row-3][col+3]){
                return true
            }
            if(col - 3 >= 0 &&
                piece == board[row-1][col-1] &&
                piece == board[row-2][col-2] &&
                piece == board[row-3][col-3]){
                return true
            }
        }
        return false
    }
}