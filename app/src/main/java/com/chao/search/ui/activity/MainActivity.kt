package com.chao.search.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.chao.search.R
import com.chao.search.databinding.ActivityMainBinding
import com.chao.search.ui.fragment.MainFragment
import com.chao.search.ui.fragment.MainViewModel
import com.chao.search.util.DataState
import com.chao.search.util.DataStateListener
import com.chao.search.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), DataStateListener {

    lateinit var viewModel: MainViewModel

    private val binding by viewBinding(ActivityMainBinding::inflate)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel :: class.java)


        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragmentContainer, MainFragment.newInstance())
                    .commit()
        }
    }

    override fun onDataStateChange(dataState: DataState<*>?) {
        handleDataState(dataState)
    }

    private fun handleDataState(dataState: DataState<*>?) {
        dataState?.let {
            //handle loading on ui
            showProgressBar(it.loading)

            //handle message on ui
            it.message?.let { event ->
                event.getContentIfNotHandled()?.let {
                    showToast(it)
                }
            }
        }
    }

    private fun showProgressBar(isVisible: Boolean) {
        binding.progressBar.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
