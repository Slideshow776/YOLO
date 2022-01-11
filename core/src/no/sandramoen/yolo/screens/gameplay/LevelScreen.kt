package no.sandramoen.yolo.screens.gameplay

import no.sandramoen.yolo.no.sandramoen.yolo.actors.Metronome
import no.sandramoen.yolo.utils.BaseScreen
import no.sandramoen.yolo.utils.Transition

class LevelScreen : BaseScreen() {
    private var tag = "LevelScreen"
    private lateinit var metronome: Metronome

    override fun initialize() {
        metronome = Metronome(50f, 50f, mainStage)
    }

    override fun update(dt: Float) {

    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (metronome.stop)
            metronome.restart()
        else {
            metronome.stop = true
            println(metronome.getScore())
        }
        return super.touchDown(screenX, screenY, pointer, button)
    }
}
