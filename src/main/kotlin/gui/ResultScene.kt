package gui

import entity.Player
import tools.aqua.bgw.components.layoutviews.Pane
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextField
import tools.aqua.bgw.components.uicomponents.UIComponent
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color
import kotlin.system.exitProcess
import service.RootService
import tools.aqua.bgw.core.MenuScene
import kotlin.system.exitProcess

/**
 * [MenuScene] that is displayed when the game is finished. It shows the final result of the game
 * as well as the scores of both players. Also, there are two buttons: one for starting a new game and one for
 * quitting the program.
 */

class ResultScene(val rootService: RootService) : MenuScene(1920, 1080), Refreshable {
    // This pane is used to hold all components of the scene and easily center them on the screen
    private val contentPane = Pane<UIComponent>(
        width = 1000,
        height = 650,
        posX = 1920 / 2 - 1000 / 2,
        posY = 1080 / 2 - 650 / 2,
        visual = ColorVisual(Color(255, 255, 255, 50))
    )

    private val rankingPane = Pane<UIComponent>(
        width = 500,
        height = 250,
        posX = 1920 / 2 - 500 / 2,
        posY = 1080 / 2 - 250 / 2,
        visual = ColorVisual(Color(0x8c5760))
    )

    private val gameOverLabel = Label(
        text = "Game Over",
        width = 1000,
        height = 100,
        posX = 0,
        posY = 100,
        alignment = Alignment.CENTER,
        font = Font(40, Color(0xFFFFFFF), "JetBrains Mono ExtraBold")
    )

    private val winnerLabel = Label(
        text = "Winner: Player Name!",
        width = 500,
        height = 100,
        posX = 0,
        posY = 25,
        alignment = Alignment.CENTER,
        font = Font(30, Color(0xFFFFFFF), "JetBrains Mono ExtraBold")
    )

    private val scoresLabel = Label(
        text = "Scores:",
        width = 500,
        height = 100,
        posX = 0,
        posY = 85,
        alignment = Alignment.CENTER,
        font = Font(30, Color(0xFFFFFFF), "JetBrains Mono ExtraBold")
    )

    private val playerOneScoreLabel = Label(
        text = "P1: Points",
        width = 500,
        height = 100,
        posX = 0,
        posY = 125,
        alignment = Alignment.CENTER,
        font = Font(30, Color(0xFFFFFFF), "JetBrains Mono ExtraBold")
    )

    private val playerTwoScoreLabel = Label(
        text = "P2: Points",
        width = 500,
        height = 100,
        posX = 0,
        posY = 160,
        alignment = Alignment.CENTER,
        font = Font(30, Color(0xFFFFFFF), "JetBrains Mono ExtraBold")
    )

    private val playAgainButton = Button(
        text = "Play Again",
        width = 300,
        height = 60,
        posX = 350,
        posY = 475,
        font = Font(30, Color(0xFFFFFFF), "JetBrains Mono ExtraBold"),
        visual = ColorVisual(Color(0x49585D))
    ).apply {
        onMouseClicked = {
            refreshAfterRestartGame()
        }
    }

    private val exitButton = Button(
        text = "Exit",
        width = 300,
        height = 60,
        posX = 350,
        posY = 550,
        font = Font(30, Color(0xFFFFFFF), "JetBrains Mono ExtraBold"),
        visual = ColorVisual(Color(0x49585D))
    ).apply {
        onMouseClicked = {
            exitProcess(0)
        }
    }

    /**
     * Updates [winnerLabel], [playerOneScoreLabel] and [playerTwoScoreLabel].
     */
    override fun refreshAfterEndGame() {
        val game = rootService.currentGame
        require(game != null) { "No game found." }
        val playerOne = game.players[0]
        val playerTwo = game.players[1]
        val winner: Player
        val looser: Player

        if (playerOne.score > playerTwo.score) {
            winner = playerOne
            looser = playerTwo
        } else {
            winner = playerTwo
            looser = playerOne
        }

        winnerLabel.text = "Winner: " + winner.name + "!"
        playerOneScoreLabel.text = winner.name + ": " + winner.score
        playerTwoScoreLabel.text = looser.name + ": " + looser.score

        rootService.currentGame = null
        rootService.currentPlayer = null
    }

    init {
        background = ColorVisual(Color(0x8c5760))
        contentPane.addAll(
            gameOverLabel,
            //playAgainButton,
            exitButton)
        addComponents(contentPane)

        rankingPane.addAll(
            winnerLabel,
            scoresLabel,
            playerOneScoreLabel,
            playerTwoScoreLabel
        )
        addComponents((rankingPane))
    }
}