package us.deveron.moviesuch

import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.Nullable
import android.content.Context
import us.deveron.moviesuch.databinding.SubscribeBinding

class SubscribeView(context: Context, @Nullable attributeSet: AttributeSet) : LinearLayout(context,attributeSet) {
    private val editText: EditText
    private val subscribeButton: Button

    init {
        // Inflating the fragment
        LayoutInflater.from(context).inflate(R.layout.subscribe, this)
        // Need to explicitly designate the orientation if it is vertical because the image may be incorrect
        this.orientation = VERTICAL
        // Linking our View from xml (you can address them even without the variables)
        editText = findViewById<EditText>(R.id.et_subscribe)
        subscribeButton = findViewById<Button>(R.id.btn_subscribe)
        // Linking the listener on the button. Now it is sending data from EditText to Toast, in working application it would send the data to server.
        subscribeButton.setOnClickListener {
            Toast.makeText(context, editText.text, Toast.LENGTH_SHORT).show()
        }
    }
}