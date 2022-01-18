package no.sandramoen.yolo.utils

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.Preferences
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetErrorListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable
import com.badlogic.gdx.utils.I18NBundle
import kotlin.system.measureTimeMillis

abstract class BaseGame(var googlePlayServices: GooglePlayServices?, appLocale: String) : Game(), AssetErrorListener {
    private val tag = "BaseGame.kt"
    private val appLocale = appLocale

    init { game = this }

    companion object {
        private var game: BaseGame? = null

        lateinit var assetManager: AssetManager
        lateinit var fontGenerator: FreeTypeFontGenerator
        const val WORLD_WIDTH = 100f
        const val WORLD_HEIGHT = 100f
        const val scale = 1.0f
        var RATIO = 0f
        var enableCustomShaders = true
        val lightPink = Color(1f, .816f, .94f, 1f)

        // game assets
        var gps: GooglePlayServices? = null
        var labelStyle: LabelStyle? = null
        var textButtonStyle: TextButtonStyle? = null
        var textureAtlas: TextureAtlas? = null
        var churchBellSound: Sound? = null
        var countSound: Sound? = null
        var defaultShader: String? = null
        var constellationShader: String? = null

        // game state
        var prefs: Preferences? = null
        var loadPersonalParameters = false
        var soundVolume = .75f
        var musicVolume = .125f
        var isGPS = false
        var myBundle: I18NBundle? = null
        var currentLocale: String? = null

        fun setActiveScreen(s: BaseScreen) = game?.setScreen(s)
    }

    override fun create() {
        Gdx.input.inputProcessor = InputMultiplexer() // discrete input

        // global variables
        gps = this.googlePlayServices
        GameUtils.loadGameState()
        if (!loadPersonalParameters) {
            soundVolume = .75f
            musicVolume = .25f
            currentLocale = appLocale
        }
        RATIO = Gdx.graphics.width.toFloat() / Gdx.graphics.height

        // asset manager
        val time = measureTimeMillis {
            assetManager = AssetManager()
            assetManager.setErrorListener(this)
            assetManager.load("images/included/packed/yolo.pack.atlas", TextureAtlas::class.java)

            // music
            // assetManager.load("audio/music/251461__joshuaempyre__arcade-music-loop.wav", Music::class.java)

            // sounds
            assetManager.load("audio/sound/259278__akke-h__church-bell-fischerhude-short.wav", Sound::class.java)
            assetManager.load("audio/sound/Blip_Select34.wav", Sound::class.java)

            // fonts
            val resolver = InternalFileHandleResolver()
            assetManager.setLoader(FreeTypeFontGenerator::class.java, FreeTypeFontGeneratorLoader(resolver))
            assetManager.setLoader(BitmapFont::class.java, ".ttf", FreetypeFontLoader(resolver))
            assetManager.setLoader(Text::class.java, TextLoader(InternalFileHandleResolver()))

            // shaders
            assetManager.load(AssetDescriptor("shaders/default.vs", Text::class.java, TextLoader.TextParameter()))
            assetManager.load(AssetDescriptor("shaders/constellation.fs", Text::class.java, TextLoader.TextParameter()))

            // skins
            // assetManager.load("skins/arcade/arcade.json", Skin::class.java)

            // i18n
            // assetManager.load("i18n/MyBundle", I18NBundle::class.java, I18NBundleParameter(Locale(currentLocale)))

            assetManager.finishLoading()

            textureAtlas = assetManager.get("images/included/packed/yolo.pack.atlas") // all images are found in this global static variable

            // audio
            // levelMusic = assetManager.get("audio/music/251461__joshuaempyre__arcade-music-loop.wav", Music::class.java)

            churchBellSound = assetManager.get("audio/sound/259278__akke-h__church-bell-fischerhude-short.wav", Sound::class.java)
            countSound = assetManager.get("audio/sound/Blip_Select34.wav", Sound::class.java)

            // text files
            defaultShader = assetManager.get("shaders/default.vs", Text::class.java).getString()
            constellationShader = assetManager.get("shaders/constellation.fs", Text::class.java).getString()

            // skin
            // skin = assetManager.get("skins/arcade/arcade.json", Skin::class.java)

            // i18n
            // myBundle = assetManager["i18n/MyBundle", I18NBundle::class.java]

            // fonts
            FreeTypeFontGenerator.setMaxTextureSize(2048) // solves font bug that won't show some characters like "." and "," in android
            fontGenerator = FreeTypeFontGenerator(Gdx.files.internal("fonts/ARCADE_R.TTF"))
            val fontParameters = FreeTypeFontParameter()
            fontParameters.size = (.038f * Gdx.graphics.height).toInt() // Font size is based on width of screen...
            fontParameters.color = Color.WHITE
            fontParameters.borderWidth = 2f
            fontParameters.borderColor = Color.BLACK
            fontParameters.borderStraight = true
            fontParameters.minFilter = TextureFilter.Linear
            fontParameters.magFilter = TextureFilter.Linear
            val customFont = fontGenerator.generateFont(fontParameters)

            val buttonFontParameters = FreeTypeFontParameter()
            buttonFontParameters.size = (.04f * Gdx.graphics.height).toInt() // If the resolutions height is 1440 then the font size becomes 86
            buttonFontParameters.color = Color.WHITE
            buttonFontParameters.borderWidth = 2f
            buttonFontParameters.borderColor = Color.BLACK
            buttonFontParameters.borderStraight = true
            buttonFontParameters.minFilter = TextureFilter.Linear
            buttonFontParameters.magFilter = TextureFilter.Linear
            val buttonCustomFont = fontGenerator.generateFont(buttonFontParameters)

            labelStyle = LabelStyle()
            labelStyle!!.font = customFont

            textButtonStyle = TextButtonStyle()
            val buttonTexUp = textureAtlas!!.findRegion("blankPixel") // button
            // val buttonTexDown = textureAtlas!!.findRegion("button-pressed")
            val buttonPatchUp = NinePatch(buttonTexUp, 44, 24, 24, 24)
            // val buttonPatchDown = NinePatch(buttonTexDown, 44, 24, 24, 24)
            textButtonStyle!!.up = NinePatchDrawable(buttonPatchUp)
            // textButtonStyle!!.down = NinePatchDrawable(buttonPatchDown)
            textButtonStyle!!.font = buttonCustomFont
            textButtonStyle!!.fontColor = Color.WHITE
        }
        Gdx.app.error(tag, "Asset manager took $time ms to load all game assets.")
    }

    override fun dispose() {
        super.dispose()
        GameUtils.saveGameState()
        if (gps != null) gps!!.signOut()
        /*try { // TODO: uncomment this when development is done
            assetManager.dispose()
            fontGenerator.dispose()
        } catch (error: UninitializedPropertyAccessException) {
            Gdx.app.error("BaseGame", "$error")
        }*/
    }

    override fun error(asset: AssetDescriptor<*>, throwable: Throwable) {
        Gdx.app.error(tag, "Could not load asset: " + asset.fileName, throwable)
    }
}
