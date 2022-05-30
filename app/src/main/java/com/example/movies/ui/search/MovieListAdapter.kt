package com.example.movies.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.database.tables.MovieTileTable
import com.example.movies.databinding.MovieItemBinding
import com.facebook.drawee.view.SimpleDraweeView

class MovieListAdapter :RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    private lateinit var movieList : List<MovieTileTable>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = movieList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = movieList.size

    fun updateList(movies : List<MovieTileTable>) {
        movieList = movies
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: MovieItemBinding): RecyclerView.ViewHolder(binding.root) {
        val imageView: SimpleDraweeView = binding.moviePoster
        val favIcon:ImageButton = binding.favIcon
        val title:TextView = binding.movieName
        val year: TextView = binding.movieYear

        init {
            favIcon.setOnClickListener {
                //TODO
            }

        }

        fun bind(movieTile: MovieTileTable){
            imageView.setImageURI(movieTile.poster)
            title.text = movieTile.title
            title.text = movieTile.year
            if (movieTile.isFav) {
                favIcon.setImageDrawable(favIcon.context.getDrawable(R.drawable.ic_fav_filled))
            } else {
                favIcon.setImageDrawable(favIcon.context.getDrawable(R.drawable.ic_fav_unfilled))

            }
        }
    }
}