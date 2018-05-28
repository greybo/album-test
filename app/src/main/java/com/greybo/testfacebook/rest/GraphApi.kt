package com.greybo.testfacebook.rest

import com.google.gson.GsonBuilder
import com.greybo.testfacebook.model.AlbumRasponse
import com.greybo.testfacebook.model.PhotoResponse
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

class GraphApi {

    val BASE_URL = "https://graph.facebook.com/v3.0/"

    fun getApi(): GatewayApi {

        val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setLenient()
                .create()

        val restAdapter = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()

        return restAdapter.create(GatewayApi::class.java)
    }

    interface GatewayApi {

        @GET("{userId}/albums")
        fun getAlbums(
                @Path("userId") userId: String,
                @Query("access_token") accessToken: String,
                @Query("fields") fields: String
        ): Observable<AlbumRasponse>

        @GET("{userId}/photos")
        fun getPhotos(
                @Path("userId") userId: String,
                @Query("access_token") accessToken: String,
                @Query("fields") fields: String,
                @Query("type") type: String
        ): Observable<PhotoResponse>
    }

}