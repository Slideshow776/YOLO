package no.sandramoen.yolo.utils

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import no.sandramoen.yolo.screens.gameplay.LevelScreen

class Transition(x: Float = 0f, y: Float = 0f, s: Stage) : BaseActor(x, y, s) {
    private var tag: String = "Transition"
    var duration = .125f

    init {
        loadImage("whitePixel_BIG")
        setSize(BaseGame.WORLD_WIDTH + 2, BaseGame.WORLD_HEIGHT + 2)
        setPosition(-1f, -1f)
        color = Color.BLACK
    }

    fun fadeOut() {
        addAction(Actions.fadeOut(duration))
    }

    fun fadeIn() {
        addAction(Actions.fadeIn(duration))
    }

    fun fadeInToLevelScreen() {
        addAction(Actions.sequence(
            Actions.fadeIn(duration),
            Actions.run {
                BaseGame.setActiveScreen(LevelScreen())
            }
        ))
    }
}
