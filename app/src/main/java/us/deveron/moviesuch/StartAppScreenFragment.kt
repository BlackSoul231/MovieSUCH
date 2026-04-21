package us.deveron.moviesuch

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.airbnb.lottie.LottieAnimationView
import us.deveron.moviesuch.R.raw.lottie_main_menu_loading

class StartAppScreenFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_start_app_screen, container, false)
        val animationView = view.findViewById<LottieAnimationView>(R.id.lottie_anim)

        view.postDelayed({
            navigateToMain()}, 3000)

        animationView.addAnimatorListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                navigateToMain()
            }
        })

        return view

    }

    private fun navigateToMain() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_placeholder, HomeFragment())
            .commit()
    }
}

