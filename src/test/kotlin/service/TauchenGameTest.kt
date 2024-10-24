package service

import entity.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertFails

class TauchenGameTest {
    val aceOfSpades = Card(CardSuit.SPADES, CardValue.ACE)
    val testPlayer = Player(
        "PlayerOne",
        23,
        true,
        collectionStack = mutableListOf(aceOfSpades),
        hand = MutableList<Card>(2) { aceOfSpades }
    )

    /**
     * Test if players must be exactly == 2
     */
    @Test
    fun testTwoPlayers() {
        assertFails {
            val testGame = TauchenGame(
                players = arrayOf(testPlayer, testPlayer, testPlayer),
                true,
                drawStack = mutableListOf(aceOfSpades),
                playStack = mutableListOf(aceOfSpades),
                discardStack = mutableListOf(aceOfSpades)
            )
        }
        assertFails {
            val testGame = TauchenGame(
                players = arrayOf(testPlayer),
                true,
                drawStack = mutableListOf(aceOfSpades),
                playStack = mutableListOf(aceOfSpades),
                discardStack = mutableListOf(aceOfSpades)
            )
        }
    }

    /**
     * Test if playStack must be < 3
     */
    @Test
    fun playStackSize() {
        assertFails {
            val testGame = TauchenGame(
                players = arrayOf(testPlayer, testPlayer),
                true,
                drawStack = mutableListOf(aceOfSpades),
                playStack = MutableList<Card>(3) {aceOfSpades},
                discardStack = mutableListOf(aceOfSpades)
            )
        }
    }

    /**
     * Test if drawStack must be <= 52
     */
    @Test
    fun drawStackSize() {
        assertFails {
            val testGame = TauchenGame(
                players = arrayOf(testPlayer, testPlayer),
                true,
                drawStack = MutableList<Card>(53) {aceOfSpades},
                playStack = mutableListOf(aceOfSpades),
                discardStack = mutableListOf(aceOfSpades)
            )
        }
    }
}