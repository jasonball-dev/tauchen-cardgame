package entity

/**
 * Entity for the cards used in "Tauchen".
 *
 * It is characterized by a [CardSuit] and a [CardValue].
 */

data class Card(
    val suit: CardSuit,
    val value: CardValue
) {
    override fun toString() = "$suit $value"
}
