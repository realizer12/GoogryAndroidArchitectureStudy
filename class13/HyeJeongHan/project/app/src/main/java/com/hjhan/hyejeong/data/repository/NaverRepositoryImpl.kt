package com.hjhan.hyejeong.data.repository

import com.hjhan.hyejeong.data.model.MovieData
import com.hjhan.hyejeong.data.source.local.NaverLocalDataSourceImpl
import com.hjhan.hyejeong.data.source.remote.NaverRemoteDataSourceImpl

class NaverRepositoryImpl(
    private val naverRemoteDataSourceImpl: NaverRemoteDataSourceImpl,
    private val naverLocalDataSourceImpl: NaverLocalDataSourceImpl
) : NaverRepository {

    override fun getSearchMovies(
        query: String,
        success: (MovieData) -> Unit,
        failed: (String) -> Unit
    ) {
        saveQuery(query)
        naverRemoteDataSourceImpl.getSearchMovies(query, success = success, failed = failed)
    }

    override fun saveQuery(query: String) {
        naverLocalDataSourceImpl.saveQuery(query)
    }

    override fun getQueryList(): List<String> {
        return naverLocalDataSourceImpl.getQueryList()
    }
}