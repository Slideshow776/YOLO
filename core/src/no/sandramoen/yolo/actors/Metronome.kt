package no.sandramoen.yolo.no.sandramoen.yolo.actors

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.MathUtils.ceil
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import no.sandramoen.yolo.utils.BaseActor
import no.sandramoen.yolo.utils.GameUtils.Companion.normalizeValues
import kotlin.math.floor

class Metronome(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    private val tag = "Metronome"
    private val pendulum: Pendulum

    var stop = false

    init {
        loadImage("gradient")
        setSize(80f, 10f)
        setPosition(x - width / 2, y - height / 2)

        pendulum = Pendulum(x, y, s, this)
        pendulum.maxLeftPosition = this.x
        pendulum.maxRightPosition = this.x + width - pendulum.width
        pendulum.middlePosition = this.x + width / 2 - pendulum.width / 2
        pendulum.setPosition(this.x, y - pendulum.height / 2) // left
        pendulum.speed = .01f * width
        pendulum.originalSpeed = pendulum.speed
    }

    override fun act(dt: Float) {
        super.act(dt)
        if (stop) return

        // determine which direction the pendulum is moving
        if (floor(pendulum.x) <= x)
            pendulum.movingRight = true
        else if (ceil(pendulum.x) >= pendulum.maxRightPosition)
            pendulum.movingRight = false

        // move the pendulum
        if (pendulum.movingRight && pendulum.x <= pendulum.maxRightPosition * 2/3) {
            pendulum.x += pendulum.speed * (pendulum.x + pendulum.maxRightPosition * 2/3) * .04f
        } else if (pendulum.movingRight && pendulum.x < pendulum.maxRightPosition) {
            pendulum.x += pendulum.speed * (pendulum.maxRightPosition - pendulum.x) * .1f
        }
        else if (!pendulum.movingRight && pendulum.x >= pendulum.maxRightPosition * 2/3)
            pendulum.x -= pendulum.speed * (pendulum.x + pendulum.maxRightPosition * 2/3) * .04f
        else if (!pendulum.movingRight && pendulum.x > pendulum.maxLeftPosition)
            pendulum.x -= pendulum.speed * (pendulum.x - pendulum.maxLeftPosition) * .1f
    }

    /**
     * @return a normalized score that is indicative about how close to the center the pendulum is, where 1 is the closest.
     */
    fun getScore(): Float {
        var score = 0f
        var isPendulumOnLeftSide = pendulum.x <= pendulum.middlePosition
        if (isPendulumOnLeftSide)
            score = normalizeValues(pendulum.x, pendulum.maxLeftPosition, pendulum.middlePosition)
        else
            score = 1 - normalizeValues(pendulum.x, pendulum.middlePosition, pendulum.maxRightPosition)
        if (score < 0) score = 0f
        return score
    }

    fun restart() {
        val duration = MathUtils.random(0f, .2f)
        addAction(Actions.sequence(
            Actions.delay(duration),
            Actions.run { stop = false }
        ))
        if (MathUtils.randomBoolean())
            pendulum.setPosition(pendulum.maxLeftPosition + 1, (y + height / 2) - pendulum.height / 2)
        else
            pendulum.setPosition(pendulum.maxRightPosition - 1, (y + height / 2) - pendulum.height / 2)
    }

    fun adjustSpeed(modifier: Float) {
        pendulum.speed = pendulum.originalSpeed * modifier - .5f
    }
}
