package com.example.androidarchitecturestudy.data.datasource

import com.example.androidarchitecturestudy.data.GetMovieInfo

interface RemoteMovieDataSource {

    fun getMovieSearchResult(
        movieName: String,
        onSuccess: (GetMovieInfo.MovieList) -> Unit,
        onFailure: (Throwable) -> Unit
    )
}