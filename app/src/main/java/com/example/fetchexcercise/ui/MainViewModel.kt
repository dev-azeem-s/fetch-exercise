package com.example.fetchexcercise.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchexcercise.data.HiringItem
import com.example.fetchexcercise.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _hiringItems = MutableLiveData<Map<Int, List<HiringItem>>>()
    val hiringItems: LiveData<Map<Int, List<HiringItem>>>
        get() = _hiringItems

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private var itemList: List<HiringItem> = emptyList()

    // Property to track sorting type
    var sortByName: Boolean = true

    fun fetchItems() {
        _isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val response: Response<List<HiringItem>> = RetrofitInstance.apiService.getItems().execute()

            if (response.isSuccessful) {
                response.body()?.let { fetchedItems ->
                    itemList = fetchedItems.filter { !it.name.isNullOrEmpty() } // Filter and store items
                    sortAndPostItems() // Sort and post the initial value
                }
            }
            _isLoading.postValue(false)
        }
    }

    private fun sortAndPostItems() {
        val groupedItems = itemList.sortedWith(compareBy<HiringItem> { it.listId }
            .thenBy {
                if (sortByName) {
                    it.name // Sort by name
                } else {
                    // Extract the numeric part for number sorting
                    val numberPart = it.name?.substringAfter("Item ")?.toIntOrNull() ?: Int.MAX_VALUE
                    numberPart
                }
            }).groupBy { it.listId }

        _hiringItems.postValue(groupedItems)
    }

    fun toggleSorting() {
        sortByName = !sortByName
        sortAndPostItems() // Sort again based on the new setting
    }

}