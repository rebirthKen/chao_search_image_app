package com.chao.search.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.chao.search.R
import com.chao.search.api.model.ImageResult
import com.chao.search.api.ImageService
import com.chao.search.databinding.FragmentMainBinding
import com.chao.search.ui.adapter.ViewModelAdapter
import com.chao.search.ui.state.MainStateEvent
import com.chao.search.util.DataStateListener
import com.chao.search.util.SpaceItemDecoration
import com.chao.search.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {
    companion object {
        const val TAG = "MainActivity"
        const val URL = "URL"
        const val ID = "id"
        const val TITLE = "title"

        fun newInstance(): MainFragment = MainFragment()
    }
    @Inject
    lateinit var imageService: ImageService

    private val adapter = ViewModelAdapter().apply {
        listener = object : ViewModelAdapter.Listener {
            override fun longClickListener(id: String, url: String?, title: String?) {
                onImageLongPress(id, url, title)
                removeObservers()
            }
        }
    }

    private val viewModel: MainViewModel by activityViewModels()

    private val binding by viewBinding(FragmentMainBinding::bind)


    private var dataStateHandler: DataStateListener? = null

    private val space: Int by lazy { requireContext().resources.getDimensionPixelSize(R.dimen.image_space) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        searchImages( "panda")

        binding.searchPhrase.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchImages( binding.searchPhrase.text.toString())
                return@setOnEditorActionListener true
            }
            false
        }

        binding.recyclerView.layoutManager = GridLayoutManager(context, 3)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(SpaceItemDecoration(space, space, space, space))
    }

    override fun onResume() {
        super.onResume()
        subscribeObservers()
    }


    private fun subscribeObservers(){
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->

            // Handle Loading and Message
            dataStateHandler?.onDataStateChange(dataState)

            // handle Data<T>
            dataState.data?.let{ event ->
                event.getContentIfNotHandled()?.let{ mainViewState ->

                    println("DEBUG: DataState: ${mainViewState}")

                    mainViewState.images.let {
                        viewModel.setImages(it)
                    }

                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer {viewState ->

            viewState.images.let{ images ->
               updateImages(images)
            }
        })
    }

    private fun removeObservers() {
        viewModel.dataState.removeObservers(viewLifecycleOwner)
        viewModel.viewState.removeObservers(viewLifecycleOwner)
    }

    private fun updateImages(images: List<ImageResult>) {
        adapter.setViewModels(images)
    }
    
    private fun onImageLongPress(id: String, uri: String?, title: String?) {
        val popUpDialogFragment = requireActivity().supportFragmentManager.findFragmentByTag(TAG)
                as? PopUpDialogFragment ?: PopUpDialogFragment()
        popUpDialogFragment.apply {
            val args = Bundle()
            args.putString(URL, uri)
            args.putString(ID, id)
            args.putString(TITLE, title)
            arguments = args

            listener = object : PopUpDialogFragment.Listener {
                override fun dismiss() {
                    subscribeObservers()
                }
            }
        }
        popUpDialogFragment.show(requireActivity().supportFragmentManager, TAG)
    }

    private fun searchImages(query: String){
        viewModel.setStateEvent(MainStateEvent.SearchImages(query))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            dataStateHandler = context as DataStateListener
        }catch(e: ClassCastException){
            println("$context must implement DataStateListener")
        }

    }
}
