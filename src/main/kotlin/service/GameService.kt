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

        val game = rootService.currentGame
        rootService.currentGame = TauchenGame(players = arrayOf(playerOne, playerTwo))

        rootService.currentPlayer = selectStartingPlayer(arrayOf(playerOne, playerTwo))

        // If player at position 0 in players is the startingPlayer, then he is already the current player.
        if (game != null && game.players[1] == rootService.currentPlayer) {
            rootService.currentGame!!.isPlayerOneActive = false
        }

        rootService.currentGame?.drawStack = CardService(rootService).createDrawStack()
        CardService(rootService).dealCards()

        onAllRefreshables { refreshAfterStartGame() }
    }

    /**
     * Ends the game if the games drawStack is empty.
     */
    fun endGame() {
        val game = rootService.currentGame
        requireNotNull(game) {"Game is null."}

        require(game.drawStack.size == 0) {"DrawStack must empty to end the game."}

        onAllRefreshables { refreshAfterEndGame() }
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
        val playerTwo = game.players[1]

        if (playerOne == player) {
            rootService.currentGame?.isPlayerOneActive = true
            rootService.currentPlayer = rootService.currentGame?.players?.first()
        } else {
            rootService.currentGame?.isPlayerOneActive = false
            rootService.currentPlayer = rootService.currentGame?.players?.last()
        }

        onAllRefreshables { refreshAfterStartTurn() }
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
        val playerTwo = game.players[1]

        if (playerOne == player) {
            rootService.currentGame?.isPlayerOneActive = false
            rootService.currentPlayer = rootService.currentGame?.players?.last()
        } else {
            rootService.currentGame?.isPlayerOneActive = true
            rootService.currentPlayer = rootService.currentGame?.players?.first()
        }

        onAllRefreshables { refreshAfterEndTurn() }
    }

    /**
     * Returns the randomized startingPlayer
     *
     * @param players the two players participating in Tauchen.
     */
    private fun selectStartingPlayer(players : Array<Player>) : Player {
        players.shuffle()
        return players[0]
    }
}