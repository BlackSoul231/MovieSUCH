package us.deveron.moviesuch
import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

// Created layout (film_item.xml) will be passed to layout
class FilmViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
    // Here we bind View from the layout to the variables.
    private val title = itemView.findViewById<TextView>(R.id.title)
    private val poster = itemView.findViewById<ImageView>(R.id.poster)
    private val description = itemView.findViewById<TextView>(R.id.description)

    // In this method we put the data from Film into our Views.
    fun bind(film: Film) {
        // Set the title.
        title.text = film.title
        // Set the poster.
        poster.setImageResource(film.poster)
        // Set the description.
        description.text = film.description
    }
}

class FilmListRecyclerAdapter(private val clickListener: OnMovieClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    // The list of elements for the RV is stored here
    private val items = mutableListOf<Film>()

    // This method should be redetermined to return quantity of elements in the RV list
    override fun getItemCount() = items.size

    // This method binds our ViewHolder and transfers there "inflated" xml of our movie
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FilmViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.film_item, parent, false))
    }

    // This method will implement binding of the fields of the object Film to View from film_item.xml
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FilmViewHolder -> {
                // We call "bind()" method, which we created, and pass the object from our DataBase with position designation there.
                holder.bind(items[position])
                // We process the click on the whole element (we can make it to be processed while clicking on one element) and we call Listener method, which we receive from adapter's constructor.
                val itemContainer = holder.itemView.findViewById<ViewGroup>(R.id.item_container)
                itemContainer.setOnClickListener {
                    clickListener.click(items[position])
                }
            }
        }
    }

    // Method for adding objects to our list
    fun addItems(list: List<Film>) {
        // First, we clear it (if DiffUtils are not realized here)
        items.clear()
        // Then we add
        items.addAll(list)
        // We notify the RV, that the new list has been added and we need to bind everything from the beginning.
        notifyDataSetChanged()
    }

    fun returnItems(): MutableList<Film> {
        return items
    }

    // Interface for click listening
    interface OnMovieClickListener {
        fun click(film: Film)
    }
}

class TopSpacingItemDecoration(private val paddingInDp: Int): RecyclerView.ItemDecoration() {
    private val Int.convertPx: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = paddingInDp.convertPx
        outRect.right = paddingInDp.convertPx
        outRect.left = paddingInDp.convertPx
    }
}