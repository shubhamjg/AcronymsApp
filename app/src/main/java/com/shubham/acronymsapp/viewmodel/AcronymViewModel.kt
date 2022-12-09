package com.shubham.acronymsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shubham.acronymsapp.R
import com.shubham.acronymsapp.repository.AcronymRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import java.io.EOFException

class AcronymViewModel(private val repository: AcronymRepository) : ViewModel() {

    val acronym = MutableLiveData("")
    val error = MutableLiveData(0)

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        if (throwable is EOFException) {
            error.postValue(R.string.no_results)
        } else {
            acronym.postValue("")
            error.postValue(R.string.network_failure)
        }
    }

    fun getAcronyms(sF: String) {
        viewModelScope.launch(IO + exceptionHandler) {
            val response = repository.getAcronyms(sF)

            if (!response.isSuccessful) {
                error.postValue(R.string.network_failure)
                return@launch
            }

            var strs = ""

            response.body()?.forEach {
                it.lfs.forEach { longForm ->
                    strs = strs.plus(longForm.lf.toString().plus("\n"))
                }
            }

            if (strs == "") {
                error.postValue(R.string.no_results)
            } else {
                error.postValue(R.string.no_error)
            }

            acronym.postValue(strs)

        }
    }
}