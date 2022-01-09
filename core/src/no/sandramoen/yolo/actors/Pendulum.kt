package no.sandramoen.yolo.no.sandramoen.yolo.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import no.sandramoen.yolo.utils.BaseActor

class Pendulum(x: Float, y: Float, s: Stage, parent: BaseActor) : BaseActor(x, y, s) {
    private val parent = parent

    var movingRight = true

    init {
        loadImage("whitePixel")
        setSize(parent.width * .1f, parent.height)
        setPosition(parent.x, y - height / 2)
        color = Color.WHITE
    }

    override fun act(dt: Float) {
        super.act(dt)

        if (x + width >= parent.x + parent.width)
            movingRight = false
        else if (x <= parent.x)
            movingRight = true
    }
}
