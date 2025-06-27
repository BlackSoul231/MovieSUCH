package us.deveron.moviesuch

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import us.deveron.moviesuch.databinding.ActivityMainBinding


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val knopka = findViewById<Button>(R.id.button5)
        knopka.setOnClickListener {
            Toast.makeText(this, "Perdin!", Toast.LENGTH_SHORT).show()
        }
    }

    fun onClickToast(view: View) {
        Toast.makeText(this, "Chin!", Toast.LENGTH_SHORT).show()
    }
}