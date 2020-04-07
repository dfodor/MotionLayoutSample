package com.dfodor.motionlayout.sample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.dfodor.motionlayout.sample.databinding.ActivityMainBinding
import kotlin.math.roundToInt

private const val SCALE = 1.2f
private const val FIRST_ANIMATION_PART_DURATION = 400
private const val SECOND_ANIMATION_PART_DURATION = 800

class MainActivity : AppCompatActivity() {

    private enum class StateSet {
        START,
        END,
        FIRST_TO_SECOND,
        SECOND_TO_THIRD,
        FOURTH_TO_FIFTH,
        SECOND_TO_FIRST,
        THIRD_TO_SECOND,
        FOURTH_TO_THIRD,
        FIFTH_TO_FOURTH
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var animatedView: MotionLayout

    private var iconSize = 0
    private var margin = 0
    private var scaleValue = 0

    private val firstSet = R.id.first_set
    private val secondSet = R.id.second_set
    private val thirdSet = R.id.third_set
    private val fourthSet = R.id.fourth_set
    private val fifthSet = R.id.fifth_set

    private var activeSet = StateSet.START

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        animatedView = binding.animatedMusicBandItem

        iconSize = resources.getDimensionPixelSize(R.dimen.six_grid_unit)
        margin = resources.getDimensionPixelSize(R.dimen.one_grid_unit)
        scaleValue = (iconSize * SCALE / 2).toInt()

        binding.recyclerView.adapter = MusicAdapter(getTestItems()) { view, musicBand ->
            animate(view, musicBand)
        }
    }

    private val transitionAdapter = object : TransitionAdapter() {

        @Suppress("NON_EXHAUSTIVE_WHEN")
        override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
            with(animatedView) {
                when (activeSet) {
                    StateSet.FIRST_TO_SECOND -> {
                        setTransition(secondSet, thirdSet)
                        setTransitionDuration(FIRST_ANIMATION_PART_DURATION)
                        transitionToState(thirdSet)
                        activeSet = StateSet.SECOND_TO_THIRD
                    }
                    StateSet.SECOND_TO_FIRST -> {
                        binding.root.transitionToStart()

                        val constraintSet = getConstraintSet(firstSet)
                        constraintSet.setVisibility(R.id.thumbnail, ConstraintSet.GONE)
                        constraintSet.applyTo(this)
                        activeSet = StateSet.START
                    }

                    StateSet.THIRD_TO_SECOND -> {
                        setTransition(secondSet, firstSet)
                        setTransitionDuration(FIRST_ANIMATION_PART_DURATION)
                        transitionToState(firstSet)
                        activeSet = StateSet.SECOND_TO_FIRST
                    }

                    StateSet.FOURTH_TO_FIFTH -> {
                        setTransition(fourthSet, fifthSet)
                        setTransitionDuration(SECOND_ANIMATION_PART_DURATION)
                        transitionToState(fifthSet)
                        activeSet = StateSet.END
                    }

                    StateSet.FIFTH_TO_FOURTH -> {
                        setTransition(fourthSet, thirdSet)
                        setTransitionDuration(SECOND_ANIMATION_PART_DURATION)
                        transitionToState(thirdSet)
                        activeSet = StateSet.FOURTH_TO_THIRD
                    }
                }
            }
        }

        override fun onTransitionStarted(motionLayout: MotionLayout, startId: Int, endId: Int) {
            val progress = motionLayout.progress.roundToInt()
            val isFourthSet = motionLayout.currentState == fourthSet
            val isSecondSet = motionLayout.currentState == secondSet

            if (isSecondSet && startId == secondSet) {
                activeSet = StateSet.THIRD_TO_SECOND
            } else if (isFourthSet && (progress == 0)) {
                activeSet = StateSet.FOURTH_TO_FIFTH
            } else if (isFourthSet && (progress == 1)) {
                activeSet = StateSet.FIFTH_TO_FOURTH
            }
        }
    }


    private fun animate(view: View, musicBand: MusicBand) {
        val marginTop = (view.height * SCALE).toInt() - (iconSize * SCALE / 2).toInt() - margin

        animatedView.also {
            var set = it.getConstraintSet(firstSet)
            set.setMargin(R.id.thumbnail, ConstraintSet.TOP, view.y.toInt())
            set.setVisibility(R.id.thumbnail, ConstraintSet.VISIBLE)
            set.setMargin(R.id.discography, ConstraintSet.TOP, marginTop)
            set.applyTo(it)

            set = it.getConstraintSet(secondSet)
            set.setMargin(R.id.discography, ConstraintSet.TOP, marginTop)
            set.applyTo(it)

            set = it.getConstraintSet(thirdSet)
            set.setMargin(R.id.discography, ConstraintSet.TOP, marginTop)
            set.applyTo(it)

            with(binding) {
                thumbnail.background =
                    ContextCompat.getDrawable(this@MainActivity, musicBand.drawableId)
                name.text = musicBand.name
                tags.text = musicBand.tags
                description.text = musicBand.description
                aboutText.text = musicBand.description

                it.setTransitionListener(transitionAdapter)

                root.transitionToEnd()
                it.setTransition(firstSet, secondSet)
                it.setTransitionDuration(FIRST_ANIMATION_PART_DURATION)
                it.transitionToState(secondSet)
                activeSet = StateSet.FIRST_TO_SECOND
            }
        }
    }
}