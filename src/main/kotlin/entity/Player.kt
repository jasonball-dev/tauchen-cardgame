package entity

/**
 * Entity to represent a player in the game "Tauchen". Besides having a [name],
 * a [score] and a [collectionStack], it also contains the players [hand], as well
 * as the information if the player [hasSpecialAction].
 *
 * @property name The name to be displayed for this player
 */

class Player(
    val name: String,
) {
    var score: Int = 0
    var hasSpecialAction: Boolean = true
    val collectionStack: MutableList<Card> = mutableListOf<Card>()
    val hand: MutableList<Card> = mutableListOf<Card>()
}