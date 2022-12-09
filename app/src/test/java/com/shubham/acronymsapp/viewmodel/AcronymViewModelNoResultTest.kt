package com.shubham.acronymsapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.shubham.acronymsapp.Constants
import com.shubham.acronymsapp.R
import com.shubham.acronymsapp.model.AcronymResponse
import com.shubham.acronymsapp.repository.AcronymRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class AcronymViewModelNoResultTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var errorObserver: Observer<Int>

    @Mock
    lateinit var repository: AcronymRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test getAcronyms() returns empty data`() {
        runTest(StandardTestDispatcher()) {

            val gson = Gson()
            val typeToken = object : TypeToken<List<AcronymResponse>>() {}
            val fakeResponse = Response.success(gson.fromJson("[]", typeToken))

            Mockito.`when`(repository.getAcronyms("XDER")).thenReturn(fakeResponse)

            val viewModel = AcronymViewModel(repository)

            viewModel.error.observeForever(errorObserver)

            viewModel.getAcronyms("XDER")
            val expectedResult = R.string.no_results
            verify(repository).getAcronyms("XDER")

            verify(errorObserver).onChanged(expectedResult)

            viewModel.error.removeObserver(errorObserver)
        }
    }
}