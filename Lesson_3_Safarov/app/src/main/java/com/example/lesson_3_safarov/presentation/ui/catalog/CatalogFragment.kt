package com.example.lesson_3_safarov.presentation.ui.catalog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson_3_safarov.R
import com.example.lesson_3_safarov.data.responsemodel.ResponseStates
import com.example.lesson_3_safarov.databinding.FragmentCatalogBinding
import com.example.lesson_3_safarov.domain.catalog.Product
import com.example.lesson_3_safarov.presentation.exception.getError
import com.example.lesson_3_safarov.presentation.ui.order.PreOrderProduct
import com.example.lesson_3_safarov.presentation.ui.product.ProductFragmentDirections
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class CatalogFragment : Fragment() {

    companion object {
        private const val VIEW_FLIPPER_LOADING = 0
        private const val VIEW_FLIPPER_ERROR = 1
        private const val VIEW_FLIPPER_LIST = 2
    }

    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by createViewModelLazy(
        CatalogViewModel::class,
        { this.viewModelStore },
        factoryProducer = { viewModelFactory })

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    @Inject
    lateinit var adapter: CatalogAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatalogBinding.inflate(inflater)
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.status_bar)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setListeners()
        setStateObserver()
        val recyclerView = binding.catalogRecyclerView

        val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        ContextCompat.getDrawable(requireContext(), R.drawable.products_list_divider)
            ?.let { dividerItemDecoration.setDrawable(it) }

        recyclerView.addItemDecoration(dividerItemDecoration)

        recyclerView.adapter = adapter
    }

    private fun setListeners() {
        binding.errorRefreshButton.setOnClickListener {
            viewModel.onLoadData()
        }
    }

    private fun setStateObserver() {
        viewModel.catalogState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResponseStates.Success -> {
                    val list = mutableListOf<ProductItem>()
                    state.data.forEach { product ->
                        list.add(
                            ProductItem(
                                product,
                                buttonClickListener = {
                                    navigateToOrder(it)
                                },
                                itemClickListener = {
                                    navigateToProduct(it)
                                }
                            )
                        )
                    }
                    adapter.submitList(list)
                    binding.viewFlipper.displayedChild = VIEW_FLIPPER_LIST
                }

                is ResponseStates.Failure -> {
                    binding.unexpectedErrorDescription.text = state.e.getError()
                    binding.viewFlipper.displayedChild = VIEW_FLIPPER_ERROR
                }

                is ResponseStates.Loading -> {
                    binding.viewFlipper.displayedChild = VIEW_FLIPPER_LOADING
                }
            }
        }
    }

    private fun navigateToProduct(product: Product) {
        findNavController().navigate(
            CatalogFragmentDirections.actionCatalogFragmentToProductFragment(
                product.id, product.title
            )
        )
    }

    private fun navigateToOrder(product: Product) {
        findNavController().navigate(
            CatalogFragmentDirections.actionCatalogFragmentToOrderFragment(
                PreOrderProduct(
                    id = product.id,
                    title = product.title,
                    department = product.department,
                    price = product.price,
                    preview = product.preview,
                    size = "M"
                )
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}