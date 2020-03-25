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

class MainActivity : AppCompatActivity() {

    private lateinit var anchorView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val items = listOf(
            MovieItem("Parasite", "Comedy, Drama, Thriller ", R.drawable.parasite),
            MovieItem("Asdf", "Comedy, Thriller ", R.drawable._1917),
            MovieItem("Badlfqg", "Comedy, Drama ", R.drawable.run),
            MovieItem("wfwwfw", "Drama, Thriller ", R.drawable.parasite)
        )

        recyclerView.adapter = MoviesAdapter(items) { view, movieItem ->
            animate(view, movieItem)
        }
    }

    private fun animate(view: View, movieItem: MovieItem) {
        anchorView = view

        val set = animated_movie_item.getConstraintSet(R.id.start)
        set.setMargin(animated_movie_item.thumbnail.id, ConstraintSet.TOP, view.y.toInt())
        set.applyTo(animated_movie_item)

        animated_movie_item.thumbnail.background =
            ContextCompat.getDrawable(this, movieItem.drawableId)

        animated_movie_item.back_arrow.setOnClickListener {
            var started = false

            animated_movie_item.setTransitionListener(object : TransitionAdapter() {
                override fun onTransitionChange(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int,
                    progress: Float
                ) {
                    if (progress < 0.1f && startId == R.id.start && !started) {
                        started = true
                        activity_root.transitionToStart()
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