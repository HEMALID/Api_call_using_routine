package com.example.api_call_using_coroutine


import com.google.gson.annotations.SerializedName

class Albums : ArrayList<Albums.AlbumsItem>(){
    data class AlbumsItem(
        @SerializedName("id")
        val id: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("userId")
        val userId: Int
    )
}