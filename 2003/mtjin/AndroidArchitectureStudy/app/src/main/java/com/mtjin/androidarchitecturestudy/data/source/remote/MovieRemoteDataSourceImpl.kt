package com.mtjin.androidarchitecturestudy.data.source.remote

import android.util.Log
import com.mtjin.androidarchitecturestudy.api.ApiClient
import com.mtjin.androidarchitecturestudy.api.ApiInterface
import com.mtjin.androidarchitecturestudy.data.Movie
import com.mtjin.androidarchitecturestudy.data.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRemoteDataSourceImpl : MovieRemoteDataSource {
    private lateinit var movieCall: Call<MovieResponse>
    private val apiInterface: ApiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)
    override fun getSearchMovies(
        query: String,
        success: (List<Movie>) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        movieCall = apiInterface.getSearchMovie(query)
        movieCall.enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d(TAG, "Remote onFailure")
                fail(t)
            }

            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                with(response) {
                    val body = body()
                    if (isSuccessful && body != null) {
                        Log.d(TAG, "Remote onResponse success")
                        success(body.movies)
                    } else {
                        Log.d(TAG, "Remote onResponse fail")
                        fail(Throwable(response.code().toString()))
                    }
                }
            }
        })
    }

    companion object {
        const val TAG = "MovieRemoteDataSource"
    }
}