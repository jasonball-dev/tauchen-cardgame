package service

import entity.Player
import entity.TauchenGame
import javax.security.auth.Refreshable

/**
 * Main class of the service layer for the Tauchen card game. Provides access
 * to all other service classes and holds the [currentGame] state for these
 * services to access.
 */

class RootService {
    val gameService = GameService(this)
    val playerActionService = PlayerActionService(this)
    val cardService = CardService(this)

    var currentGame : TauchenGame? = null
    var currentPlayer : Player? = null


    /**
     * Adds the provided [newRefreshable] to all services connected
     * to this root service
     */
    fun addRefreshable(newRefreshable : Refreshable) {
        gameService.addRefreshable(newRefreshable)
        playerActionService.addRefreshable(newRefreshable)
        cardService.addRefreshable(newRefreshable)
    }

    /**
     * Adds each of the provided [newRefreshables] to all services
     * connected to this root service
     */
    fun addRefreshable(vararg newRefreshable : Refreshable) {
        newRefreshable.forEach {addRefreshable(it)}
    }
}