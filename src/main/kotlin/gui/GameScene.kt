package gui

import entity.*
import service.*
import service.CardImageLoader
import service.GameService
import service.RootService
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color
import tools.aqua.bgw.components.container.CardStack
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.components.container.LinearLayout
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.util.BidirectionalMap
import tools.aqua.bgw.visual.ImageVisual

import tools.aqua.bgw.animation.DelayAnimation
import tools.aqua.bgw.components.ComponentView
import tools.aqua.bgw.components.container.Area
import tools.aqua.bgw.components.gamecomponentviews.TokenView
import tools.aqua.bgw.components.layoutviews.Pane
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
    ).apply {
        onMouseClicked = {
            var hasPlayed: Boolean = false
            playerHand.components.forEach { cardView ->
                cardView.onMouseClicked = {
                    if (hasPlayed == false) {
                        rootService.playerActionService.playCard(cardMap.backward(cardView))
                        hasPlayed = true
                    }
                }
            }
        }
    }

    val drawButton: Button = Button(
        height = 50,
        width = 120,
        posX = 820,
        posY = 680,
        text = "Draw",
        font = Font(20, Color(0x000000), "JetBrains Mono ExtraBold"),
        visual = ColorVisual(255, 255, 255, 50)
    ).apply {
        onMouseClicked = {
            rootService.playerActionService.drawCard()
        }
    }

    val swapButton: Button = Button(
        height = 50,
        width = 120,
        posX = 980,
        posY = 680,
        text = "Swap",
        font = Font(20, Color(0x000000), "JetBrains Mono ExtraBold"),
        visual = ColorVisual(255, 255, 255, 50)
    ).apply {
        onMouseClicked = {
            var hasChosenFirst: Boolean = false
            var hasSwapped: Boolean = false

            playerHand.components.forEach { cardView ->
                cardView.onMouseClicked = {
                    if (hasChosenFirst == false && PlayerActionService(rootService).fitsTrio(cardMap.backward(cardView))) {
                        val firstToSwap = cardMap.backward(cardView)

                        playStack.components.forEach { cardView ->
                            cardView.onMouseClicked = {
                                if (hasSwapped == false) {
                                    rootService.playerActionService.swapCard(firstToSwap, cardMap.backward(cardView))
                                }
                                hasSwapped = true
                            }
                        }
                        hasChosenFirst = true
                    }
                }
            }
        }
    }

    val discardButton: Button = Button(
        height = 50,
        width = 120,
        posX = 1140,
        posY = 680,
        text = "Discard",
        font = Font(20, Color(0x000000), "JetBrains Mono ExtraBold"),
        visual = ColorVisual(255, 255, 255, 50)
    ).apply {
        onMouseClicked = {
            var hasDiscarded: Boolean = false
            playerHand.components.forEach { cardView ->
                cardView.onMouseClicked = {
                    if (hasDiscarded == false) {
                        rootService.playerActionService.discardCard(cardMap.backward(cardView))
                        hasDiscarded = true
                    }
                }
            }
        }
    }

    val endTurnButton: Button = Button(
        height = 180,
        width = 180,
        posX = 1565,
        posY = 770,
        text = "End Turn",
        font = Font(30, Color(0x000000), "JetBrains Mono ExtraBold"),
        visual = ColorVisual(255, 255, 255, 50)
    ).apply {
        onMouseClicked = {
            rootService.currentPlayer?.let { it1 -> rootService.gameService.endTurn(it1) }
        }
    }

    val drawStack: CardStack<CardView> = CardStack(
        height = 200,
        width = 130,
        posX = 200,
        posY = 200,
        visual = ColorVisual(255, 255, 255, 50),
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

    var playerHand: LinearLayout<CardView> = LinearLayout(
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

    private val cardMap: BidirectionalMap<Card, CardView> = BidirectionalMap()

    /**
     * Initializes the complete GUI, i.e. the stack views.
     */
    override fun refreshAfterStartGame() {
        val game = rootService.currentGame
        checkNotNull(game) { "No started game found." }
        val player = rootService.currentPlayer
        checkNotNull(player) { "No current player found." }

        cardMap.clear()

        val cardImageLoader = CardImageLoader()

        CardValue.values().forEach { value ->
            CardSuit.values().forEach { suit ->
                cardMap[Card(suit, value)] = CardView(
                    posX = 0,
                    posY = 0,
                    width = 130,
                    height = 200,
                    front = cardImageLoader.frontImageFor(suit, value),
                    back = cardImageLoader.backImage
                )
            }
        }

        playerHand.clear()
        player.hand.forEach { card ->
            playerHand.add(
                (cardMap[card] as CardView).apply {
                    this.showFront()
                }
            )
        }

        //Add cardback to discard stack

        collectionStack.clear()
        playStack.clear()
        discardStack.clear()
    }

    /**
     * Initializes the [playerName] label.
     */
    override fun refreshAfterStartTurn() {
        updatePlayerNameAndScore()
    }

    /**
     * Ends current players turn and shows NextPlayerScreen.
     */
    override fun refreshAfterEndTurn() {
        updatePlayerNameAndScore()
        updatePlayerHand()
        updateCollectionStack()
    }

    /**
     * Refreshes [playerHand].
     */
    override fun refreshAfterDrawCard() {
        updatePlayerHand()
    }

    /**
     * Refreshes [playerHand] and [drawStack].
     */
    override fun refreshAfterDiscardCard() {
        updatePlayerHand()
        updateDiscardStack()
    }

    /**
     * Refreshes [playerHand], [playStack] the players points and [collectionStack].
     */
    override fun refreshAfterPlayCard() {
        updatePlayerHand()
        updatePlayStack()
        updatePlayerNameAndScore()
        updateCollectionStack()
    }

    /**
     * Refreshes [playerHand] and [playStack].
     */
    override fun refreshAfterSwapCard(replacement: Card) {
        updatePlayerHandAfterSwapCard(replacement)
        updatePlayStack()
    }

    /**
     * Update [playerName] so that the current player attribute is shown.
     */
    private fun updatePlayerNameAndScore() {
        val player = rootService.currentPlayer
        require(player != null) { "No player found." }
        playerName.text = "Player: " + player.name
        playerPoints.text = "Points: " + player.score.toString()
    }

    /**
     * Update [playerHand].
     */
    private fun updatePlayerHand() {
        val player = rootService.currentPlayer
        checkNotNull(player) { "No current player found." }

        playerHand.clear()
        player.hand.forEach { card ->
            playerHand.add(
                (cardMap[card] as CardView).apply {
                    this.showFront()
                }
            )
        }
    }

    /**
     * Update [playerHand] after a card has been swapped.
     */
    private fun updatePlayerHandAfterSwapCard(replacement: Card) {
        val player = rootService.currentPlayer
        checkNotNull(player) { "No current player found." }

        playStack.remove(cardMap[replacement] as CardView)

        playerHand.clear()
        player.hand.forEach { card ->
            playerHand.add(
                (cardMap[card] as CardView).apply {
                    this.showFront()
                }
            )
        }
    }

    /**
     * Update [playStack].
     */
    private fun updatePlayStack() {
        val game = rootService.currentGame
        checkNotNull(game) { "No started game found." }
        val gamePlayStack = game.playStack

        playStack.clear()
        gamePlayStack.forEach { card ->
            playStack.add(
                (cardMap[card] as CardView).apply {
                    this.showFront()
                }
            )
        }
    }

    /**
     * Update [discardStack].
     */
    private fun updateDiscardStack() {
        val game = rootService.currentGame
        checkNotNull(game) { "No started game found." }
        val gameDiscardStack = game.discardStack

        discardStack.clear()
        discardStack.add(cardMap[gameDiscardStack.last()] as CardView)
        discardStack.last().showFront()
    }

    /**
     * Update [collectionStack].
     */
    private fun updateCollectionStack() {
        val player = rootService.currentPlayer
        checkNotNull(player) { "No current player found." }
        val playerCollectionStack = player.collectionStack

        collectionStack.clear()
        if (playerCollectionStack.size != 0) {
            collectionStack.add(cardMap[playerCollectionStack.last()] as CardView)
            collectionStack.last().showFront()
        }
    }

    init {
        addComponents(
            drawStack,
            discardStack,
            collectionStack,
            playerHand,
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