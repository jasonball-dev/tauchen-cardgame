package entity

/**
 * Entity class that contains the two [players], as well as the
 * [drawStack], [playStack] and [discardStack].
 * The current active player is stored via [isPlayerOneActive].
 *
 * @property players array of size 2, which contains the players
 * @property isPlayerOneActive True, if it is the player turn, that is at position 0 in [players]
 *
 * @param drawStack list of cards the player draws from
 * @param playStack list of cards that played cards are placed
 * @param discardStack list of cards the discarded cards are placed
 */

class TauchenGame(
    val players: Array<Player>,
    var isPlayerOneActive: Boolean,
    val drawStack: MutableList<Card>,
    val playStack: MutableList<Card>,
    val discardStack: MutableList<Card>
) {
    init {
        require(players.size == 2) { "player must contain exactly 2 players" }
        require(playStack.size < 3) { "playStack must be less than three cards" }
        require(drawStack.size <= 52 ) { "drawStack must contain <= 52 cards" }
    }
}
