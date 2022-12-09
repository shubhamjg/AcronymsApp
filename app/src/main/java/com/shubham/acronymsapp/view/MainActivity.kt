package com.shubham.acronymsapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.DataBindingUtil
import com.shubham.acronymsapp.R
import com.shubham.acronymsapp.databinding.ActivityMainBinding
import com.shubham.acronymsapp.network.AcronymHelper
import com.shubham.acronymsapp.network.NetworkApi
import com.shubham.acronymsapp.repository.AcronymRepository
import com.shubham.acronymsapp.viewmodel.AcronymViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        val api = AcronymHelper.getRetrofitInstance().create(NetworkApi::class.java)
        val acronymRepository = AcronymRepository(api)
        val viewModel = AcronymViewModel(acronymRepository)

        binding.acronymViewModel = viewModel

        binding.searchAcronym.addTextChangedListener(object :TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.getAcronyms(s.toString())
            }
        })
    }
}