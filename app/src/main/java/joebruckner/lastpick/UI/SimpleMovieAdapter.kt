package joebruckner.lastpick.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import joebruckner.lastpick.R
import joebruckner.lastpick.data.Movie
import java.util.*

class SimpleMovieAdapter(val context: Context): RecyclerView.Adapter<SimpleMovieViewHolder>() {
    var movies = listOf<Movie>()

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleMovieViewHolder? {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_recent, parent, false)
        return SimpleMovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimpleMovieViewHolder, position: Int) {
        holder.title.text = movies[position].title
        Glide.with(context)
                .load(movies[position].fullPosterPath())
                .crossFade()
                .into(holder.poster)
    }

    public fun setNewMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }
}
