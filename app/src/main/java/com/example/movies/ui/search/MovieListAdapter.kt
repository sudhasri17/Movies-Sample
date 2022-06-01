package com.example.movies.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.database.tables.MovieTileTable
import com.example.movies.databinding.MovieItemBinding
import com.example.movies.ui.fav.FavoriteFragmentDirections
import com.facebook.drawee.view.SimpleDraweeView

class MovieListAdapter(val showFavIcon: Boolean = true,
                       val movieTileClickListener: MovieTileClickListener,
                       val favClickListener: FavClickListener? = null
                       ) :RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    private var movieList: List<MovieTileTable> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.movie_item,
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

    fun updateList(movies: List<MovieTileTable>) {
        movieList = movies
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val imageView: SimpleDraweeView = binding.moviePoster
        private val favIcon: ImageButton = binding.favIcon

        init {
            binding.root.setOnClickListener { view ->
                binding.data?.let {
                    movieTileClickListener.onMovieTileClicked(it.imdbID)
                }
            }
            favIcon.setOnClickListener {
                favClickListener?.let { clickListener ->
                    binding.data?.let { data ->
                        val updatedMovieTileTable = clickListener.onFavIconClicked(data)
                        setFavImage(updatedMovieTileTable.isFav)
                        binding.data = updatedMovieTileTable
                    }
                }
            }
        }

        fun bind(movieTile: MovieTileTable) {
            imageView.setImageURI(movieTile.poster)
            favIcon.isGone = !showFavIcon
            binding.data = movieTile
            binding.executePendingBindings()
            if (showFavIcon) {
                setFavImage(movieTile.isFav)
            }
        }

        private fun setFavImage(isFav: Boolean) {
            val favDrawable = if (isFav) {
                R.drawable.ic_fav_filled
            } else {
                R.drawable.ic_fav_unfilled
            }
            favIcon.setImageDrawable(
                AppCompatResources.getDrawable(
                    favIcon.context,
                    favDrawable
                )
            )
        }
    }

    fun interface MovieTileClickListener {
        fun onMovieTileClicked(movieId: String)
    }

    fun interface FavClickListener{
        fun onFavIconClicked(movieTileTable: MovieTileTable):MovieTileTable
    }
}