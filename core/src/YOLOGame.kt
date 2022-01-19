package no.sandramoen.yolo

import no.sandramoen.yolo.screens.shell.SplashScreen
import no.sandramoen.yolo.screens.gameplay.LevelScreen
import no.sandramoen.yolo.utils.BaseGame
import no.sandramoen.yolo.utils.GooglePlayServices

class YOLOGame(googlePlayServices: GooglePlayServices?, appLocale: String) : BaseGame(googlePlayServices, appLocale) {

    override fun create() {
        super.create()

        setActiveScreen(SplashScreen()) // TODO: @release: change to this
        setActiveScreen(LevelScreen())
    }
}
