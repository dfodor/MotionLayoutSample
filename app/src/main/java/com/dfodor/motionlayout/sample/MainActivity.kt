package com.dfodor.motionlayout.sample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.dfodor.motionlayout.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var animatedView: MotionLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val lorem =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor" +
                    " incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud" +
                    " exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute " +
                    "irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla" +
                    " pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui " +
                    "officia deserunt mollit anim id est laborum."

        val items = listOf(
            MovieItem("Run", "Comedy, Drama, Thriller ", lorem.take(300).trim(), R.drawable.run),
            MovieItem("1917", "Comedy, Thriller ", lorem.takeLast(260).trim(), R.drawable._1917),
            MovieItem("Parasite", "Comedy, Drama ", lorem.take(220).trim(), R.drawable.parasite),
            MovieItem("Run", "Drama, Thriller ", lorem.takeLast(190).trim(), R.drawable.run)
        )

        binding.recyclerView.adapter = MoviesAdapter(items) { view, movieItem ->
            animate(view, movieItem)
        }

        animatedView = binding.animatedMovieItem
    }

    private fun animate(view: View, movieItem: MovieItem) {
        val set = animatedView.getConstraintSet(R.id.start_animated)
        set.setMargin(R.id.thumbnail, ConstraintSet.TOP, view.y.toInt())
        set.setVisibility(R.id.thumbnail, ConstraintSet.VISIBLE)
        set.applyTo(animatedView)

        with(binding) {
            thumbnail.background =
                ContextCompat.getDrawable(this@MainActivity, movieItem.drawableId)
            title.text = movieItem.title
            tags.text = movieItem.tags
            description.text = movieItem.description

            backArrow.setOnClickListener {
                var started = false

                animatedView.setTransitionListener(object : TransitionAdapter() {
                    override fun onTransitionChange(
                        motionLayout: MotionLayout,
                        startId: Int,
                        endId: Int,
                        progress: Float
                    ) {
                        if (progress < 0.1f && startId == R.id.start_animated && !started) {
                            started = true
                            binding.root.transitionToStart()
                        }

                        if (progress == 0f && startId == R.id.start_animated) {
                            val constraintSet = animatedView.getConstraintSet(R.id.start_animated)
                            constraintSet.setVisibility(R.id.thumbnail, ConstraintSet.GONE)
                            constraintSet.applyTo(animatedView)
                        }
                    }
                })

                animatedView.transitionToStart()
            }

            binding.root.transitionToEnd()
            animatedView.transitionToEnd()
        }
    }
}