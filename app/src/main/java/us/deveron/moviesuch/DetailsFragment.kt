package us.deveron.moviesuch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import us.deveron.moviesuch.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // We receive our movie from the bundle.

        loadMovie()
    }


    fun loadMovie() {
        val film = arguments?.get("film") as Film
        // We set the title
        binding.detailsToolbar.title = film.title
        // We set the image
        binding.detailsPoster.setImageResource(film.poster)
        // We set the description
        binding.detailsDescription.text = film.description
    }
}