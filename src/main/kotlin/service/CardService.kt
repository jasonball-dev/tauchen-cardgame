package service

import entity.Card
import entity.CardSuit
import entity.CardValue

/**
 * Service layer class that provides the logic for dealing cards and creating decks.
 */

class CardService(private val rootService: RootService): AbstractRefreshingService() {
    /**
     * Deals the cards to the players hands at the start of the game.
     *
     * @throws IllegalArgumentException if game and/or player are null
     */
    fun dealCards() {
        val game = rootService.currentGame
        requireNotNull(game)

        val playerOne = game.players.first()
        requireNotNull(playerOne)
        val playerTwo = game.players.last()
        requireNotNull(playerTwo)

        for (i in 1..5)
        playerOne.hand.add(game.drawStack.first())
        game.drawStack.removeFirst()
        playerTwo.hand.add(game.drawStack.first())
        game.drawStack.removeFirst()
    }

    /**
     * Creates the games drawStack.
     */
    fun createDrawStack() : List<Card> {
        val unshuffledCards: List<Card> = List<Card>(52) { index ->
            Card(
                CardSuit.values()[index / 13],
                CardValue.values()[index % 13]
            )
        }

        //val random: Random = Random
        val shuffledCards = unshuffledCards.shuffled()
        return shuffledCards
    }

    /* Is this needed if all decks are initialized as empty?
    fun createDecks() : List<Card> {
        val game = rootService.currentGame
        requireNotNull(game)

        val player = rootService.currentPlayer
        requireNotNull(player)

        createCollectionStack()
        createDrawStack()
        createDiscardStack()
        createPlayStack()
    }

    fun createCollectionStack() : List<Card> {}

    fun createDiscardStack() : List<Card> {}

    fun createPlayStack() : List<Card> {}
    */
}