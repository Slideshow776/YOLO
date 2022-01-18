package no.sandramoen.yolo.screens.gameplay

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import no.sandramoen.yolo.actors.Background
import no.sandramoen.yolo.no.sandramoen.yolo.actors.CharacterHandler
import no.sandramoen.yolo.no.sandramoen.yolo.actors.Metronome
import no.sandramoen.yolo.no.sandramoen.yolo.actors.character.*
import no.sandramoen.yolo.utils.BaseGame
import no.sandramoen.yolo.utils.BaseScreen
import java.math.RoundingMode

class LevelScreen : BaseScreen() {
    private var tag = "LevelScreen"
    private lateinit var metronome: Metronome
    private lateinit var characterHandler: CharacterHandler
    private lateinit var characeter: Zero
    private lateinit var scoreLabel: Label
    private lateinit var ageLabel: Label

    private var gameScore = 0
    private var currentLifePhase: Int = 1
    private var rawCurrentScore: Float = 0f
    private val lifePhases: Int = 5
    private val numDaysInYear: Int = 365
    private val maxYears: Int = 100

    override fun initialize() {
        // actors
        Background(0f, 0f, mainStage)
        characterHandler = CharacterHandler(mainStage)
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

    override fun update(dt: Float) {}

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

        gameScore += score
        setCountingText(scoreLabel, gameScore.toFloat(), scoreLabel.text.toString().toFloat(), true)

        val years: Float = (gameScore / numDaysInYear.toFloat()).toBigDecimal().setScale(1, RoundingMode.UP).toFloat()
        setCountingText(ageLabel, years, ageLabel.text.toString().toFloat())
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
        characterHandler.setImage(0)
        metronome.restart()
        rawCurrentScore = 0f
        gameScore = 0
    }

    private fun checkpoint() {
        val difficulty = .5f
        val pass = gameScore >= ((numDaysInYear * maxYears) / lifePhases) * difficulty * currentLifePhase

        if (pass) {
            currentLifePhase++
            if (currentLifePhase == lifePhases + 1) {
                BaseGame.churchBellSound!!.play(BaseGame.soundVolume)
                characterHandler.setImage(101)
            } else {
                characterHandler.setImage(gameScore / 365)
            }
        } else {
            currentLifePhase = lifePhases + 1
            characterHandler.setImage(101)
            BaseGame.churchBellSound!!.play(BaseGame.soundVolume, .5f, 0f)
        }
    }

    private fun setCountingText(label: Label, number: Float, previousNumber: Float, convertToInt: Boolean = false) {
        var counter = previousNumber
        val increment = number / 20f
        var pitchCounter = 1.5f
        label.addAction(
            Actions.forever(
                Actions.sequence(
                    Actions.run {
                        counter += increment
                        if (counter > number) {
                            label.clearActions()
                            label.setText("$number")
                        }
                        BaseGame.countSound!!.play(BaseGame.soundVolume * .25f, pitchCounter, 0f)
                        pitchCounter += 1 / 50f
                        label.setText("${
                            if (convertToInt) 
                                counter.toBigDecimal().setScale(1, RoundingMode.UP).toFloat().toInt()
                            else
                                counter.toBigDecimal().setScale(1, RoundingMode.UP).toFloat()
                            }"
                        )
                    },
                    Actions.delay(0f)
                )
            )
        )
    }
}
