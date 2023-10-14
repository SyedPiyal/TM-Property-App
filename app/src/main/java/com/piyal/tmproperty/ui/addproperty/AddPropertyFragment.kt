package com.piyal.tmproperty.ui.addproperty


import com.piyal.tmproperty.databinding.FragmentAddPropertyBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


class AddPropertyFragment : Fragment() {
    private var _binding: FragmentAddPropertyBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentAddPropertyBinding.inflate(inflater, container, false)
        return binding.root
        //return inflater.inflate(R.layout.fragment_add_property, container, false)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

