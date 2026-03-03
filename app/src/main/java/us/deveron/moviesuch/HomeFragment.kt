package us.deveron.moviesuch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import us.deveron.moviesuch.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var filmsAdapter: FilmListRecyclerAdapter
    val filmsDatabase = mutableListOf(
        Film("Warfare", R.drawable.warfare_poster1, "The movie about American soldiers in Middle East fighting Taliban or something."),
        Film("Civil War", R.drawable.civil_war_poster, "Movie about modern civil war in the USA."),
        Film("Nobody 2", R.drawable.nobody_two_poster, "Movie about ex-killer of the CIA."),
        Film("Napoleon", R.drawable.napoleon_poster, "Movie about the Joker star who is Napoleon."),
        Film("Alien", R.drawable.aliens_movie, "Movie about the aliens"),
        Film("Batman", R.drawable.dark_knight_movie, "Movie about batman."),
        Film("Gladiator", R.drawable.gladiator_movie, "Movie about gladiator"),
        Film("Top Gun", R.drawable.top_gun_movie, "Movie about jet fighter and a pilot"))


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Here we find our RV.
        val mainRecycler = binding.mainRecycler

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

        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                DialogOnLeaveFragment().show(parentFragmentManager, "exit_dialog")
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
