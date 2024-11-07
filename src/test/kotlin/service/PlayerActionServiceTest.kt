package service

import entity.Card
import entity.CardSuit
import entity.CardValue
import entity.Player
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertTrue

/**
 * TestClass for PlayerActionService.
 */

class PlayerActionServiceTest {
    val playerNames : List<String> = listOf("testPlayerOne", "testPlayerTwo")
    val rootService = RootService()

    val aceOfSpades = Card(CardSuit.SPADES, CardValue.ACE)
    val queenOfHearts = Card(CardSuit.HEARTS, CardValue.QUEEN)

    @BeforeTest
    fun beforeTest() {
        GameService(rootService).startGame(playerNames)
    }

    /**
     * Tests if playCard() plays a card on the games playStack and removes a card from the players hand.
     */
    @Test
    fun testPlayCardWorks() {
        val game = rootService.currentGame
        requireNotNull(game) { "Game is null." }
        val player = rootService.currentPlayer
        requireNotNull(player) { "Player is null." }

        PlayerActionService(rootService).playCard(player.hand[0])

        assertEquals(player.hand.size, 4)
        assertEquals(game.playStack.size, 1)
    }

    /**
     * Tests if playCard() allows playing a first, second and third card that fits
     * and emptying it after finishing a trio.
     */
    @Test
    fun testPlayCardCasesTrue() {
        val game = rootService.currentGame
        requireNotNull(game) { "Game is null." }
        val player = rootService.currentPlayer
        requireNotNull(player) { "Player is null." }

        assertDoesNotThrow { PlayerActionService(rootService).playCard((aceOfSpades)) }
        assertDoesNotThrow { PlayerActionService(rootService).playCard((aceOfSpades)) }
        assertDoesNotThrow { PlayerActionService(rootService).playCard((aceOfSpades)) }
        assertDoesNotThrow { PlayerActionService(rootService).playCard((aceOfSpades)) }
        assertDoesNotThrow { PlayerActionService(rootService).playCard((aceOfSpades)) }
    }

    /**
     * Tests if playCard() doesnt allow playing a card that doesnt fit.
     */
    @Test
    fun testPlayCardCasesFalse() {
        val game = rootService.currentGame
        requireNotNull(game) { "Game is null." }
        val player = rootService.currentPlayer
        requireNotNull(player) { "Player is null." }

        game.playStack.add(aceOfSpades)
        assertFails { PlayerActionService(rootService).playCard((queenOfHearts)) }

        game.playStack.add(aceOfSpades)
        assertFails { PlayerActionService(rootService).playCard((queenOfHearts)) }

        PlayerActionService(rootService).playCard((aceOfSpades))
        assertDoesNotThrow { PlayerActionService(rootService).playCard((queenOfHearts)) }
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
     * Tests if endGame() is called when the last card has been drawn.
     */
    @Test
    fun testDrawLastCard() {
        val game = rootService.currentGame
        requireNotNull(game) { "Game is null." }
        val player = rootService.currentPlayer
        requireNotNull(player) { "Player is null." }

        game.drawStack.clear()
        game.drawStack.add(aceOfSpades)
        PlayerActionService(rootService).drawCard()

        assertEquals(rootService.currentGame, null)
        assertEquals(rootService.currentPlayer, null)
    }

    /**
     * Tests if swapCard() works.
     */
    @Test
    fun testSwapCardWorks() {
        val game = rootService.currentGame
        requireNotNull(game) { "Game is null." }
        val player = rootService.currentPlayer
        requireNotNull(player) { "Player is null." }

        //Player plays a card, so his hand size is four and there is one card in the games playStack
        PlayerActionService(rootService).playCard(player.hand[0])

        PlayerActionService(rootService).swapCard(player.hand[0], game.playStack[0])

        assertEquals(player.hand.size, 4)
        assertEquals(game.playStack.size, 1)

        assertEquals(player.hasSpecialAction, false)
    }

    /**
     * Tests if swapCard() works when swapped cards fit.
     */
    @Test
    fun testSwapCardTrue() {
        val game = rootService.currentGame
        requireNotNull(game) { "Game is null." }
        val player = rootService.currentPlayer
        requireNotNull(player) { "Player is null." }

        game.playStack.add(aceOfSpades)
        game.playStack.add(aceOfSpades)

        assertDoesNotThrow{PlayerActionService(rootService).swapCard(aceOfSpades, game.playStack[0])}
    }

    /**
     * Tests if swapCard() doesnt work when swapped cards dont fit.
     */
    @Test
    fun testSwapCardFalse() {
        val game = rootService.currentGame
        requireNotNull(game) { "Game is null." }
        val player = rootService.currentPlayer
        requireNotNull(player) { "Player is null." }

        game.playStack.add(aceOfSpades)
        game.playStack.add(aceOfSpades)

        assertFails{PlayerActionService(rootService).swapCard(queenOfHearts, game.playStack[0])}
    }

    /**
     * Tests if discardCard() works.
     */
    @Test
    fun testDiscardCard() {
        val game = rootService.currentGame
        requireNotNull(game) { "Game is null." }
        val player = rootService.currentPlayer
        requireNotNull(player) { "Player is null." }

        PlayerActionService(rootService).discardCard(player.hand[0])

        assertEquals(player.hand.size, 4)
        assertEquals(game.discardStack.size, 1)
    }
}