package service

import entity.Card
import entity.CardSuit
import entity.CardValue

/**
 * Service layer class that provides the logic for dealing cards and creating decks.
 */

class CardService(private val rootService: RootService) : AbstractRefreshingService() {
    /**
     * Returns the games drawStack and shuffles it.
     *
     * @throws IllegalArgumentException if game is null.
     */
    fun createDrawStack(): MutableList<Card> {
        val cards: MutableList<Card> = mutableListOf()
        for (suit in CardSuit.values()) {
            for (value in CardValue.values()) {
                cards.add(Card(suit, value))
            }
        }
        cards.shuffled()
        return cards
    }

    /**
     * Deals the cards to the players hands at the start of the game.
     *
     * @throws IllegalArgumentException if game is null.
     */
    fun dealCards() {
        for (numberOfCards in 1..5) {
            rootService.currentGame?.players?.first()?.hand?.add(rootService.currentGame!!.drawStack.first())
            rootService.currentGame?.drawStack?.removeFirst()
            rootService.currentGame?.players?.last()?.hand?.add(rootService.currentGame!!.drawStack.first())
            rootService.currentGame?.drawStack?.removeFirst()
        }
    }
}