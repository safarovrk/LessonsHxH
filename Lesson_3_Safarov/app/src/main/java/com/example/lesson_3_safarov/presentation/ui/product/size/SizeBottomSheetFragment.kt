package com.example.lesson_3_safarov.presentation.ui.product.size

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.createViewModelLazy
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lesson_3_safarov.R
import com.example.lesson_3_safarov.databinding.FragmentSizeBottomSheetBinding
import com.example.lesson_3_safarov.presentation.utils.SnackbarEvents
import com.example.lesson_3_safarov.presentation.utils.showStylizedSnackbar
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class SizeBottomSheetFragment : BottomSheetDialogFragment() {

    companion object {
        const val SIZE_KEY = "size_key"
        const val BUNDLE_SIZE_KEY = "bundle_size_key"
    }

    private var _binding: FragmentSizeBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val args: SizeBottomSheetFragmentArgs by navArgs()

    @Inject
    lateinit var adapter: SizeAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSizeBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.sizesRecyclerView.adapter = adapter
        val list = mutableListOf<SizeItem>()
        args.sizes.forEach { size ->
            list.add(
                SizeItem(
                    size,
                    itemClickListener = {
                        setFragmentResult(
                            SIZE_KEY,
                            bundleOf(BUNDLE_SIZE_KEY to size.value)
                        )
                        findNavController().popBackStack()
                    }
                )
            )
        }
        adapter.submitList(list)
    }
}