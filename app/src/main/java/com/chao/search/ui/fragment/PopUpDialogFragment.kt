package com.chao.search.ui.fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import coil.load
import com.chao.search.R
import com.chao.search.databinding.FragmentDialogPopupBinding
import com.chao.search.ui.adapter.ViewModelAdapter
import com.chao.search.util.DataStateListener
import com.chao.search.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopUpDialogFragment : AppCompatDialogFragment() {

    interface Listener {
        fun dismiss()
    }

    var listener: Listener? = null
    private val binding by viewBinding(FragmentDialogPopupBinding::bind)
    private var dataStateHandler: DataStateListener? = null
    var id: String? = null
    var title: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.PopupDialogFragment)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        isCancelable = false
        return inflater.inflate(R.layout.fragment_dialog_popup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val url = requireArguments().getString(MainFragment.URL)
        id = requireArguments().getString(MainFragment.ID)
        title = requireArguments().getString(MainFragment.TITLE)
        binding.imageView.load(url)
        binding.titleView.text = title
        binding.backgroundView.setOnClickListener {
            dismiss()
            listener?.dismiss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            dataStateHandler = context as DataStateListener
        }catch(e: ClassCastException){
            println("$context must implement DataStateListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(requireActivity(), theme) {
            override fun onBackPressed() {
                dismiss()
                listener?.dismiss()
            }
        }
    }
}
