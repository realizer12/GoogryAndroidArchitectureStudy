package com.mtjin.androidarchitecturestudy.ui.search

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.mtjin.androidarchitecturestudy.data.search.Movie
import com.mtjin.androidarchitecturestudy.data.search.source.MovieRepository
import retrofit2.HttpException

class MovieSearchViewModel(private val movieRepository: MovieRepository) {
    var query: ObservableField<String> = ObservableField("")
    var movieList: ObservableField<ArrayList<Movie>> = ObservableField()
    var toastMsg: ObservableField<String> = ObservableField()
    var lastPageMsg: ObservableBoolean = ObservableBoolean(false)
    var emptyQueryMsg: ObservableBoolean = ObservableBoolean(false)
    var noResultMsg: ObservableBoolean = ObservableBoolean(false)
    var networkErrorMsg: ObservableBoolean = ObservableBoolean(false)
    var successMsg: ObservableBoolean = ObservableBoolean(false)
    var isLoading: ObservableBoolean = ObservableBoolean(false)
    var scrollRestateState: ObservableBoolean = ObservableBoolean(false)
    var isLoadMore: Boolean = false


    fun requestMovie() {
        isLoadMore = false
        if (query.get().toString().isEmpty()) {
            emptyQueryMsg.set(true)
        } else {
            isLoading.set(true)
            scrollRestateState.set(true)
            movieRepository.getSearchMovies(query.get().toString(),
                success = {
                    if (it.isEmpty()) {
                        noResultMsg.set(true)
                    } else {
                        movieList.get()?.clear()
                        movieList.set(it as ArrayList<Movie>?)
                        successMsg.set(true)
                    }
                    isLoading.set(false)
                },
                fail = {
                    Log.d(TAG, it.toString())
                    when (it) {
                        is HttpException -> networkErrorMsg.set(true)
                        else -> toastMsg.set(it.message.toString())
                    }
                    isLoading.set(false)
                })
        }
    }

    fun requestPagingMovie(offset: Int) {
        isLoadMore = true
        isLoading.set(true)
        movieRepository.getPagingMovies(query.get().toString(), offset,
            success = {
                if (it.isEmpty()) {
                    lastPageMsg.set(true)
                } else {
                    movieList.set(it as ArrayList<Movie>?)
                    successMsg.set(true)
                }
                isLoading.set(false)
            },
            fail = {
                Log.d(TAG, it.toString())
                when (it) {
                    is HttpException -> networkErrorMsg.set(true)
                    else -> toastMsg.set(it.message.toString())
                }
                isLoading.set(false)
            })
    }

    companion object {
        const val TAG = "MovieSearchTAG"
    }
}