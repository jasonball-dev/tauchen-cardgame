package service

import entity.Card
import org.junit.jupiter.api.Test
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

/**
 * TestClass for CardService.
 */

class CardServiceTest {
    val playerNames : List<String> = listOf("testPlayerOne", "testPlayerTwo")
    val rootService = RootService()
    
    @BeforeTest
    fun beforeTest() {
        GameService(rootService).startGame(playerNames)
    }

    /**
     * Tests if the games drawStack has the correct starting size.
     */
    @Test
    fun drawStackSizeCorrect() {
        assertEquals(CardService(rootService).createDrawStack().size, 52)
    }

    /**
     * Tests if both players get dealt exactly 5 cards to hand.
     */
    @Test
    fun dealtRightAmountOfHandCards() {
        rootService.currentGame?.drawStack = CardService(rootService).createDrawStack()

        assertEquals(rootService.currentGame?.players?.get(0)?.hand?.size, 5)
        assertEquals(rootService.currentGame?.players?.get(1)?.hand?.size, 5)
        assertEquals(rootService.currentGame?.drawStack?.size, 52)

        CardService(rootService).dealCards(rootService)

        assertEquals(rootService.currentGame?.players?.get(0)?.hand?.size, 10)
        assertEquals(rootService.currentGame?.players?.get(1)?.hand?.size, 10)
        assertEquals(rootService.currentGame?.drawStack?.size, 42)

    }
}