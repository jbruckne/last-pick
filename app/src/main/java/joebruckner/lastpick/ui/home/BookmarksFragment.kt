package joebruckner.lastpick.ui.home

import android.content.Intent
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.google.gson.Gson
import joebruckner.lastpick.LastPickApp
import joebruckner.lastpick.R
import joebruckner.lastpick.data.Movie
import joebruckner.lastpick.network.BookmarkManager
import joebruckner.lastpick.presenters.BookmarksPresenter
import joebruckner.lastpick.presenters.BookmarksPresenterImpl
import joebruckner.lastpick.ui.common.BaseFragment
import joebruckner.lastpick.ui.movie.MovieActivity
import joebruckner.lastpick.ui.movie.MovieAdapter

class BookmarksFragment : BaseFragment(), BookmarksPresenter.BookmarksView {
    override val layoutId = R.layout.fragment_bookmarks
    override var isLoading = false
    lateinit var presenter: BookmarksPresenter
    lateinit var adapter: MovieAdapter

    override fun showContent(movies: List<Movie>) {
        adapter.setNewMovies(movies)
    }

    override fun showError(errorMessage: String) {
        Log.e("ERROR", errorMessage)
    }

    override fun onStart() {
        super.onStart()

        ViewCompat.setNestedScrollingEnabled(view, false)

        adapter = MovieAdapter(context, R.layout.card_movie_list)
        adapter.addOnItemClickListener { position ->
            val intent = Intent(context, MovieActivity::class.java)
            intent.putExtra("movie", Gson().toJson(adapter.movies[position]))
            startActivity(intent)
        }

        val content = view?.findViewById(R.id.content) as RecyclerView

        content.layoutManager = LinearLayoutManager(activity)
        content.adapter = adapter

        val bookmarkManager = parent.application
                .getSystemService(LastPickApp.BOOKMARKS_MANAGER) as BookmarkManager
        presenter = BookmarksPresenterImpl(bookmarkManager)
        presenter.attachView(this)
        presenter.getBookmarks()
    }
}