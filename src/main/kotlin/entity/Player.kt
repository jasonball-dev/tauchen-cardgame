package entity

/**
 * Entity to represent a player in the game "Tauchen". Besides having a [name],
 * a [score] and a [collectionStack], it also contains the players [hand], as well
 * as the information if the player [hasSpecialAction].
 *
 * @property name The name to be displayed for this player
 * @property score The players achieved score
 * @property hasSpecialAction True if player has his special action available
 *
 * @param collectionStack list of cards that have been collected after completing trios
 * @param hand list of the players cards in hand
 */

class Player (
    val name: String,
    var score: Int,
    var hasSpecialAction: Boolean,
    val collectionStack: MutableList<Card>,
    val hand: MutableList<Card>
) {
    init {
        require(hand.size <= 9) { "Hand must be equal or less than 9" }
    }
}