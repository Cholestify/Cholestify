package com.example.cholestifyapp.ui.updateDaily

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cholestifyapp.data.request.AddMealRequest
import com.example.cholestifyapp.data.response.DataItem
import com.example.cholestifyapp.data.response.DataMealFoodHistoryRespnose
import com.example.cholestifyapp.data.response.FoodResponse
import com.example.cholestifyapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateViewModel : ViewModel() {

    private val _dataItem = MutableLiveData<List<DataItem>>()
    val listEvent: LiveData<List<DataItem>> = _dataItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    init {
        fetchFoodList()
    }

    private fun fetchFoodList() {
        _isLoading.value = true
        ApiConfig.getApiService().getAllFood().enqueue(object : Callback<FoodResponse> {
            override fun onResponse(call: Call<FoodResponse>, response: Response<FoodResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _dataItem.value = response.body()?.data.orEmpty()
                } else {
                    _message.value = "Gagal memuat data: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<FoodResponse>, t: Throwable) {
                _isLoading.value = false
                _message.value = "Kesalahan jaringan: ${t.message}"
            }
        })
    }

    fun sendSelectedFoodData(selectedItems: List<AddMealRequest>) {
        _isLoading.value = true
        ApiConfig.getApiService().sendDailyFoodUpdate(selectedItems)
            .enqueue(object : Callback<DataMealFoodHistoryRespnose> {
                override fun onResponse(
                    call: Call<DataMealFoodHistoryRespnose>,
                    response: Response<DataMealFoodHistoryRespnose>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _message.value = "Data berhasil diperbarui"
                    } else {
                        _message.value = "Gagal mengirim data: ${response.message()}"
                    }
                }

                override fun onFailure(call: Call<DataMealFoodHistoryRespnose>, t: Throwable) {
                    _isLoading.value = false
                    _message.value = "Kesalahan jaringan: ${t.message}"
                }
            })
    }
}
