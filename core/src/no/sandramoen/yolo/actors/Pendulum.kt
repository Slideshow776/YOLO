package no.sandramoen.yolo.no.sandramoen.yolo.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import no.sandramoen.yolo.utils.BaseActor

class Pendulum(x: Float, y: Float, s: Stage, parent: BaseActor) : BaseActor(x, y, s) {
    private val tag = "Pendulum"

    var movingRight = true
    var maxLeftPosition = 0f
    var middlePosition = 0f
    var maxRightPosition = 0f
    var speed = 0f
    var originalSpeed = 0f

    init {
        loadImage("whitePixel")
        setSize(parent.width * .1f, parent.height)
        color = Color.WHITE
    }
}
