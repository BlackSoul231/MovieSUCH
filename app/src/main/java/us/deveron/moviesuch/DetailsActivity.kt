package us.deveron.moviesuch

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        // We receive our movie from the bundle.
        val film = intent.extras?.get("movie") as Film
        loadMovie(film)

        val mainViewGroup = findViewById<CoordinatorLayout>(R.id.main)
        val detailsShareFab = findViewById<FloatingActionButton>(R.id.details_share_fab)
        val snackbarShare = Snackbar.make(mainViewGroup, "Share", Snackbar.LENGTH_SHORT)
        val detailsLikeFab = findViewById<FloatingActionButton>(R.id.details_like_fab)
        val snackbarLike = Snackbar.make(mainViewGroup, "Like", Snackbar.LENGTH_SHORT)
        val detailsWatchLaterFab = findViewById<FloatingActionButton>(R.id.details_watch_later_fab)
        val snackbarWatchLater = Snackbar.make(mainViewGroup, "Watch later", Snackbar.LENGTH_SHORT)

        detailsShareFab.setOnClickListener {
            snackbarShare
                .setTextColor(Color.WHITE)
                .setBackgroundTint(Color.DKGRAY)
                .show()
        }
        detailsLikeFab.setOnClickListener {
            snackbarLike
                .setTextColor(Color.WHITE)
                .setBackgroundTint(Color.DKGRAY)
                .show()
        }
        detailsWatchLaterFab.setOnClickListener {
            snackbarWatchLater
                .setTextColor(Color.WHITE)
                .setBackgroundTint(Color.DKGRAY)
                .show()
        }

    }

    fun loadMovie(movie: Film) {
        val detailsToolbarThing = findViewById<Toolbar>(R.id.details_toolbar)
        val detailsPosterThing = findViewById<ImageView>(R.id.details_poster)
        val detailsDescription = findViewById<TextView>(R.id.details_description)
        // We set the title
        detailsToolbarThing.title = movie.title
        // We set the image
        detailsPosterThing.setImageResource(movie.poster)
        // We set the description
        detailsDescription.text = movie.description
    }
}