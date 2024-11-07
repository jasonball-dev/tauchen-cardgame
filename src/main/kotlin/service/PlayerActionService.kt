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
     * Only works if it fits the trio.
     * The players score is increased if a trio is completed and the playStack gets cleared.
     * Both players regain their specialAction if a trio is completed.
     *
     * @param card the card to play.
     *
     * @throws IllegalArgumentException if game and/or player are null
     * @throws IllegalArgumentException if not enough cards in players hand.
     */
    fun playCard(card: Card) {
        val game = rootService.currentGame
        requireNotNull(game) { "Game is null." }

        val player = rootService.currentPlayer
        requireNotNull(player) { "Player is null." }

        require(player.hand.size > 0) { "PlayerHand is empty. PlayCard not possible." }
        if (fitsTrio(card) && game.playStack.size == 2) {
            game.playStack.clear()
            game.players[0].hasSpecialAction = true
            game.players[1].hasSpecialAction = true
        } else if (fitsTrio(card)) {
            game.playStack.add(card)
            player.hand.remove(card)
        } else {
            throw IllegalArgumentException("Card doesnt fit stack.")
        }

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
        requireNotNull(game) { "Game is null." }

        val player = rootService.currentPlayer
        requireNotNull(player) { "Player is null." }

        require(game.drawStack.size > 0) { "DrawStack is empty." }
        player.hand.add(game.drawStack.first())
        game.drawStack.removeFirst()

        if (game.drawStack.size == 0) {
            if (fitsTrio(player.hand.last())) {
                    playCard(player.hand.last())
            }
            GameService(rootService).endGame()
            //onAllRefreshables { Refreshable.refreshAfterDrawLastCard() }
        } else {
            //onAllRefreshables { Refreshable.refreshAfterDrawCard() }
        }
    }

    /**
     * Swaps two cards; A from the players hand with a card from the games playStack.
     * If a player uses swapCard() he looses his specialAction.
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
        requireNotNull(game) { "Game is null." }

        val player = rootService.currentPlayer
        requireNotNull(player) { "Player is null." }

        require(player.hand.size > 0) { "Players hand is empty. SwapCard not possible." }
        require(game.playStack.size < 3) { "PlayStack is >2. SwapCard not possible." }

        if (game.playStack.size == 2) {
            val testPlayStack: MutableList<Card> = mutableListOf()
            testPlayStack.add(game.playStack.first())
            testPlayStack.add(game.playStack.last())

            if (card.value != testPlayStack[0].value && card.suit != testPlayStack[0].suit) {
                throw IllegalArgumentException("Card to swap doesnt fit.")
            }
        }

        game.playStack.add(card)
        game.playStack.remove(replacement)
        player.hand.add(replacement)
        player.hand.remove(card)

        player.hasSpecialAction = false

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
        requireNotNull(game) { "Game is null." }

        val player = rootService.currentPlayer
        requireNotNull(player) { "Player is null." }

        require(player.hand.size > 0) { "PlayerHand is empty. DiscardCard not possible." }
        player.hand.remove(card)
        game.discardStack.add(card)

        //onAllRefreshables { Refreshable.refreshAfterDiscardCard() }
    }

    /**
     * Private function that checks if a card fits a trio.
     * If a trio is completed, the player gets awarded points.
     */
    private fun fitsTrio(card: Card): Boolean {
        val game = rootService.currentGame
        requireNotNull(game) { "Game is null." }

        val player = rootService.currentPlayer
        requireNotNull(player) { "Player is null." }

        if (game.playStack.isEmpty()) {
            return true
        } else if (game.playStack.size == 1) {
            if (card.value == game.playStack[0].value || card.suit == game.playStack[0].suit) return true;
        } else if (game.playStack.size == 2) {
            if (card.value == game.playStack[0].value && card.value == game.playStack[1].value) {
                player.score += 20
                return true;
            }
            if (card.suit == game.playStack[0].suit && card.suit == game.playStack[1].suit) {
                player.score += 5
                return true
            }
        }
        return false
    }
}
