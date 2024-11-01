package entity

/**
 * Entity class that contains the two [players], as well as the
 * [drawStack], [playStack] and [discardStack].
 * The current active player is stored via [isPlayerOneActive].
 *
 * @property players array of size 2, which contains the players
 * @property isPlayerOneActive True, if it is the player turn, that is at position 0 in [players]
 */

class TauchenGame(
    val players: Array<Player>,
    var isPlayerOneActive: Boolean = true,
) {
    val drawStack: MutableList<Card> = mutableListOf<Card>()
    val playStack: MutableList<Card> = mutableListOf<Card>()
    val discardStack: MutableList<Card> = mutableListOf<Card>()

    init {
        require(players.size == 2) { "player must contain exactly 2 players" }
    }
}
