package no.sandramoen.yolo.no.sandramoen.yolo.actors

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import no.sandramoen.yolo.utils.BaseActor

class LifeImages(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    private var tag = "LifeImages"

    init {
        loadImage("character/0")
        setSize(50f, 50f)
        setPosition(x - width / 2, y - height / 2)
    }

    fun setImage(age: Int) {
        val duration = .25f
        addAction(
            Actions.sequence(
                Actions.fadeOut(duration),
                Actions.run {
                    when (age) {
                        0 -> loadImage("character/0")
                        in 10..15 -> loadImage("character/10-15")
                        in 15..20 -> loadImage("character/15-20")
                        in 20..30 -> loadImage("character/20-30")
                        in 30..50 -> loadImage("character/30-50")
                        in 50..70 -> loadImage("character/50-70")
                        in 70..100 -> loadImage("character/70-100")
                        101 -> loadImage("character/dead")
                    }
                    setSize(50f, 50f)
                },
                Actions.fadeIn(duration)
            )
        )
    }
}
