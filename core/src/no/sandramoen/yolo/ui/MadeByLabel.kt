package no.sandramoen.yolo.no.sandramoen.yolo.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import no.sandramoen.yolo.utils.BaseGame
import no.sandramoen.yolo.utils.GameUtils

class MadeByLabel : Label("made by Sandra Moen 2022", BaseGame.labelStyle) {
    private var tag = "MadeByLabel"

    init {
        setFontScale(.4f)
        setAlignment(Align.center)
        color = Color.GRAY
        addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                /*println("$tag: Clicked")*/
                super.clicked(event, x, y)
                BaseGame.clickSound!!.play(BaseGame.soundVolume)
                addAction(
                    Actions.sequence(
                        Actions.delay(.5f),
                        Actions.run {
                            /*Gdx.net.openURI("https://sandramoen.no")*/
                        }
                    ))
                event!!.stop()
            }
        })
        GameUtils.addWidgetEnterExitEffect(this, Color.GRAY, Color.DARK_GRAY)

        addAction(Actions.sequence(
            Actions.fadeOut(0f),
            Actions.fadeIn(5f),
            Actions.fadeOut(5f),
            Actions.run { remove() }
        ))
    }
}
