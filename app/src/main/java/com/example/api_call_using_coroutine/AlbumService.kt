package com.example.api_call_using_coroutine

import retrofit2.Response
import retrofit2.http.*


interface AlbumService {

    @GET("/albums")
    suspend fun getAlbums(): Response<Albums>

    @GET("/albums")
    suspend fun getSortedAlbums(@Query("userId") userId: Int): Response<Albums>

    @GET("/albums/{id}")
    suspend fun getAlbum(@Path(value = "id") albumId: Int): Response<Albums.AlbumsItem>

    @POST("/albums")
    suspend fun uploadAlbum(@Body album: Albums.AlbumsItem): Response<Albums.AlbumsItem>


}