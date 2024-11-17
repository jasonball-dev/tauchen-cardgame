package gui

import service.RootService
import tools.aqua.bgw.core.BoardGameApplication

/**
 * Implementation of the BGW [BoardGameApplication] for the game "Tauchen".
 */

class TauchenApplication : BoardGameApplication("Tauchen Game"), Refreshable {
    private val rootService = RootService()

    private val gameScene = GameScene(rootService)
    private val newGameScene = NewGameScene(rootService)

    init {
        rootService.addRefreshables(
            this,
            gameScene,
            newGameScene
            //resultScene
        )

        this.showGameScene(gameScene)
        this.showMenuScene(newGameScene, 0)
    }

    override fun refreshAfterStartTurn() {
        hideMenuScene(500)
    }

    override fun refreshAfterEndTurn() {
        showMenuScene(newGameScene, 500)
    }

    override fun refreshAfterEndGame() {
        // showMenuScene(resultScene)
    }
}

