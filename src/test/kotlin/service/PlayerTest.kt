package service

import entity.Card
import entity.CardSuit
import entity.CardValue
import entity.Player
import org.junit.jupiter.api.Test
import kotlin.test.assertFails

class PlayerTest {
    val aceOfSpades = Card(CardSuit.SPADES, CardValue.ACE)

    /**
     * Test if hand must be <= 9
     */
    @Test
    fun testHandSize() {
        assertFails {
            val testPlayer = Player(
                "PlayerOne",
                23,
                true,
                collectionStack = mutableListOf(aceOfSpades),
                hand = MutableList<Card>(10) {aceOfSpades}
            )
        }
    }
}