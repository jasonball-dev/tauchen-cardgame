package entity

class TauchenGame(
    val players: Array<Player>,
    var isPlayerOneActive: Boolean,
    val drawStack: MutableList<Card>,
    val playStack: MutableList<Card>,
    val discardStack: MutableList<Card>,
) {

}
