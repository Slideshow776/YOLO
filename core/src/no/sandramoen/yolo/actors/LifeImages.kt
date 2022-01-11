package no.sandramoen.yolo.no.sandramoen.yolo.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import no.sandramoen.yolo.utils.BaseActor

class LifeImages(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadImage("baby")
        setSize(50f, 50f)
        setPosition(x - width / 2, y - height / 2)
    }

    fun setImage(phase: Int) {
        when (phase) {
            1 -> loadImage("baby")
            2 -> loadImage("school")
            3 -> loadImage("student")
            4 -> loadImage("adult")
            5 -> loadImage("old")
            6 -> loadImage("dead")
        }
        setSize(50f, 50f)
    }
}
