import connectfour.Board

const val player1 = "o"
const val player2 = "*"
private var currentPlayer = ""
fun main() {
    val validRange = 5..9
    var rows = 0
    var cols = 0
    var validBoardSize = false
    var validGameNumber = false
    var gameIsOn = true
    val regex = Regex("\\d?\\d(x|X)\\d?\\d")
    val singleDigit = Regex("\\d+")
    var moveCount = 0
    var isDraw = false
    var isWinningMove = false
    var numberOfGames:Int? = 1
    var moveSuccessful = false
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

    while(!validGameNumber) {
        println(
            "Do you want to play single or multiple games?\n" +
                    "For a single game, input 1 or press Enter\n" +
                    "Input a number of games:"
        )
        val numberOfGamesText = readLine()
        if (numberOfGamesText == "") {
            numberOfGames = 1
            break
        }
        numberOfGames = numberOfGamesText!!.toIntOrNull()
        if (numberOfGames == null || numberOfGames == 0) {
            println("Invalid Input")
        } else {
            validGameNumber = true
        }
    }

    println("$firstPlayer VS $secondPlayer")
    println("$rows X $cols board")
    val range = 1..cols
    val gameBoard = Board(rows,cols, firstPlayer!!, secondPlayer!!, numberOfGames!!)
    if(gameBoard.isSingleGame){
        println("Single Game")
        gameBoard.printGameBoard()
    } else {
        println("Total $numberOfGames games")
        println("Game #${gameBoard.gameCount}")
        gameBoard.printGameBoard()
    }

    takeTurns()

    while(gameBoard.gameCount <= numberOfGames){
        if (gameBoard.moveCount == 0 && !gameBoard.isSingleGame && moveSuccessful){
            println("Game #${gameBoard.gameCount}")
            gameBoard.printGameBoard()
        }
        if (currentPlayer == player1) {
            println("$firstPlayer's turn:")
        } else{
            println("$secondPlayer's turn")
        }
        val inputText = readLine()!!
        if (inputText == "end"){
            break
        }
        if (!inputText.matches(singleDigit)){
            println("Incorrect column number")
        } else {
            var colChoice = inputText.toInt()
            if (colChoice in range) {
                colChoice--
                moveSuccessful = gameBoard.placePiece(colChoice, currentPlayer)
                if (moveSuccessful) takeTurns()
            } else {
                println("The column number is out of range (1 - $cols)")
            }
        }
    }
    println("Game over!")
}


fun takeTurns(){
    currentPlayer = if(player1 == currentPlayer){
        player2
    } else {
        player1
    }
}

