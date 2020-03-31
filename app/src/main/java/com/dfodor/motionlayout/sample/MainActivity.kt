package com.dfodor.motionlayout.sample

import android.os.Bundle
import android.util.Log
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

        var set = animatedView.getConstraintSet(R.id.start)
        set.setMargin(R.id.thumbnail, ConstraintSet.TOP, view.y.toInt())
        set.setVisibility(R.id.thumbnail, ConstraintSet.VISIBLE)
        set.setMargin(R.id.discography, ConstraintSet.TOP, marginTop)
        set.applyTo(animatedView)

        set = animatedView.getConstraintSet(R.id.middle)
        set.setMargin(R.id.discography, ConstraintSet.TOP, marginTop)
        set.applyTo(animatedView)

        with(binding) {
            thumbnail.background =
                ContextCompat.getDrawable(this@MainActivity, musicBand.drawableId)
            name.text = musicBand.name
            tags.text = musicBand.tags
            description.text = musicBand.description

            var started = false
            var isFromEndToMiddle = false
            var isFromEndToMiddleSet = false
            animatedView.setTransitionListener(object : TransitionAdapter() {
                override fun onTransitionChange(
                    motionLayout: MotionLayout,
                    startId: Int,
                    endId: Int,
                    progress: Float
                ) {

                    if (startId == R.id.middle && endId == R.id.end && !isFromEndToMiddleSet) {
                        isFromEndToMiddle = progress - 0.5 > 0
                        isFromEndToMiddleSet = true
                    }

                    if (startId == R.id.start) {
                        if (progress < 0.1f && !started) {
                            started = true
                            root.transitionToStart()
                        }

                        if (progress == 0f && startId == R.id.start) {
                            val constraintSet = animatedView.getConstraintSet(R.id.start)
                            constraintSet.setVisibility(R.id.thumbnail, ConstraintSet.GONE)
                            constraintSet.applyTo(animatedView)
                        }
                    } else if (endId == R.id.end && !started && isFromEndToMiddle) {
                        if (progress < 0.5) {
                            started = true
                            discography.background =
                                ContextCompat.getDrawable(
                                    applicationContext,
                                    R.drawable.round_background
                                )
                        }
                    } else if (startId == R.id.middle && !started && !isFromEndToMiddle) {
                        if (progress > 0.5) {
                            started = true
                            discography.background =
                                ContextCompat.getDrawable(
                                    applicationContext,
                                    R.drawable.square_background
                                )
                        }
                    }

                    if (progress == 1f || progress == 0f) {
                        started = false
                        isFromEndToMiddleSet = false
                    }
                }
            })

            animatedView.transitionToStart()

            root.transitionToEnd()
            animatedView.transitionToEnd()
        }
    }
}