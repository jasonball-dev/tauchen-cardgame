package gui

/**
 * This interface provides a mechanism for the service layer classes to communicate
 * (usually to the GUI classes) that certain changes have been made to the entity
 * layer, so that the user interface can be updated accordingly.
 *
 * Default (empty) implementations are provided for all methods, so that implementing
 * GUI classes only need to react to events relevant to them.
 */

interface Refreshable {
    /** Refreshes the GUI after a new game has been started. */
    fun refreshAfterStartGame() {}

    /** Refreshes the GUI after the game has ended. */
    fun refreshAfterEndGame() {}

    /** Refreshes the GUI after a new turn has been started. */
    fun refreshAfterStartTurn() {}

    /** Refreshes the GUI after a turn has ended. */
    fun refreshAfterEndTurn() {}

    /** Refreshes the GUI after a card has been played. */
    fun refreshAfterPlayCard() {}

    /** Refreshes the GUI after a card has been drawn. */
    fun refreshAfterDrawCard() {}

    /** Refreshes the GUI after the last card has been drawn. */
    fun refreshAfterDrawLastCard() {}

    /** Refreshes the GUI after a card has been swapped. */
    fun refreshAfterSwapCard() {}

    /** Refreshes the GUI after a card has been discarded. */
    fun refreshAfterDiscardCard() {}
}