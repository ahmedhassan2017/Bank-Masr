package banquemisr.challenge05.ui.movies.details.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import banquemisr.challenge05.Models.MovieDetailsResponse
import banquemisr.challenge05.R

class GenreAdapter(private val genres: List<MovieDetailsResponse.Genre>?) : RecyclerView.Adapter<GenreAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        genres?.get(position)?.let { it.name.let { it1 -> holder.bind(it1) } }
    }

    override fun getItemCount(): Int = genres?.size?:0

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val genreTextView: TextView = itemView.findViewById(R.id.genreTextView)

        fun bind(genre: String) {
            genreTextView.text = genre
        }
    }
}
