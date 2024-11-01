package entity

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

/**
 * TestClass for Player
 */

class PlayerTest {
    val aceOfSpades = Card(CardSuit.SPADES, CardValue.ACE)
    var testPlayer = Player("PlayerOne")

    @Test
            /**
             * Test if collectionStack & hand are initialized correctly
             */
    fun testInitializeHand() {
        assertTrue(testPlayer.collectionStack.isEmpty())
        assertTrue(testPlayer.hand.isEmpty())
    }
}