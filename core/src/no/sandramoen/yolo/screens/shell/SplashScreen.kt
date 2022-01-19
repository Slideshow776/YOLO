package no.sandramoen.yolo.screens.shell

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import no.sandramoen.yolo.actors.ShockwaveBackground
import no.sandramoen.yolo.screens.gameplay.LevelScreen
import no.sandramoen.yolo.utils.BaseActor
import no.sandramoen.yolo.utils.BaseGame
import no.sandramoen.yolo.utils.BaseScreen

class SplashScreen : BaseScreen() {
    private lateinit var tag: String
    private lateinit var shock: ShockwaveBackground

    override fun initialize() {
        tag = "SplashScreen"

        // image with effect
        shock = ShockwaveBackground(0f, 0f, "images/excluded/splash.jpg", mainStage)

        // black overlay
        val background = BaseActor(0f, 0f, mainStage)
        background.loadImage("whitePixel_BIG")
        background.color = Color.BLACK
        background.touchable = Touchable.childrenOnly
        background.setSize(BaseGame.WORLD_WIDTH+2, BaseGame.WORLD_HEIGHT+2)
        background.setPosition(-1f, -1f)
        var totalDurationInSeconds = 6f
        background.addAction(
            Actions.sequence(
                Actions.fadeIn(0f),
                Actions.fadeOut(totalDurationInSeconds / 4),
                Actions.delay(totalDurationInSeconds / 4),
                Actions.fadeIn(totalDurationInSeconds / 4)
            )
        )
        background.addAction(Actions.after(Actions.run {
            dispose()
            // GameUtils.stopAllMusic()
            BaseGame.setActiveScreen(LevelScreen())
        }))
    }

    override fun update(dt: Float) {}

    override fun dispose() {
        super.dispose()
        shock.shaderProgram.dispose()
        shock.remove()
    }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACKSPACE)
            Gdx.app.exit()
        return false
    }
}
