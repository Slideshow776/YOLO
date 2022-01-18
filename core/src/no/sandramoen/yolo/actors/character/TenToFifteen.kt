package no.sandramoen.yolo.no.sandramoen.yolo.actors.character

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Array
import no.sandramoen.yolo.utils.BaseActor
import no.sandramoen.yolo.utils.BaseGame

class TenToFifteen(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    private var tag = "TenToFifteen"
    private var leftArm: BaseActor
    private var rightArm: BaseActor
    private var head: BaseActor
    private var body: BaseActor

    init {
        setSize(50f, 50f)
        setPosition(x - width / 2, y - height / 2)

        var animationImages: Array<TextureAtlas.AtlasRegion> = Array()
        for (i in 0 until 15)
            animationImages.add(BaseGame.textureAtlas!!.findRegion("character/10-15/head"))
        animationImages.add(BaseGame.textureAtlas!!.findRegion("character/10-15/head-blink"))
        head = BaseActor(0f, 0f, s)
        head.setAnimation(Animation(.5f, animationImages, Animation.PlayMode.LOOP))
        head.setSize(25f, 15f)
        head.setPosition(x - 12f, y - 7.7f)
        head.setOrigin(Align.center)
        head.addAction(Actions.forever(Actions.sequence(
            Actions.rotateBy(2.5f, 2.5f),
            Actions.rotateBy(-5f, 5f),
            Actions.rotateBy(2.5f, 2.5f)
        )))

        body = BaseActor(0f, 0f, s)
        body.loadImage("character/10-15/body")
        body.setSize(22.5f, 19.8f)
        body.setPosition(x - 10.8f, y - 25f)

        leftArm = BaseActor(0f, 0f, s)
        leftArm.loadImage("character/10-15/leftArm")
        leftArm.setSize(6.5f, 8.2f)
        leftArm.setPosition(x + 9.1f, y - 14.1f)
        leftArm.setOrigin(Align.center)
        leftArm.addAction(
            Actions.forever(
                Actions.sequence(
                    Actions.parallel(
                        Actions.rotateBy(-15f, 5f),
                        Actions.moveBy(-1f, -.55f, 5f)
                    ),
                    Actions.parallel(
                        Actions.rotateBy(15f, 5f),
                        Actions.moveBy(1f, .55f, 5f)
                    )
                )
            )
        )

        rightArm = BaseActor(0f, 0f, s)
        rightArm.loadImage("character/10-15/rightArm")
        rightArm.setSize(6.5f, 8.2f)
        rightArm.setPosition(x - 15f, y - 14.1f)
        rightArm.setOrigin(Align.center)
        rightArm.addAction(
            Actions.forever(
                Actions.sequence(
                    Actions.parallel(
                        Actions.rotateBy(15f, 5f),
                        Actions.moveBy(1.1f, -.6f, 5f)
                    ),
                    Actions.parallel(
                        Actions.rotateBy(-15f, 5f),
                        Actions.moveBy(-1.1f, .6f, 5f)
                    )
                )
            )
        )
    }

    fun makeTransparent() {
        leftArm.color.a = 0f
        rightArm.color.a = 0f
        head.color.a = 0f
        body.color.a = 0f
    }

    fun fadeIn(duration: Float = .125f) {
        leftArm.addAction(Actions.fadeIn(duration))
        rightArm.addAction(Actions.fadeIn(duration))
        head.addAction(Actions.fadeIn(duration))
        body.addAction(Actions.fadeIn(duration))
    }

    fun fadeOut(duration: Float = .125f) {
        leftArm.addAction(Actions.fadeOut(duration))
        rightArm.addAction(Actions.fadeOut(duration))
        head.addAction(Actions.fadeOut(duration))
        body.addAction(Actions.fadeOut(duration))
    }
}
