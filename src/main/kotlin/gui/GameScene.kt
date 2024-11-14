package gui

import entity.Card
import service.RootService
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color
import tools.aqua.bgw.components.container.CardStack
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.util.BidirectionalMap
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.animation.DelayAnimation
import tools.aqua.bgw.components.ComponentView
import tools.aqua.bgw.components.container.Area
import tools.aqua.bgw.components.container.LinearLayout
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.gamecomponentviews.TokenView
import tools.aqua.bgw.components.layoutviews.Pane
import tools.aqua.bgw.visual.ImageVisual
import kotlin.math.min

/**
 * The GameScene class is a BoardGameScene that displays the game board and all game components.
 *
 * @property rootService The associated [RootService]
 */

class GameScene(val rootService: RootService) :
    BoardGameScene(1920, 1080, background = ColorVisual(Color(0x8c5760))), Refreshable {

    private val playerName: Label = Label(
        height = 100,
        width = 230,
        posX = 1540,
        posY = 50,
        text = "Player Name",
        font = Font(30, Color(0x000000), "JetBrains Mono ExtraBold")
    )

    private val playerPoints: Label = Label(
        height = 100,
        width = 230,
        posX = 1540,
        posY = 100,
        text = "Points",
        font = Font(30, Color(0x000000), "JetBrains Mono ExtraBold")
    )

    private val drawStackLabel: Label = Label(
        height = 50,
        width = 130,
        posX = 200,
        posY = 160,
        text = "Draw Stack:",
        font = Font(17, Color(0x000000), "JetBrains Mono ExtraBold")
    )

    private val collectionStackLabel: Label = Label(
        height = 50,
        width = 130,
        posX = 200,
        posY = 410,
        text = "Collection Stack:",
        font = Font(17, Color(0x000000), "JetBrains Mono ExtraBold")
    )

    private val discardStackLabel: Label = Label(
        height = 50,
        width = 130,
        posX = 1590,
        posY = 285,
        text = "Discard Stack:",
        font = Font(17, Color(0x000000), "JetBrains Mono ExtraBold")
    )

    val playButton: Button = Button(
        height = 50,
        width = 120,
        posX = 660,
        posY = 680,
        text = "Play",
        font = Font(20, Color(0x000000), "JetBrains Mono ExtraBold"),
        visual = ColorVisual(255, 255, 255, 50)
    )

    val drawButton: Button = Button(
        height = 50,
        width = 120,
        posX = 820,
        posY = 680,
        text = "Draw",
        font = Font(20, Color(0x000000), "JetBrains Mono ExtraBold"),
        visual = ColorVisual(255, 255, 255, 50)
    )

    val swapButton: Button = Button(
        height = 50,
        width = 120,
        posX = 980,
        posY = 680,
        text = "Swap",
        font = Font(20, Color(0x000000), "JetBrains Mono ExtraBold"),
        visual = ColorVisual(255, 255, 255, 50)
    )

    val discardButton: Button = Button(
        height = 50,
        width = 120,
        posX = 1140,
        posY = 680,
        text = "Discard",
        font = Font(20, Color(0x000000), "JetBrains Mono ExtraBold"),
        visual = ColorVisual(255, 255, 255, 50)
    )

    val endTurnButton: Button = Button(
        height = 180,
        width = 180,
        posX = 1565,
        posY = 770,
        text = "End Turn",
        font = Font(30, Color(0x000000), "JetBrains Mono ExtraBold"),
        visual = ColorVisual(255, 255, 255, 50)
    )

    val drawStack: CardStack<CardView> = CardStack(
        height = 200,
        width = 130,
        posX = 200,
        posY = 200,
        visual = ColorVisual(255, 255, 255, 50)
    )

    val collectionStack: CardStack<CardView> = CardStack(
        height = 200,
        width = 130,
        posX = 200,
        posY = 450,
        visual = ColorVisual(255, 255, 255, 50)
    )

    val discardStack: CardStack<CardView> = CardStack(
        height = 200,
        width = 130,
        posX = 1590,
        posY = 325,
        visual = ColorVisual(255, 255, 255, 50)
    )

    var playerOneHand: LinearLayout<CardView> = LinearLayout(
        height = 220,
        width = 800,
        posX = 560,
        posY = 750,
        spacing = -50,
        alignment = Alignment.CENTER,
        visual = ColorVisual(255, 255, 255, 50)
    )

    var playStack: LinearLayout<CardView> = LinearLayout(
        height = 360,
        width = 800,
        posX = 560,
        posY = 225,
        spacing = -50,
        alignment = Alignment.CENTER,
        visual = ColorVisual(255, 255, 255, 50)
    )

    init {
        addComponents(
            drawStack,
            discardStack,
            collectionStack,
            playerOneHand,
            playStack,
            playerPoints,
            playerName,
            playButton,
            drawButton,
            swapButton,
            discardButton,
            drawStackLabel,
            collectionStackLabel,
            discardStackLabel,
            endTurnButton
        )
    }
}