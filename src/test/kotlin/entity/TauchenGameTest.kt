package entity

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue
import kotlin.test.assertEquals
import kotlin.test.assertFails

/**
 * TestClass for TauchenGame
 */

class TauchenGameTest {
    // val aceOfSpades = Card(CardSuit.SPADES, CardValue.ACE)
    val testPlayer = Player("testPlayer")
    val testGame = TauchenGame(players = arrayOf(testPlayer, testPlayer))

    @Test
            /**
             * Test if players, drawStack, playStack, discardStack are initialized correctly
             */
    fun testInitialize() {
        assertEquals(testGame.players.size, 2)
        assertTrue(testGame.drawStack.isEmpty())
        assertTrue(testGame.playStack.isEmpty())
        assertTrue(testGame.discardStack.isEmpty())
    }

    @Test
            /**
             * Test if players must be exactly == 2
             */
    fun testTwoPlayers() {
        assertFails {
            val testGame = TauchenGame(players = arrayOf(testPlayer, testPlayer, testPlayer))
        }
        assertFails {
            val testGame = TauchenGame(players = arrayOf(testPlayer))
        }
    }
}
