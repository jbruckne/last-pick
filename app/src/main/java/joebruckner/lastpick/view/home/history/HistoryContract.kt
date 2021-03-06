package joebruckner.lastpick.view.home.history

import joebruckner.lastpick.model.presentation.Movie
import joebruckner.lastpick.model.presentation.State

class HistoryContract {
    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun getHistory()
    }
    interface View {
        fun showContent(movies: List<Movie>)
        fun showError(errorMessage: String)
        fun showLoading()
        var state: State
    }
}
