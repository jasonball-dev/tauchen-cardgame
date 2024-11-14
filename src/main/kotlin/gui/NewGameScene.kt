package gui

import service.RootService
import tools.aqua.bgw.components.layoutviews.Pane
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.UIComponent
import tools.aqua.bgw.components.uicomponents.TextField
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import kotlin.system.exitProcess
import java.awt.Color

/**
 * [MenuScene] that is used for starting a new game. It is displayed directly at program start.
 * There is a [startGameButton] and a [exitButton].
 * When pressing the [startGameButton] the user is able to provide the names of both players and
 * start the game with the [startButton]. This screen is also displayed when "new game"
 * or the "return arrow" is clicked.
 */

class NewGameScene(val rootService: RootService) : MenuScene(1920, 1080), Refreshable {

    // This pane is used to hold all components of the scene and easily center them on the screen
    private val contentPane = Pane<UIComponent>(
        width = 1000,
        height = 650,
        posX = 1920 / 2 - 1000 / 2,
        posY = 1080 / 2 - 650 / 2,
        visual = ColorVisual(Color(0x0C2027))
    )

    private val headlineLabel = Label(
        text = "Tauchen Game",
        width = 1000,
        height = 100,
        posX = 0,
        posY = 50,
        alignment = Alignment.CENTER,
        font = Font(55, Color(0xFFFFFFF), "JetBrains Mono ExtraBold")
    )

    private val enterNamesLabel = Label(
        text = "Enter Names",
        width = 1000,
        height = 100,
        posX = 0,
        posY = 150,
        alignment = Alignment.CENTER,
        font = Font(35, Color(0xFFFFFFF), "JetBrains Mono ExtraBold")
    )

    private val nextPlayerLabel = Label(
        text = "Next Player: Player Name",
        width = 1000,
        height = 100,
        posX = 0,
        posY = 150,
        alignment = Alignment.CENTER,
        font = Font(45, Color(0xFFFFFFF), "JetBrains Mono ExtraBold")
    )

    private val startTurnButton = Button(
        text = "Start Turn",
        width = 450,
        height = 100,
        posX = 275,
        posY = 300,
        font = Font(45, Color(0xFFFFFFF), "JetBrains Mono ExtraBold"),
        visual = ColorVisual(Color(0x49585D))
    ).apply {
        onMouseClicked = {
            val player = rootService.currentPlayer
            if (player != null) {
                rootService.gameService.startTurn(player)
            }
        }
    }

    /* private val returnButton = Button(
        text = "<-",
        width = 150,
        height = 100,
        posX = 30,
        posY = 65,
        font = Font(45, Color(0xFFFFFFF), "JetBrains Mono ExtraBold"),
        visual = ColorVisual(Color(0x49585D))
    ).apply {
        onMouseClicked = {
            isVisible = false
            startGameButton.isVisible = true
            exitButton.isVisible = true
            enterNamesLabel.isVisible = false
            playerOneInput.isVisible = false
            playerTwoInput.isVisible = false
            startButton.isVisible = false
        }
    } */

    private val startGameButton = Button(
        text = "Start Game",
        width = 450,
        height = 100,
        posX = 275,
        posY = 250,
        font = Font(45, Color(0xFFFFFFF), "JetBrains Mono ExtraBold"),
        visual = ColorVisual(Color(0x49585D))
    ).apply {
        onMouseClicked = {
            isVisible = false
            exitButton.isVisible = false
            startButton.isVisible = true
            playerOneInput.isVisible = true
            playerTwoInput.isVisible = true
            enterNamesLabel.isVisible = true
            // returnButton.isVisible = true
        }
    }

    private val exitButton = Button(
        text = "Exit",
        width = 450,
        height = 100,
        posX = 275,
        posY = 370,
        font = Font(45, Color(0xFFFFFFF), "JetBrains Mono ExtraBold"),
        visual = ColorVisual(Color(0x49585D))
    ).apply {
        onMouseClicked = {
            exitProcess(0)
        }
    }

    private val playerOneInput = TextField(
        prompt = "Name",
        width = 450,
        height = 100,
        posX = 275,
        posY = 250,
        font = Font(35, Color(0xFFFFFFF), "JetBrains Mono ExtraBold"),
        visual = ColorVisual(Color(0x49585D)),
    )

    private val playerTwoInput = TextField(
        prompt = "Name",
        width = 450,
        height = 100,
        posX = 275,
        posY = 370,
        font = Font(35, Color(0xFFFFFFF), "JetBrains Mono ExtraBold"),
        visual = ColorVisual(Color(0x49585D)),
    )

    private val startButton = Button(
        text = "Start",
        width = 450,
        height = 100,
        posX = 275,
        posY = 490,
        font = Font(45, Color(0xFFFFFFF), "JetBrains Mono ExtraBold"),
        visual = ColorVisual(Color(0x49585D))
    ).apply {
        onMouseClicked = {
            val playerNames : List<String> = listOf(playerOneInput.text.trim(), playerTwoInput.text.trim())
            rootService.gameService.startGame(playerNames)
            headlineLabel.isVisible = false
            this.isVisible = false
            playerOneInput.isVisible = false
            playerTwoInput.isVisible = false
            enterNamesLabel.isVisible = false
            // returnButton.isVisible = false
            nextPlayerLabel.isVisible = true
            startTurnButton.isVisible = true
        }
    }

    /**
     * Initializes the [nextPlayerLabel].
     */
    override fun refreshAfterStartGame() {
        val player = rootService.currentPlayer
        require(player != null) { "No player found." }
        nextPlayerLabel.text = "Next Player: " + player.name
    }

    /**
     * Updates the [nextPlayerLabel].
     */
    override fun refreshAfterEndTurn() {
        val player = rootService.currentPlayer
        require(player != null) { "No player found." }
        nextPlayerLabel.text = "Next Player: " + player.name
    }

    init {
        background = ColorVisual(Color(12, 32, 39, 240))
        contentPane.addAll(
            headlineLabel,
            startGameButton,
            exitButton,
            startButton,
            playerOneInput,
            playerTwoInput,
            enterNamesLabel,
            // returnButton,
            nextPlayerLabel,
            startTurnButton)
        addComponents(contentPane)
        startButton.isVisible = false
        playerOneInput.isVisible = false
        playerTwoInput.isVisible = false
        enterNamesLabel.isVisible = false
        // returnButton.isVisible = false
        nextPlayerLabel.isVisible = false
        startTurnButton.isVisible = false
    }
}