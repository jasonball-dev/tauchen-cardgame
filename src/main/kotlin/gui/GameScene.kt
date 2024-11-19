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

    private val drawStackAmountLabel: Label = Label(
        height = 50,
        width = 130,
        posX = 350,
        posY = 260,
        text = "42 Cards",
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

    val playDrawnCardButton: Button = Button(
        height = 50,
        width = 120,
        posX = 660,
        posY = 680,
        text = "Play",
        font = Font(20, Color(0x000000), "JetBrains Mono ExtraBold"),
        visual = ColorVisual(255, 255, 255, 50)
    ).apply {
        onMouseClicked = {
            val player = rootService.currentPlayer
            checkNotNull(player) { "No current player found." }

            rootService.playerActionService.playCard(player.hand.last())
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
                    if (hasChosenFirst == false) {
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
            val game = rootService.currentGame
            checkNotNull(game) { "No started game found." }

            if (game.drawStack.size == 0) {
                rootService.gameService.endGame()
            } else {
                rootService.currentPlayer?.let { it1 -> rootService.gameService.endTurn(it1) }
            }
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
        val aceOfSpades = Card(CardSuit.SPADES, CardValue.ACE)
        drawStack.clear()
        val cardBack = CardView(
            height = 200,
            width = 130,
            front = cardImageLoader.backImage
        )
        drawStack.add(cardBack)


        collectionStack.clear()
        playStack.clear()
        discardStack.clear()

        /* Test resultScene: Less cards in drawStack.
        for (i in 1 .. 39) {
            game.drawStack.removeFirst()
        } */
    }

    /**
     * Initializes the [playerName] label.
     */
    override fun refreshAfterStartTurn() {
        val game = rootService.currentGame
        checkNotNull(game) { "No started game found." }
        val player = rootService.currentPlayer
        checkNotNull(player) { "No current player found." }

        updatePlayerNameAndScore()
        discardButton.isDisabled = true
        drawButton.isDisabled = false
        playButton.isDisabled = false
        playButton.isVisible = true
        playDrawnCardButton.isVisible = false
        endTurnButton.isDisabled = true
        if (player.hasSpecialAction && game.playStack.size > 0) {
            swapButton.isDisabled = false
            } else {
            swapButton.isDisabled = true
        }
    }

    /**
     * Ends current players turn and shows NextPlayerScreen.
     */
    override fun refreshAfterEndTurn() {
        val game = rootService.currentGame
        checkNotNull(game) { "No started game found." }

        updatePlayerNameAndScore()
        updatePlayerHand()
        updateCollectionStack()
    }

    /**
     * Refreshes [playerHand].
     */
    override fun refreshAfterDrawCard() {
        val game = rootService.currentGame
        checkNotNull(game) { "No started game found." }
        val player = rootService.currentPlayer
        checkNotNull(player) { "No current player found." }

        if (game.drawStack.size == 0) {
            endTurnButton.text = "End Game"
        }

        updatePlayerHand()
        updateDrawStackAmount()
        if (game.drawStack.size == 0) {
            drawStack.clear()
        }
        drawButton.isDisabled = true
        swapButton.isDisabled = true
        endTurnButton.isDisabled = false
        playButton.isVisible = false

        playDrawnCardButton.isVisible = true

        if (player.hand.size > 8) {
            endTurnButton.isDisabled = true
            discardButton.isDisabled = false
            playDrawnCardButton.isVisible = true
        }
    }

    /**
     * Refreshes [playerHand] and [drawStack].
     */
    override fun refreshAfterDiscardCard() {
        updatePlayerHand()
        updateDiscardStack()
        discardButton.isDisabled = true
        endTurnButton.isDisabled = false
        playButton.isDisabled = true
    }

    /**
     * Refreshes [playerHand], [playStack] the players points and [collectionStack].
     */
    override fun refreshAfterPlayCard() {
        updatePlayerHand()
        updatePlayStack()
        updatePlayerNameAndScore()
        updateCollectionStack()
        playButton.isDisabled = true
        playButton.isVisible = true
        playDrawnCardButton.isVisible = false
        endTurnButton.isDisabled = false
        swapButton.isDisabled = true
        drawButton.isDisabled = true
        discardButton.isDisabled = true
    }

    /**
     * Refreshes [playerHand] and [playStack].
     */
    override fun refreshAfterSwapCard(replacement: Card) {
        updatePlayerHandAfterSwapCard(replacement)
        updatePlayStack()
        swapButton.isDisabled = true
        drawButton.isDisabled = true
        playButton.isDisabled = true
        endTurnButton.isDisabled = false
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
     * Update [drawStackAmountLabel].
     */
    private fun updateDrawStackAmount() {
        val game = rootService.currentGame
        require(game != null) { "No game found." }
        drawStackAmountLabel.text = game.drawStack.size.toString() + " Cards"
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
            endTurnButton,
            playDrawnCardButton,
            drawStackAmountLabel
        )
    }
}