package com.dfodor.motionlayout.sample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.dfodor.motionlayout.sample.databinding.ActivityMainBinding

private const val SCALE = 1.2f

class MainActivity : AppCompatActivity() {

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

    private fun animate(view: View, musicBand: MusicBand) {
        val marginTop = (view.height * SCALE).toInt() - (iconSize * SCALE / 2).toInt() - margin

        var set = animatedView.getConstraintSet(firstSet)
        set.setMargin(R.id.thumbnail, ConstraintSet.TOP, view.y.toInt())
        set.setVisibility(R.id.thumbnail, ConstraintSet.VISIBLE)
        set.setMargin(R.id.discography, ConstraintSet.TOP, marginTop)
        set.applyTo(animatedView)

        set = animatedView.getConstraintSet(secondSet)
        set.setMargin(R.id.discography, ConstraintSet.TOP, marginTop)
        set.applyTo(animatedView)

        set = animatedView.getConstraintSet(thirdSet)
        set.setMargin(R.id.discography, ConstraintSet.TOP, marginTop)
        set.applyTo(animatedView)

        with(binding) {
            thumbnail.background =
                ContextCompat.getDrawable(this@MainActivity, musicBand.drawableId)
            name.text = musicBand.name
            tags.text = musicBand.tags
            description.text = musicBand.description
            aboutText.text = musicBand.description

            var started = false
            animatedView.setTransitionListener(object : TransitionAdapter() {
                override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                    when (currentId) {
                        secondSet -> {
                            animatedView.setTransition(secondSet, thirdSet)
                            animatedView.setTransitionDuration(400)
                            animatedView.transitionToState(thirdSet)
                        }
                        thirdSet -> {
                            animatedView.setTransition(thirdSet, fourthSet)
                            animatedView.setTransitionDuration(1000)
                            animatedView.transitionToState(fourthSet)
                        }
                        fourthSet -> {
                            // todo check flickering at the end of the animation
                            animatedView.setTransition(fourthSet, fifthSet)
                            animatedView.setTransitionDuration(1000)
                            animatedView.transitionToState(fifthSet)
                        }
                    }
                }

                override fun onTransitionStarted(
                    motionLayout: MotionLayout,
                    startId: Int,
                    endId: Int
                ) {
//                    if (startId == firstSet) {
//                        root.transitionToStart()
//
////                        val constraintSet = animatedView.getConstraintSet(firstSet)
////                        constraintSet.setVisibility(R.id.thumbnail, ConstraintSet.GONE)
////                        constraintSet.applyTo(animatedView)
//                    }
                }
            })

            root.transitionToEnd()
            animatedView.transitionToEnd()
        }
    }
}