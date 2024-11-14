package gui

import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual

/**
 * [MenuScene] that is displayed when the game is finished. It shows the final result of the game
 * as well as the scores of both players. Also, there are two buttons: one for starting a new game and one for
 * quitting the program.
 */

class ResultScene : BoardGameScene(500, 500) {

    private val helloLabel = Label(
        width = 500,
        height = 500,
        posX = 0,
        posY = 0,
        text = "Hello, SoPra!",
        font = Font(size = 20)
    )

    init {
        background = ColorVisual(108, 168, 59)
        addComponents(helloLabel)
    }
}