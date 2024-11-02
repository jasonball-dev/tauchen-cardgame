package service

import entity.Card
import view.Refreshable

/**
 * Service layer class that provides the logic for the four possible actions a player
 * can take in Tauchen: playing, drawing, swapping & discarding.
 */

class PlayerActionService(private val rootService: RootService) : AbstractRefreshingService() {
    /**
     * Plays a card from the players hand to the games playStack.
     *
     * @param card the card to play.
     *
     * @throws IllegalArgumentException if game and/or player are null
     * @throws IllegalArgumentException if not enough cards in players hand.
     */
    fun playCard(card: Card) {
        val game = rootService.currentGame
        requireNotNull(game)

        val player = rootService.currentPlayer
        requireNotNull(player)

        require (player.hand.size > 0)
        game.playStack.add(card)
        player.hand.remove(card)

        //onAllRefreshables { Refreshable.refreshAfterPlayCard() }
    }

    /**
     * Draws a card from the games drawStack.
     *
     * @throws IllegalArgumentException if game and/or player are null
     * @throws IllegalArgumentException if not enough cards on drawStack to draw a card.
     */
    fun drawCard() {
        val game = rootService.currentGame
        requireNotNull(game)

        val player = rootService.currentPlayer
        requireNotNull(player)

        require (game.drawStack.size > 0)
        player.hand.add(game.drawStack.first())
        game.drawStack.removeFirst()

        //onAllRefreshables { Refreshable.refreshAfterDrawCard() }
    }

    /**
     * Swaps two cards; A from the players hand with a card from the games playStack.
     *
     * @param card the card from the players hand.
     * @param replacement the cards from the games playStack.
     *
     * @throws IllegalArgumentException if game and/or player are null
     * @throws IllegalArgumentException if too many cards on the games playStack.
     * @throws IllegalArgumentException if not enough cards in the players hand.
     */
    fun swapCard(card: Card, replacement: Card) {
        val game = rootService.currentGame
        requireNotNull(game)

        val player = rootService.currentPlayer
        requireNotNull(player)

        require (player.hand.size > 0)
        require (game.playStack.size < 3)
        game.playStack.add(card)
        player.hand.add(replacement)

        //onAllRefreshables { Refreshable.refreshAfterSwapCard() }
    }

    /**
     * Discards a card from the players hand to the games discardStack.
     *
     * @param card the card to discard.
     *
     * @throws IllegalArgumentException if game and/or player are null
     * @throws IllegalArgumentException if not enough cards in players hand.
     */
    fun discardCard(card: Card) {
        val game = rootService.currentGame
        requireNotNull(game)

        val player = rootService.currentPlayer
        requireNotNull(player)

        require (player.hand.size > 0)
        player.hand.remove(card)
        game.discardStack.add(card)

        //onAllRefreshables { Refreshable.refreshAfterDiscardCard() }
    }
}