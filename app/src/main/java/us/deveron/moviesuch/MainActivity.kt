package us.deveron.moviesuch
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import us.deveron.moviesuch.databinding.ActivityMainBinding
import java.util.concurrent.Executors
import kotlin.math.hypot
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var filmsAdapter: FilmListRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_placeholder, StartAppScreenFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.favorites -> {
                    val tag = "favorites"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment?: FavoritesFragment(), tag)
                    Toast.makeText(this, "Избранное", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.watch_later -> {
                    val tag = "watch_later"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment?: WatchLaterFragment(), tag)
                    Toast.makeText(this, "Посмотреть позже", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.selections -> {
                    val tag = "selections"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment?: SelectionsFragment(), tag)
                    Toast.makeText(this, "Подборки", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.home -> {
                    val tag = "home"
                    val fragment = checkFragmentExistence(tag)
                    // In the first parameter, if fragment not found and function returned NULL, then wil elvis we call creation of a new fragment
                    changeFragment(fragment?: HomeFragment(), tag)
                    Toast.makeText(this, "Дом", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> false
            }
        }
    }

    fun addNewMoviesWithDiffUtil(filmsDatabase: MutableList<Film>, adapter: FilmListRecyclerAdapter) {
        val oldList = filmsAdapter.returnItems()
        val newList = filmsDatabase
        val filmDiff = FilmDiff(oldList, newList)
        val diffResult = DiffUtil.calculateDiff(filmDiff)
        filmsAdapter.addItems(filmsDatabase)
        diffResult.dispatchUpdatesTo(filmsAdapter)
    }

    object AnimationHelper {
        // This variable is for the reveal circle to go from menu navigation button
        private const val menuItems = 4
        /* We have 3 params in menu:
        1 - Our rootVie, which is also a container and an object of animation
        2 - Activity, to move execution of new thread to the UI thread.
        3 - Position in navigation menu, for the circle to move from the menu navigation button
         */
        fun performFragmentCircularRevealAnimation(rootView: View, activity: Activity, position: Int) {
            // Creating new thread
            Executors.newSingleThreadExecutor().execute {
                //In endless cycle we check, whether our animated VIEW is "attached" to the screen
                while (true) {
                    // When it is attached, we execute the code
                    if (rootView.isAttachedToWindow) {
                        // Returning to main thread to do animation
                        activity.runOnUiThread {
                            // Super-complex math for figuring out the start of the animation
                            val itemCenter = rootView.width / (menuItems * 2)
                            val step = (itemCenter * 2) * (position - 1) + itemCenter

                            val x: Int = step
                            val y: Int = rootView.y.roundToInt() + rootView.height

                            val startRadius = 0
                            val endRadius = hypot(rootView.width.toDouble(), rootView.height.toDouble())
                            // Creating the animation itself
                            ViewAnimationUtils.createCircularReveal(rootView, x, y, startRadius.toFloat(), endRadius.toFloat()).apply {
                                // Setting time of animation
                                duration = 500
                                // Interpolator for more natural animation
                                interpolator = AccelerateDecelerateInterpolator()
                                // Launch
                                start()
                            }
                            // Setting visibility of our element
                            rootView.visibility = View.VISIBLE
                        }
                        return@execute
                    }
                }
            }
        }

    }

    fun launchDetailsFragment(film: Film) {
        // We create a bundle
        val bundle = Bundle()
        // We put the movie into the bundle
        bundle.putParcelable("film", film)
        // We put the fragment with details into the variable
        val fragment = DetailsFragment()
        // We attach our parcel to the fragment
        fragment.arguments = bundle

        // Here we start the fragment
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, DetailsFragment().apply {
                arguments = bundle
            })
            .addToBackStack("DetailsFragment")
            .commit()
    }

    private fun changeFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment, tag)
            .addToBackStack(null)
            .commit()
    }

    private fun checkFragmentExistence(tag: String): Fragment? = supportFragmentManager.findFragmentByTag(tag)
}