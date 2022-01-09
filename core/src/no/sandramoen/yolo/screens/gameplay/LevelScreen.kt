package no.sandramoen.yolo.screens.gameplay

import no.sandramoen.yolo.no.sandramoen.yolo.actors.Metronome
import no.sandramoen.yolo.utils.BaseScreen
import no.sandramoen.yolo.utils.Transition

class LevelScreen : BaseScreen() {
    private var tag = "LevelScreen"

    override fun initialize() {
        Metronome(50f, 50f, mainStage)
    }

    override fun update(dt: Float) {

    }
}
