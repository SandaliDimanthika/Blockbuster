package lk.pasindu.movieflicker.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TmdbApiClient {
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    val apiService: TmdbApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TmdbApiService::class.java)
    }
}
