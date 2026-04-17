package us.deveron.moviesuch

import android.os.Bundle
import android.transition.Scene
import android.transition.Slide
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class HomeFragment : Fragment() {
    lateinit var filmsAdapter: FilmListRecyclerAdapter
    var started = 0
    val filmsDatabase = mutableListOf(
        Film("Warfare", R.drawable.warfare_poster1, "The movie about American soldiers in Middle East fighting Taliban or something."),
        Film("Civil War", R.drawable.civil_war_poster, "Movie about modern civil war in the USA."),
        Film("Nobody 2", R.drawable.nobody_two_poster, "Movie about ex-killer of the CIA."),
        Film("Napoleon", R.drawable.napoleon_poster, "Movie about the Joker star who is Napoleon."),
        Film("Alien", R.drawable.aliens_movie, "Movie about the aliens"),
        Film("Batman", R.drawable.dark_knight_movie, "Movie about batman."),
        Film("Gladiator", R.drawable.gladiator_movie, "Movie about gladiator"),
        Film("Top Gun", R.drawable.top_gun_movie, "Movie about jet fighter and a pilot"))

    init {
        exitTransition = Slide(Gravity.START).apply { duration = 800; mode = Slide.MODE_OUT }
        reenterTransition = Slide(Gravity.START).apply { duration = 800 }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rootView = view.findViewById<ConstraintLayout>(R.id.home_fragment_root)
        val scene = Scene.getSceneForLayout(rootView, R.layout.merge_home_screen_content, requireContext())
        val searchSlide = Slide(Gravity.TOP).addTarget(R.id.search_bar)
        val recyclerSlide = Slide(Gravity.BOTTOM).addTarget(R.id.main_recycler)
        val customTransition = TransitionSet().apply {
            duration = 500
            addTransition(recyclerSlide)
            addTransition(searchSlide)
        }

        if (started == 0) {
            TransitionManager.go(scene, customTransition)
        }

        val searchBar = requireActivity().findViewById<SearchView>(R.id.search_bar)
        searchBar.setOnClickListener { // Adding the option of clicking on the whole search field
            searchBar.isIconified = false
        }
        // Here we find our RV.
        val mainRecycler = requireActivity().findViewById<RecyclerView>(R.id.main_recycler)

        mainRecycler.apply {
            // We initialize our adapter into constructor by passing anonymous interface
            filmsAdapter = FilmListRecyclerAdapter(object: FilmListRecyclerAdapter.OnMovieClickListener {
                override fun click(film: Film) {
                    (requireActivity() as MainActivity).launchDetailsFragment(film)
                }
            })

            // We assign Adapter
            adapter = filmsAdapter
            // We assign Layout Manager
            layoutManager = LinearLayoutManager(requireContext())
            // We apply decorator for the margin
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)
        }
        // We put our DataBase into our RecyclerView (WITH OLD WAY AND A NEW WAY)
//        filmsAdapter.addItems(filmsDatabase)
        addNewMoviesWithDiffUtil(filmsDatabase, filmsAdapter)

        // Action on pressing BACK button
        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                DialogOnLeaveFragment().show(parentFragmentManager, "exit_dialog")
            }
        })

        // Connecting the listener of changes for text input to search bar.
        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            // This method recycles SEARCH button on the soft-keyboard
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            // This method recycles every text change
            override fun onQueryTextChange(newText: String?): Boolean {
                // If input is empty, we put the whole DataBase
                if (newText.isNullOrEmpty()) {
                    filmsAdapter.addItems(filmsDatabase)
                    return true
                }
                // FIltering the list for suitable matches
                val result = filmsDatabase.filter {
                    // For everything to work properly, we need tu put both name of the move and the query to the lower case
                    it.title.lowercase(Locale.getDefault()).contains(newText.lowercase(Locale.getDefault()))
                }
                // Adding to the adapter
                filmsAdapter.addItems(result)
                return true
            }
        })
    }

    fun addNewMoviesWithDiffUtil(filmsDatabase: MutableList<Film>, adapter: FilmListRecyclerAdapter) {
        val oldList = adapter.returnItems()
        val newList = filmsDatabase
        val filmDiff = FilmDiff(oldList, newList)
        val diffResult = DiffUtil.calculateDiff(filmDiff)
        adapter.addItems(filmsDatabase)
        diffResult.dispatchUpdatesTo(adapter)
    }
}
