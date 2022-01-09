package no.sandramoen.yolo.no.sandramoen.yolo.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import no.sandramoen.yolo.utils.BaseActor

class Metronome(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    private val tag = "Metronome"
    private val pendulum: Pendulum
    private val pendulumSpeed = .5f

    init {
        loadImage("whitePixel")
        setSize(80f, 10f)
        setPosition(x - width / 2, y - height / 2)
        color = Color.CORAL

        pendulum = Pendulum(x, y, s, this)
    }

    override fun act(dt: Float) {
        super.act(dt)

        // println(pendulum.x)
        if (pendulum.movingRight && pendulum.x < (x + width) - pendulum.width)
            pendulum.x += pendulumSpeed
        else if (pendulum.x > x)
            pendulum.x -= pendulumSpeed
    }
}
