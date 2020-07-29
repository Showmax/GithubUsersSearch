package com.showmax.androidcrashcourse.ui.main

import Items
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.showmax.androidcrashcourse.api.ShowmaxApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {

    private val showmaxApi = Retrofit.Builder()
        .baseUrl("https://api.showmax.io/v131.0/android/")
        .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)).build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ShowmaxApi::class.java)

    val data: MutableLiveData<SearchStateData> by lazy {
        MutableLiveData<SearchStateData>()
    }

    fun onLoad(query: String) {
        Log.i(TAG, "onLoad: $query")
        viewModelScope.launch(Dispatchers.IO) {
            data.postValue(SearchStateData.InProgress)
            try {
                val response = showmaxApi.search(query)
                data.postValue(SearchStateData.DataCompleted(response.items))
                Log.i(TAG, "onLoad: DataCompleted")
            } catch (exception: Exception) {
                data.postValue(SearchStateData.Error(exception.message ?: "Error Occurred!"))
                Log.i(TAG, "onLoad: error")
            }
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }

}

sealed class SearchStateData {
    object InProgress : SearchStateData()
    data class DataCompleted(val items: List<Items>) : SearchStateData()
    data class Error(val error: String) : SearchStateData()

}