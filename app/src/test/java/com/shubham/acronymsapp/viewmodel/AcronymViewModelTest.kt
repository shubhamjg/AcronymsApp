package com.shubham.acronymsapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.shubham.acronymsapp.Constants
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
class AcronymViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var acronymObserver: Observer<String>

    @Mock
    lateinit var repository: AcronymRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test getAcronyms() returns data successfully`() {
        runTest(StandardTestDispatcher()) {

            val gson = Gson()
            val typeToken = object : TypeToken<List<AcronymResponse>>() {}
            val fakeResponse = Response.success(gson.fromJson(Constants.SUCCESS_RESULT, typeToken))

            Mockito.`when`(repository.getAcronyms("FIFA")).thenReturn(fakeResponse)

            val viewModel = AcronymViewModel(repository)

            viewModel.acronym.observeForever(acronymObserver)

            viewModel.getAcronyms("FIFA")
            val expectedResult = "F\u00e9d\u00e9ration Internationale de Football Association\nflow cytometric indirect immunofluorescence assay\n"
            verify(repository).getAcronyms("FIFA")

            verify(acronymObserver).onChanged(expectedResult)

            viewModel.acronym.removeObserver(acronymObserver)
        }
    }
}