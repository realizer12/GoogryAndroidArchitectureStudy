package com.example.aas

import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitManager {
	private val httpLoggingInterceptor by lazy {
		HttpLoggingInterceptor().apply {
			level = HttpLoggingInterceptor.Level.BODY
		}
	}

	private val okHttpClient by lazy {
		OkHttpClient.Builder()
			.addInterceptor(httpLoggingInterceptor)
			.addInterceptor { chain ->
				chain.request()
					.newBuilder()
					.addHeader("X-Naver-Client-Id", "AzUPF2FnW5u8lGgbr61C")
					.addHeader("X-Naver-Client-Secret", "sggbgPBFjF")
					.build()
					.let {
						chain.proceed(it)
					}
			}
			.build()
	}

	private val retrofit by lazy {
		Retrofit.Builder()
			.client(okHttpClient)
			.addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
			.addConverterFactory(GsonConverterFactory.create())
			.baseUrl("https://openapi.naver.com/")
			.build()
	}

	val naverMoviesApi: NaverMoviesApi by lazy { retrofit.create() }
}