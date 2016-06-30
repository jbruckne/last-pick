package joebruckner.lastpick.network

import joebruckner.lastpick.data.Movie
import rx.Observable

class BookmarkManager(val jsonFileManager: JsonFileManager) {
    private val bookmarks = mutableSetOf<Movie>()
    private val BOOKMARK_FILE = "bookmarks"

    fun loadBookmarksFromFile() {
        val savedBookmarks = jsonFileManager.load<Array<Movie>>(BOOKMARK_FILE)
        bookmarks.addAll(savedBookmarks?.toList() ?: emptyList())
    }

    fun addBookmark(movie: Movie): Observable<Movie> {
        return Observable.create { sub ->
            bookmarks.add(movie)
            jsonFileManager.save(BOOKMARK_FILE, bookmarks)
            sub.onNext(movie)
            sub.onCompleted()
        }
    }

    fun removeBookmark(movie: Movie): Observable<Movie> {
        return Observable.create { sub ->
            bookmarks.remove(movie)
            jsonFileManager.save(BOOKMARK_FILE, bookmarks)
            sub.onNext(movie)
            sub.onCompleted()
        }
    }

    fun isBookmarked(movie: Movie) = bookmarks.contains(movie)

    fun getBookmarks() = bookmarks
}