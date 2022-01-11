package no.sandramoen.yolo.screens.gameplay

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import no.sandramoen.yolo.no.sandramoen.yolo.actors.LifeImages
import no.sandramoen.yolo.no.sandramoen.yolo.actors.Metronome
import no.sandramoen.yolo.utils.BaseGame
import no.sandramoen.yolo.utils.BaseScreen
import java.math.RoundingMode

class LevelScreen : BaseScreen() {
    private var tag = "LevelScreen"
    private lateinit var metronome: Metronome
    private lateinit var lifeImages: LifeImages
    private lateinit var scoreLabel: Label
    private lateinit var ageLabel: Label

    private var currentLifePhase: Int = 1
    private var rawCurrentScore: Float = 0f
    private val lifePhases: Int = 5
    private val numDaysInYear: Int = 365
    private val maxYears: Int = 100

    override fun initialize() {
        // actors
        lifeImages = LifeImages(50f, 57.5f, mainStage)
        metronome = Metronome(50f, 22.5f, mainStage)

        // ui
        scoreLabel = Label("0", BaseGame.labelStyle)
        scoreLabel.setAlignment(Align.center)
        uiTable.add(scoreLabel).fill().padTop(Gdx.graphics.height * .08f).row()

        ageLabel = Label("0", BaseGame.labelStyle)
        ageLabel.setScale(.8f)
        ageLabel.setFontScale(.8f)
        ageLabel.setAlignment(Align.center)
        ageLabel.color = Color.OLIVE
        uiTable.add(ageLabel).fill()

        uiTable.top()
        // uiTable.debug = true
    }

    override fun update(dt: Float) {

    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (currentLifePhase <= lifePhases)
            progress()
        else
            restart()
        return super.touchDown(screenX, screenY, pointer, button)
    }

    private fun calculateAndDisplayScore(performance: Float) {
        rawCurrentScore = performance
        val score: Int = (((numDaysInYear * maxYears) / lifePhases) * performance).toInt()

        val finalScore = scoreLabel.text.toString().toInt() + score
        scoreLabel.setText("$finalScore")

        val years: Float = (finalScore / numDaysInYear.toFloat()).toBigDecimal().setScale(1, RoundingMode.UP).toFloat()
        ageLabel.setText("$years")
    }

    private fun progress() {
        if (metronome.stop)
            metronome.restart()
        else {
            metronome.stop = true
            calculateAndDisplayScore(metronome.getScore())
            checkpoint()
        }
    }

    private fun restart() {
        scoreLabel.setText("0")
        ageLabel.setText("0")
        currentLifePhase = 1
        lifeImages.setImage(currentLifePhase)
        metronome.restart()
        rawCurrentScore = 0f
    }

    private fun checkpoint() {
        val score = scoreLabel.text.toString().toInt()
        val difficulty = .5f
        val pass = score >= ((numDaysInYear * maxYears) / lifePhases) * difficulty * currentLifePhase

        if (pass) {
            currentLifePhase++
            lifeImages.setImage(currentLifePhase)
            if (currentLifePhase == lifePhases + 1)
                BaseGame.churchBell!!.play(BaseGame.soundVolume)
        } else {
            currentLifePhase = lifePhases + 1
            lifeImages.setImage(currentLifePhase)
            BaseGame.churchBell!!.play(BaseGame.soundVolume)
        }
    }
}
