package com.camai.archtecherstudy.data.repository

import android.content.Context
import com.camai.archtecherstudy.data.model.Items
import com.camai.archtecherstudy.source.local.MovieLocalDataSourceImpl
import com.camai.archtecherstudy.source.local.room.RecentSearchNameList
import com.camai.archtecherstudy.source.remote.MovieRemoteDataSourceImpl


object MovieRepositoryImpl: MovieRepository {

    override fun getMovieNameSearch(
        keyword: String,
        display: Int,
        start: Int,
        success: (ArrayList<Items>) -> Unit,
        failed: (String) -> Unit
    ) {
        MovieRemoteDataSourceImpl.getSearchMovie(keyword, display, start, success, failed)
    }

    override fun getRecentSearchList(
        namelist: (List<RecentSearchNameList>) -> Unit,
        context: Context
    ) {
        MovieLocalDataSourceImpl.getRecentMovieNameList(namelist, context)
    }


    override fun setMovieNameInsert(keyword: String, context: Context) {
        MovieLocalDataSourceImpl.saveMovieName(keyword, context)
    }

    override fun deteleDb(context: Context) {
        MovieLocalDataSourceImpl.deleteDb(context)
    }

    override fun dbclose() {
        MovieLocalDataSourceImpl.dbclose()
    }

}