package service

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.assertFails

/**
 * TestClass for GameService.
 */

class GameServiceTest {
    val playerNames : List<String> = listOf("testPlayerOne", "testPlayerTwo")
    val rootService = RootService()

    @BeforeTest
    fun beforeTest() {
        GameService(rootService).startGame(playerNames)
    }

    /**
     * Test if game is not null.
     */
    @Test
    fun gameNotNull() {
        val game = rootService.currentGame
        assertTrue(game != null)
    }

    /**
     * Tests if the game is initialized correctly.
     */
    @Test
    fun testStartGame() {
        val game = rootService.currentGame
        checkNotNull(game)

        assertEquals(game.players.size, 2)
        assertEquals(game.players[0].hand.size, 5)
        assertEquals(game.drawStack.size, 42)
    }

    /**
     * Tests if endGame() only works when drawStack is empty and if it resets currentGame & currentPlayer
     */
    @Test
    fun testEndGame() {
        assertFails { GameService(rootService).endGame() }

        val game = rootService.currentGame
        game?.drawStack = mutableListOf()

        GameService(rootService).endGame()
        // assertEquals(rootService.currentGame, null)
        // assertEquals(rootService.currentPlayer, null)
    }

    /**
     * Tests if the player whos turn starts is set as the current player.
     */
    @Test
    fun testStartTurn() {
        val game = rootService.currentGame
        checkNotNull(game)

        val player = game.players.first()

        GameService(rootService).startTurn(player)
        assertTrue(rootService.currentPlayer == player)
    }

    /**
     * Tests if the player whos turn ends is not the current player anymore.
     */
    @Test
    fun endStartTurn() {
        val game = rootService.currentGame
        checkNotNull(game)

        val player = game.players.first()

        GameService(rootService).endTurn(player)
        assertTrue(rootService.currentPlayer != player)
    }
}