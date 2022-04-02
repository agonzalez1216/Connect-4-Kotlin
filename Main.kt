const val space = " "
const val player1 = "o"
    const val player2 = "*"
    private var currentPlayer = ""
    fun main() {
        val validRange = 5..9
        var rows = 0
        var cols = 0
        var validBoardSize = false
        var gameIsOn = true
        val regex = Regex("\\d?\\d(x|X)\\d?\\d")
        val singleDigit = Regex("\\d+")
    var moveCount = 0
    var isDraw = false
    var isWinningMove = false
    println("Connect Four")

    println("First player's name:")
    val firstPlayer = readLine()

    println("Second player's name:")
    val secondPlayer = readLine()

    while(!validBoardSize) {
        println("Set the board dimensions (Rows x Columns)")
        println("Press Enter for default (6 x 7)")

        var boardSize = readLine()
        if (boardSize == "") {
            rows = 6
            cols = 7
            validBoardSize = true
        } else {
            boardSize = boardSize!!.trim()
            boardSize = boardSize.replace("\\s".toRegex(), "")
            if(boardSize.matches(regex)) {
                val splitList = boardSize.split("x", "X", " ", "\t").toMutableList()
                splitList.removeAll { it == "" }
                splitList.removeAll(listOf(" "))
                splitList.removeAll { it == "\t" }
                rows = splitList[0].toInt()
                cols = splitList[1].toInt()
                if (rows in validRange && cols in validRange){
                    validBoardSize = true
                } else if (rows !in validRange) {
                    println("Board rows should be from 5 to 9")
                } else if (cols !in validRange) {
                    println("Board columns should be from 5 to 9")
                }
            } else {
                println("Invalid input")
            }
        }
    }
    println("$firstPlayer VS $secondPlayer")
    println("$rows X $cols board")
    val range = 1..cols
    val gameBoard = createGameBoard(rows,cols)
    printGameBoard(rows, cols, gameBoard)
    takeTurns()
    while(gameIsOn){
        if (currentPlayer == player1) {
            println("$firstPlayer's turn:")
        } else{
            println("$secondPlayer's turn")
        }
        val inputText = readLine()!!
        if (inputText == "end"){
            gameIsOn = false
            break
        }
        if (!inputText.matches(singleDigit)){
            println("Incorrect column number")
        } else {
            var colChoice = inputText.toInt()
            if (colChoice in range) {
                colChoice--
                val (isFull, rowPlacement) = placePiece(colChoice, gameBoard, currentPlayer)
                if (!isFull) {
                    printGameBoard(rows, cols, gameBoard)
                    if (moveCount >= 6) {
                        isWinningMove = checkWin(gameBoard, rowPlacement, colChoice)
                        if (isWinningMove) break
                    }
                    moveCount++
                    isDraw = moveCount == rows * cols
                    if (isDraw) break
                    takeTurns()
                } else {
                    println("Column ${colChoice + 1} is full")
                }
            } else {
                println("The column number is out of range (1 - $cols)")
            }
        }
    }
    if(gameIsOn) {
        if (isDraw) {
            println("It is a draw")
        } else {
            if (currentPlayer == player1) {
                println("Player $firstPlayer won")
            } else {
                println("Player $secondPlayer won")
            }
        }
    }
    println("Game over!")
}
fun createGameBoard(rows: Int, cols: Int): Array<Array<String>> {
    return Array(rows) { Array(cols) { space } }
}
fun printGameBoard(rows: Int, cols: Int, gameBoard: Array<Array<String>>) {
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
                    print("║${gameBoard[i-2][j-1]}")
                    when(j) {
                        cols -> println("║")
                    }
                }
            }
        }
    }
}

fun placePiece(col:Int, gameBoard: Array<Array<String>>, piece: String): Pair<Boolean, Int> {
    for (i in gameBoard.lastIndex downTo 0) {
        if (gameBoard[i][col] == space) {
            gameBoard[i][col] = piece
            return Pair(false, i)
        }
    }
    return Pair(true, 0)
}

fun takeTurns(){
    currentPlayer = if(player1 == currentPlayer){
        player2
    } else {
        player1
    }
}

fun checkWin(gameBoard: Array<Array<String>>, row: Int, col: Int): Boolean{
    val height = gameBoard.size
    val width = gameBoard[0].size
    val piece = gameBoard[row][col]
    if(col + 3 < width &&
        piece == gameBoard[row][col+1] &&
        piece == gameBoard[row][col+2] &&
        piece == gameBoard[row][col+3]) {
        return true
    }
    if(col - 3 >= 0 &&
        piece == gameBoard[row][col-1] &&
        piece == gameBoard[row][col-2] &&
        piece == gameBoard[row][col-3]) {
        return true
    }
    if(row + 3 < height) {
        if(piece == gameBoard[row + 1][col] &&
            piece == gameBoard[row + 2][col] &&
            piece == gameBoard[row + 3][col]) {
            return true
        }
        if(col + 3 < width &&
            piece == gameBoard[row+1][col+1] &&
            piece == gameBoard[row+2][col+2] &&
            piece == gameBoard[row+3][col+3]){
            return true
        }
        if(col - 3 >= 0 &&
            piece == gameBoard[row+1][col-1] &&
            piece == gameBoard[row+2][col-2] &&
            piece == gameBoard[row+3][col-3]){
            return true
        }
    }
    if(row - 3 >= 0) {
        if(piece == gameBoard[row - 1][col] &&
            piece == gameBoard[row - 2][col] &&
            piece == gameBoard[row - 3][col]) {
            return true
        }
        if(col + 3 < width &&
            piece == gameBoard[row-1][col+1] &&
            piece == gameBoard[row-2][col+2] &&
            piece == gameBoard[row-3][col+3]){
            return true
        }
        if(col - 3 >= 0 &&
            piece == gameBoard[row-1][col-1] &&
            piece == gameBoard[row-2][col-2] &&
            piece == gameBoard[row-3][col-3]){
            return true
        }
    }
    return false
}