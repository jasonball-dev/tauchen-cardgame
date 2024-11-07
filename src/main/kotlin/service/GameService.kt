package service

import entity.Player
import entity.TauchenGame
import kotlin.random.Random

/**
 * Service layer class that provides the logic for actions not directly
 * related to a single player.
 */


class GameService(private val rootService: RootService): AbstractRefreshingService() {
    /**
     * Starts a game. This includes dealing the players handcards and choosing the starting player.
     *
     * @param playerNames the names of the two players participating in the game.
     */
    fun startGame(playerNames : List<String>) {
        val playerOne = Player(playerNames[0])
        val playerTwo = Player(playerNames[1])

        rootService.currentGame = TauchenGame(players = arrayOf(playerOne, playerTwo))
        val game = rootService.currentGame

        rootService.currentPlayer = selectStartingPlayer(arrayOf(playerOne, playerTwo))

        // If player at position 0 in players is the startingPlayer, then he is already the current player.
        if (game != null && game.players[1] == rootService.currentPlayer) {
            rootService.currentGame!!.isPlayerOneActive = false
        }

        rootService.currentGame?.drawStack = CardService(rootService).createDrawStack()
        CardService(rootService).dealCards()

        //onAllRefreshables { Refreshable.refreshAfterStartGame() }
    }

    /**
     * Ends the game if the games drawStack is empty.
     */
    fun endGame() {
        val game = rootService.currentGame
        requireNotNull(game) {"Game is null."}

        require(game.drawStack.size == 0) {"DrawStack must empty to end the game."}

        //onAllRefreshables { Refreshable.refreshAfterEndGame() }
        rootService.currentGame = null
        rootService.currentPlayer = null
    }

    /**
     * Starts the players turn.
     *
     * @param player the player whose turn starts.
     *
     * @throws IllegalArgumentException if game and/or player are null
     */
    fun startTurn(player : Player) {
        val game = rootService.currentGame
        requireNotNull(game) {"The game is null."}

        val playerOne = game.players[0]
        requireNotNull(playerOne) {"Player one is null."}
        val playerTwo = game.players[1]
        requireNotNull(playerTwo) {"Player two is null."}

        if (playerOne == player) {
            rootService.currentGame?.isPlayerOneActive = true
            rootService.currentPlayer = rootService.currentGame?.players?.first()
        } else {
            rootService.currentGame?.isPlayerOneActive = false
            rootService.currentPlayer = rootService.currentGame?.players?.last()
        }

        //onAllRefreshables { Refreshable.refreshAfterStartTurn() }
    }

    /**
     * Ends the players turn.
     *
     * @param player the player whose turn ends.
     *
     * @throws IllegalArgumentException if game and/or player are null
     */
    fun endTurn(player : Player) {
        val game = rootService.currentGame
        requireNotNull(game) {"Game is null."}

        val playerOne = game.players[0]
        requireNotNull(playerOne) {"PlayerOne is null."}
        val playerTwo = game.players[1]
        requireNotNull(playerTwo) {"PlayerTwo is null."}

        if (player.hand.size > 8) {
            //discardCard() ???
        }

        if (playerOne == player) {
            rootService.currentGame?.isPlayerOneActive = false
            rootService.currentPlayer = rootService.currentGame?.players?.last()
        } else {
            rootService.currentGame?.isPlayerOneActive = true
            rootService.currentPlayer = rootService.currentGame?.players?.first()
        }

        //onAllRefreshables { Refreshable.refreshAfterEndTurn() }
    }

    /**
     * Returns the randomized startingPlayer
     *
     * @param players the two players participating in Tauchen.
     */
    private fun selectStartingPlayer(players : Array<Player>) : Player {
        val randomIndex = Random.nextInt(1)
        return players[randomIndex]
    }
}