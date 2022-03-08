package com.example.api_call_using_coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.example.api_call_using_coroutine.databinding.ActivityMainBinding
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var retService: AlbumService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        retService = RetrofitInstance
            .getRetrofitInstance()
            .create(AlbumService::class.java)
        getRequestWithQueryParameters()

    }


    private fun getRequestWithQueryParameters() {

        val responseLiveData: LiveData<Response<Albums>> = liveData {
            val response = retService.getSortedAlbums(1)
            emit(response)
        }

        responseLiveData.observe(this, Observer {
            val albumsList = it.body()?.listIterator()
            if (albumsList != null) {
                while (albumsList.hasNext()) {
                    val albumsItem = albumsList.next()
                    val result = " " + "Album Title : ${albumsItem.title}" + "\n" +
                            " " + "Album id : ${albumsItem.id}" + "\n" +
                            " " + "User id : ${albumsItem.userId}" + "\n\n\n"
                    binding.textView.append(result)
                }
            }
        })
    }


    private fun getRequestWithPathParameters() {
        val pathResponse: LiveData<Response<Albums.AlbumsItem>> = liveData {
            val response = retService.getAlbum(3)
            emit(response)

        }

        pathResponse.observe(this, Observer {
            val title = it.body()?.title
            Toast.makeText(applicationContext, title, Toast.LENGTH_LONG).show()
        })
    }

    private fun uploadAlbum() {
        val album = Albums.AlbumsItem(0, "My title", 3)
        val postResponse: LiveData<Response<Albums.AlbumsItem>> = liveData {
            val response = retService.uploadAlbum(album)
            emit(response)
        }
        postResponse.observe(this, Observer {
            val receivedAlbumsItem = it.body()
            val result = " " + "Album Title : ${receivedAlbumsItem?.title}" + "\n" +
                    " " + "Album id : ${receivedAlbumsItem?.id}" + "\n" +
                    " " + "User id : ${receivedAlbumsItem?.userId}" + "\n\n\n"
            binding.textView.text = result
        })

    }
}