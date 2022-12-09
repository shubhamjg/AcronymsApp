package com.shubham.acronymsapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.shubham.acronymsapp.R
import com.shubham.acronymsapp.repository.AcronymRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.verify
import java.io.EOFException

@RunWith(MockitoJUnitRunner::class)
class AcronymViewModelExceptionTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var errorObserver: Observer<Int>

    @Mock
    lateinit var repository: AcronymRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test(expected = RuntimeException::class)
    fun `test getAcronyms() suffers exception`() {
        runTest(StandardTestDispatcher()) {

            doThrow(RuntimeException("No Internet.")).`when`(repository.getAcronyms("FIFA"))

            val viewModel = AcronymViewModel(repository)

            viewModel.error.observeForever(errorObserver)

            viewModel.getAcronyms("FIFA")
            val expectedResult = R.string.network_failure
            verify(repository).getAcronyms("FIFA")

            verify(errorObserver).onChanged(expectedResult)

            viewModel.error.removeObserver(errorObserver)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test(expected = RuntimeException::class)
    fun `test getAcronyms() suffers EOFException`() {
        runTest(StandardTestDispatcher()) {

            doThrow(EOFException()).`when`(repository.getAcronyms("FIFA"))

            val viewModel = AcronymViewModel(repository)

            viewModel.error.observeForever(errorObserver)

            viewModel.getAcronyms("FIFA")
            val expectedResult = R.string.no_results
            verify(repository).getAcronyms("FIFA")

            verify(errorObserver).onChanged(expectedResult)

            viewModel.error.removeObserver(errorObserver)
        }
    }


}