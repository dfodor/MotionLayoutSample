package com.dfodor.motionlayout.sample.sample_1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.dfodor.motionlayout.sample.R
import com.dfodor.motionlayout.sample.databinding.ActivityMusicBandListBinding

private const val SCALE = 1.2f
private const val ANIMATION_DURATION = 400

class MusicBandListActivity : AppCompatActivity() {

    private enum class StateSet {
        FIRST_TO_SECOND,
        SECOND_TO_THIRD,
        SECOND_TO_FIRST,
        THIRD_TO_SECOND
    }

    private lateinit var binding: ActivityMusicBandListBinding
    private lateinit var animatedView: MotionLayout

    private var iconSize = 0
    private var margin = 0
    private var scaleValue = 0

    private val firstSet = R.id.first_set
    private val secondSet = R.id.second_set
    private val thirdSet = R.id.third_set

    private var activeSet = StateSet.FIRST_TO_SECOND

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicBandListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        animatedView = binding.animatedMusicBandItem

        iconSize = resources.getDimensionPixelSize(R.dimen.six_grid_unit)
        margin = resources.getDimensionPixelSize(R.dimen.one_grid_unit)
        scaleValue = (iconSize * SCALE / 2).toInt()

        binding.recyclerView.adapter = MusicBandAdapter(getTestItems()) { vhValues, musicBand ->
            animate(vhValues, musicBand)
        }
    }

    private val transitionAdapter = object : TransitionAdapter() {

        @Suppress("NON_EXHAUSTIVE_WHEN")
        override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
            with(animatedView) {
                when (activeSet) {
                    StateSet.FIRST_TO_SECOND -> {
                        setTransition(secondSet, thirdSet)
                        setTransitionDuration(ANIMATION_DURATION)
                        transitionToState(thirdSet)
                        activeSet = StateSet.SECOND_TO_THIRD
                    }

                    StateSet.SECOND_TO_FIRST -> {
                        binding.root.transitionToStart()

                        val constraintSet = getConstraintSet(firstSet)
                        constraintSet.setVisibility(R.id.thumbnail, ConstraintSet.GONE)
                        constraintSet.applyTo(this)
                    }

                    StateSet.THIRD_TO_SECOND -> {
                        setTransition(secondSet, firstSet)
                        setTransitionDuration(ANIMATION_DURATION)
                        transitionToState(firstSet)
                        activeSet = StateSet.SECOND_TO_FIRST
                    }
                }
            }
        }

        override fun onTransitionStarted(motionLayout: MotionLayout, startId: Int, endId: Int) {
            val isSecondSet = motionLayout.currentState == secondSet && startId == secondSet
            if (isSecondSet) {
                activeSet = StateSet.THIRD_TO_SECOND
            }
        }
    }


    private fun animate(
        vhValues: MusicBandAdapter.ViewHolderValues,
        musicBandModel: MusicBandModel
    ) {
        val marginTop =
            (vhValues.getHeight() * SCALE).toInt() - (iconSize * SCALE / 2).toInt() - margin

        animatedView.also {
            var set = it.getConstraintSet(firstSet)
            set.setMargin(R.id.thumbnail, ConstraintSet.TOP, vhValues.getY())
            set.setVisibility(R.id.thumbnail, ConstraintSet.VISIBLE)
            set.setMargin(R.id.more_info, ConstraintSet.TOP, marginTop)
            set.applyTo(it)

            set = it.getConstraintSet(secondSet)
            set.setMargin(R.id.more_info, ConstraintSet.TOP, marginTop)
            set.applyTo(it)

            set = it.getConstraintSet(thirdSet)
            set.setMargin(R.id.more_info, ConstraintSet.TOP, marginTop)
            set.setMargin(R.id.about_container, ConstraintSet.TOP, marginTop)
            set.applyTo(it)

            with(binding) {
                thumbnail.background =
                    ContextCompat.getDrawable(this@MusicBandListActivity, musicBandModel.drawableId)
                name.text = musicBandModel.name
                tags.text = musicBandModel.tags
                description.text = getString(musicBandModel.shortDescriptionStringRes)
                aboutText.text = getString(musicBandModel.aboutStringRes)

                it.setTransitionListener(transitionAdapter)

                root.transitionToEnd()
                it.setTransition(firstSet, secondSet)
                it.setTransitionDuration(ANIMATION_DURATION)
                it.transitionToState(secondSet)
                activeSet = StateSet.FIRST_TO_SECOND
            }
        }
    }
}