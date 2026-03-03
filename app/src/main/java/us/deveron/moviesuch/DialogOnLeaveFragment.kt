package us.deveron.moviesuch

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.DialogFragment
import us.deveron.moviesuch.databinding.FragmentDialogOnLeaveBinding

class DialogOnLeaveFragment : DialogFragment() {
    lateinit var binding: FragmentDialogOnLeaveBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDialogOnLeaveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Binding the buttons
        binding.btnYes.setOnClickListener {
            dismiss()
            requireActivity().finishAffinity()
        }
        binding.btnNo.setOnClickListener {
            dismiss()
        }
    }

}