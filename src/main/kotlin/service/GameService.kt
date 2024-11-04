package service

import entity.Player
import entity.TauchenGame
import kotlin.random.Random

class GameService(private val rootService: RootService): AbstractRefreshingService() {
    /**
     * Starts a game. This includes dealing the players handcards and choosing the starting player.
     *
     * @param playerNames the names of the two players participating in the game.
     */
    fun startGame(playerNames : List<String>) {
        var playerOne = Player(playerNames[0])
        var playerTwo = Player(playerNames[1])

        val game = TauchenGame(arrayOf(playerOne, playerTwo))

        CardService.dealCards()

        val startingPlayer = selectStartingPlayer(arrayOf(playerOne, playerTwo))

        // If player at position 0 in players is the startingPlayer, then he is already the current player.
        if (game.players[1] == startingPlayer) {
            game.isPlayerOneActive = false
        }

        //onAllRefreshables { Refreshable.refreshAfterStartGame() }
    }

    /**
     * Ends the game.
     */
    fun endGame() {
        //onAllRefreshables { Refreshable.refreshAfterEndGame() }
    }

    /**
     * Starts the players turn.
     *
     * @param player the player whose turn starts.
     *
     * @throws IllegalArgumentException if game and/or player are null
     */
    fun startTurn(player : Player) {
        val game = RootService().currentGame
        requireNotNull(game)

        val playerOne = game.players[0]
        requireNotNull(playerOne)
        val playerTwo = game.players[1]
        requireNotNull(playerTwo)

        if (playerOne == player) {
            game.isPlayerOneActive = true
        } else {
            game.isPlayerOneActive = false
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
        val game = RootService().currentGame
        requireNotNull(game)

        val playerOne = game.players[0]
        requireNotNull(playerOne)
        val playerTwo = game.players[1]
        requireNotNull(playerTwo)

        if (playerOne == player) {
            game.isPlayerOneActive = false
        } else {
            game.isPlayerOneActive = true
        }

        //onAllRefreshables { Refreshable.refreshAfterEndTurn() }
    }

    /**
     * Returns the randomized startingPlayer
     *
     * @param players the two players participating in Tauchen.
     */
    fun selectStartingPlayer(players : Array<Player>) : Player {
        val randomIndex = Random.nextInt(1)
        return players[randomIndex]
    }
}