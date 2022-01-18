package no.sandramoen.yolo.no.sandramoen.yolo.actors

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import no.sandramoen.yolo.no.sandramoen.yolo.actors.character.*
import no.sandramoen.yolo.utils.BaseActor

class CharacterHandler(s: Stage) {
    private var tag = "CharacterHandler"
    private var seventyToOneHundred: SeventyToOneHundred
    private var fiftyToSeventy: FiftyToSeventy
    private var thirtyToFifty: ThirtyToFifty
    private var twentyToThirty: TwentyToThirty
    private var fifteenToTwenty: FifteenToTwenty
    private var tenToFifteen: TenToFifteen
    private var zero: Zero
    private var dead: BaseActor

    init {
        seventyToOneHundred = SeventyToOneHundred(50f, 57.5f, s)
        fiftyToSeventy = FiftyToSeventy(50f, 57.5f, s)
        thirtyToFifty = ThirtyToFifty(50f, 57.5f, s)
        twentyToThirty = TwentyToThirty(50f, 57.5f, s)
        fifteenToTwenty = FifteenToTwenty(50f, 57.5f, s)
        tenToFifteen = TenToFifteen(50f, 57.5f, s)
        zero = Zero(50f, 42f, s)

        dead = BaseActor(50f, 57.5f, s)
        dead.loadImage("character/dead")
        dead.setSize(40f, 25f)
        dead.setPosition(dead.x - 20f, dead.y - 25f)

        makeAllImagesInvisible()
        zero.fadeIn(0f)
    }

    fun setImage(age: Int) {
        var duration = .5f
        makeAllImagesInvisible(duration)
        when (age) {
            0 -> zero.fadeIn(duration)
            in 10..15 -> tenToFifteen.fadeIn(duration)
            in 15..20 -> fifteenToTwenty.fadeIn(duration)
            in 20..30 -> twentyToThirty.fadeIn(duration)
            in 30..50 -> thirtyToFifty.fadeIn(duration)
            in 50..70 -> fiftyToSeventy.fadeIn(duration)
            in 70..100 -> seventyToOneHundred.fadeIn(duration)
            101 -> dead.addAction(Actions.fadeIn(duration))
        }
    }

    private fun makeAllImagesInvisible(duration: Float = 0f) {
        /*seventyToOneHundred.makeTransparent()
        fiftyToSeventy.makeTransparent()
        thirtyToFifty.makeTransparent()
        twentyToThirty.makeTransparent()
        fifteenToTwenty.makeTransparent()
        tenToFifteen.makeTransparent()
        zero.makeTransparent()
        dead.color.a = 0f*/

        seventyToOneHundred.fadeOut(duration)
        fiftyToSeventy.fadeOut(duration)
        thirtyToFifty.fadeOut(duration)
        twentyToThirty.fadeOut(duration)
        fifteenToTwenty.fadeOut(duration)
        tenToFifteen.fadeOut(duration)
        zero.fadeOut(duration)
        dead.addAction(Actions.fadeOut(duration))
    }
}
