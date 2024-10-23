package entity

class Player (
    val name: String,
    var score: Int,
    var hasSpecialAction: Boolean,
    val collectionStack: MutableList<Card>,
    val hand: MutableList<Card>,
) {

}