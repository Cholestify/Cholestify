package com.example.cholestifyapp.ui.updateDaily

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cholestifyapp.data.response.DataItem
import com.example.cholestifyapp.data.response.ResponseFood
import com.example.cholestifyapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateViewModel: ViewModel() {
    private val _dataItem =  MutableLiveData<List<DataItem>>()
    val listEvent: LiveData<List<DataItem>> = _dataItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _update = MutableLiveData<List<DataItem>>()
    val update: LiveData<List<DataItem>> = _update

    private val _error = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _error

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    companion object {
        private const val TAG = "UpdateDood"
        private const val ListFood = 1 // ID khusus untuk mendeteksi
    }


    init {
        ListUpdateFood()
    }

    private fun ListUpdateFood() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFood(ListFood)
        client.enqueue(object : Callback<ResponseFood> {
            override fun onResponse(
                call: Call<ResponseFood>,
                response: Response<ResponseFood>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // Menyimpan daftar event ke LiveData
                        _dataItem.value = response.body()?.data
                        Log.w(TAG, "onResponse: ${_dataItem.value}")
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseFood>, t: Throwable) {
                _isLoading.value = false
                // Menangani kegagalan dengan mencatat log error
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}














