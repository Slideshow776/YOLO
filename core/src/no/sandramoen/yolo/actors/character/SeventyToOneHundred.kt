package no.sandramoen.yolo.no.sandramoen.yolo.actors.character

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Array
import no.sandramoen.yolo.utils.BaseActor
import no.sandramoen.yolo.utils.BaseGame

class SeventyToOneHundred(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    private var tag = "SeventyToOneHundred"
    private var leftArm: BaseActor
    private var head: BaseActor
    private var body: BaseActor

    init {
        setSize(50f, 50f)
        setPosition(x - width / 2, y - height / 2)

        var animationImages: Array<TextureAtlas.AtlasRegion> = Array()
        for (i in 0 until 15)
            animationImages.add(BaseGame.textureAtlas!!.findRegion("character/70-100/head"))
        animationImages.add(BaseGame.textureAtlas!!.findRegion("character/70-100/head-blink"))
        head = BaseActor(0f, 0f, s)
        head.setAnimation(Animation(.5f, animationImages, Animation.PlayMode.LOOP))
        head.setSize(31.5f, 18.2f)
        head.setPosition(x - 15.1f, y - 1.0f)
        head.setOrigin(Align.center)
        head.addAction(Actions.forever(Actions.sequence(
            Actions.rotateBy(1.5f, 2.5f),
            Actions.rotateBy(-3f, 5f),
            Actions.rotateBy(1.5f, 2.5f)
        )))

        body = BaseActor(0f, 0f, s)
        body.loadImage("character/70-100/body")
        body.setSize(33f, 25.6f)
        body.setPosition(x - 21.0f, y - 25f)

        leftArm = BaseActor(0f, 0f, s)
        leftArm.loadImage("character/70-100/leftArm")
        leftArm.setSize(10f, 12f)
        leftArm.setPosition(x + 8.5f, y - 11.5f)
        leftArm.setOrigin(Align.center)
        leftArm.addAction(
            Actions.forever(
                Actions.sequence(
                    Actions.parallel(
                        Actions.rotateBy(-12.5f, 5f),
                        Actions.moveBy(-1.0f, -.6f, 5f)
                    ),
                    Actions.parallel(
                        Actions.rotateBy(12.5f, 5f),
                        Actions.moveBy(1.0f, .6f, 5f)
                    )
                )
            )
        )
    }

    fun makeTransparent() {
        leftArm.color.a = 0f
        head.color.a = 0f
        body.color.a = 0f
    }

    fun fadeIn(duration: Float = .125f) {
        leftArm.addAction(Actions.fadeIn(duration))
        head.addAction(Actions.fadeIn(duration))
        body.addAction(Actions.fadeIn(duration))
    }

    fun fadeOut(duration: Float = .125f) {
        leftArm.addAction(Actions.fadeOut(duration))
        head.addAction(Actions.fadeOut(duration))
        body.addAction(Actions.fadeOut(duration))
    }
}
