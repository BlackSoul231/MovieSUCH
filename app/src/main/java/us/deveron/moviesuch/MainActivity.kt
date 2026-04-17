package us.deveron.moviesuch
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import us.deveron.moviesuch.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var filmsAdapter: FilmListRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_placeholder, HomeFragment())
            .addToBackStack(null)
            .commit()

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.favorites -> {
                    Toast.makeText(this, "Избранное", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.watch_later -> {
                    Toast.makeText(this, "Посмотреть позже", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.selections -> {
                    Toast.makeText(this, "Подборки", Toast.LENGTH_SHORT).show()
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
}