package com.dfodor.motionlayout.sample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.animated_movie_item.*
import kotlinx.android.synthetic.main.animated_movie_item.view.*
import kotlinx.android.synthetic.main.movie_item.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val items = listOf(
            MovieItem("Run", "Comedy, Drama, Thriller ", R.drawable.run),
            MovieItem("1917", "Comedy, Thriller ", R.drawable._1917),
            MovieItem("Parasite", "Comedy, Drama ", R.drawable.parasite),
            MovieItem("Run", "Drama, Thriller ", R.drawable.run)
        )

        recyclerView.adapter = MoviesAdapter(items) { view, movieItem ->
            animate(view, movieItem)
        }
    }

    private fun animate(view: View, movieItem: MovieItem) {
        val set = animated_movie_item.getConstraintSet(R.id.start_animated)
        set.setMargin(R.id.thumbnail, ConstraintSet.TOP, view.y.toInt())
        set.setVisibility(R.id.thumbnail, ConstraintSet.VISIBLE)
        set.applyTo(animated_movie_item)

        animated_movie_item.thumbnail.background =
            ContextCompat.getDrawable(this, movieItem.drawableId)
        animated_movie_item.title.text = movieItem.title
        animated_movie_item.tags.text = movieItem.tags

        animated_movie_item.back_arrow.setOnClickListener {
            var started = false

            animated_movie_item.setTransitionListener(object : TransitionAdapter() {
                override fun onTransitionChange(
                    motionLayout: MotionLayout,
                    startId: Int,
                    endId: Int,
                    progress: Float
                ) {
                    if (progress < 0.1f && startId == R.id.start_animated && !started) {
                        started = true
                        activity_root.transitionToStart()
                    }

                    if (progress == 0f && startId == R.id.start_animated) {
                        val constraintSet =
                            animated_movie_item.getConstraintSet(R.id.start_animated)
                        constraintSet.setVisibility(R.id.thumbnail, ConstraintSet.GONE)
                        constraintSet.applyTo(animated_movie_item)
                    }
                }
            })

            animated_movie_item.transitionToStart()
        }

        activity_root.transitionToStart()

        activity_root.transitionToEnd()
        animated_movie_item.transitionToEnd()
    }
}