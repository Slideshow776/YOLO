package no.sandramoen.yolo.no.sandramoen.yolo.actors.character

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Array
import no.sandramoen.yolo.utils.BaseActor
import no.sandramoen.yolo.utils.BaseGame

class Zero(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    private var tag = "Zero"
    private var pacifier: BaseActor

    init {
        var animationImages: Array<TextureAtlas.AtlasRegion> = Array()
        for (i in 0 until 15)
            animationImages.add(BaseGame.textureAtlas!!.findRegion("character/0/body"))
        animationImages.add(BaseGame.textureAtlas!!.findRegion("character/0/body-blink"))
        setAnimation(Animation(.5f, animationImages, Animation.PlayMode.LOOP))
        setSize(22f, 19f)
        setPosition(x - width / 2, y - height / 2)

        pacifier = BaseActor(0f, 0f, s)
        pacifier.loadImage("character/0/pacifier")
        pacifier.setSize(width * .25f, height * .25f * BaseGame.RATIO)
        pacifier.setPosition(x - 2.2f, y + .5f)
        pacifier.setOrigin(Align.center)
        pacifier.addAction(
            Actions.forever(
                Actions.sequence(
                    Actions.parallel(
                        Actions.scaleBy(-.25f, -.25f, .2f),
                        Actions.moveBy(0f, -.25f, .2f)
                    ),
                    Actions.parallel(
                        Actions.scaleBy(.25f, .25f, .2f),
                        Actions.moveBy(0f, .25f, .2f)
                    ),
                    Actions.delay(2f)
                )
            )
        )
    }

    fun makeTransparent() {
        color.a = 0f
        pacifier.color.a = 0f
    }

    fun fadeIn(duration: Float = .125f) {
        addAction(Actions.fadeIn(duration))
        pacifier.addAction(Actions.fadeIn(duration))
    }

    fun fadeOut(duration: Float = .125f) {
        addAction(Actions.fadeOut(duration))
        pacifier.addAction(Actions.fadeOut(duration))
    }
}
