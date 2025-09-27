package us.deveron.moviesuch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import us.deveron.moviesuch.databinding.TestFirstvideoBinding
import java.util.ArrayList


class WierdActivity : ComponentActivity() {
    private lateinit var binding: TestFirstvideoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TestFirstvideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = MyAdapter(arrayListOf())
        recyclerView.adapter = adapter
        var itemIndex = binding.itemIndex

        fun getIndex() = itemIndex.text.toString().toInt()

        fun updateData(newList: ArrayList<Product>) {
            val oldList = adapter.data
            val productDiff = ProductDiff(oldList, newList)
            val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(productDiff)
            return diffResult
        }

        binding.buttonAdd.setOnClickListener {
            adapter.data.add(getIndex(), Product(adapter.data.size, R.drawable.rocketa, "Rocket", "Bullshit words, not even worth reading..."))
            adapter.notifyItemInserted(getIndex())
        }

        binding.buttonChange.setOnClickListener {

        }
    }
}