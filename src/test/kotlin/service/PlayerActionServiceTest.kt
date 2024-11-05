package service

import org.junit.jupiter.api.Test
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

/**
 * TestClass for PlayerActionService.
 */

class PlayerActionServiceTest {
    val playerNames : List<String> = listOf("testPlayerOne", "testPlayerTwo")
    val rootService = RootService()

    @BeforeTest
    fun beforeTest() {
        GameService(rootService).startGame(playerNames)
    }

    /**
     * Tests if playCard() works.
     */
    @Test
    fun testPlayCard() {
        val game = rootService.currentGame
        val player = rootService.currentPlayer

        player?.hand?.get(0)?.let { PlayerActionService(rootService).playCard(it) }

        assertEquals(player?.hand?.size, 4)
        assertEquals(game?.playStack?.size, 1)
    }

    /**
     * Tests if drawCard() works.
     */
    @Test
    fun testDrawCard() {
        val game = rootService.currentGame
        val player = rootService.currentPlayer

        PlayerActionService(rootService).drawCard()

        assertEquals(player?.hand?.size, 6)
        assertEquals(game?.drawStack?.size, 41)
    }

    /**
     * Tests if swapCard() works.
     */
    @Test
    fun testSwapCard() {
        val game = rootService.currentGame
        val player = rootService.currentPlayer

        //Player plays a card, so his hand size is four and there is one card in the games playStack
        player?.hand?.get(0)?.let { PlayerActionService(rootService).playCard(it) }

        if (player != null) {
            if (game != null) {
                PlayerActionService(rootService).swapCard(player.hand[0], game.playStack[0])
            }
        }

        assertEquals(player?.hand?.size, 4)
        assertEquals(game?.playStack?.size, 1)
    }

    /**
     * Tests if discardCard() works.
     */
    @Test
    fun testDiscardCard() {
        val game = rootService.currentGame
        val player = rootService.currentPlayer

        player?.hand?.get(0)?.let { PlayerActionService(rootService).discardCard(it) }

        assertEquals(player?.hand?.size, 4)
        assertEquals(game?.discardStack?.size, 1)
    }
}