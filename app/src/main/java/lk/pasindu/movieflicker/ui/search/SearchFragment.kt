package lk.pasindu.movieflicker.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import lk.pasindu.movieflicker.R
import lk.pasindu.movieflicker.databinding.FragmentSearchBinding
import lk.pasindu.movieflicker.ui.adapter.SearchAdapter

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var adapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = SearchAdapter { movie ->
            val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(movie)
            findNavController().navigate(action)
        }

        binding.searchView.setIconifiedByDefault(false)
        binding.searchView.isIconified = false
        binding.searchView.requestFocus()

        binding.recyclerViewSearchResults.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewSearchResults.adapter = adapter

        // Hook SearchView input to ViewModel
        val searchEditText =
            binding.searchView.findViewById<androidx.appcompat.widget.SearchView.SearchAutoComplete>(
                androidx.appcompat.R.id.search_src_text
            )
        searchEditText.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.purple_chip_text))
        searchEditText.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_accent))

        searchEditText.doAfterTextChanged {
            viewModel.search(it.toString())
        }

        // Observe result changes
        viewModel.results.observe(viewLifecycleOwner) { results ->
            adapter.submitList(results)
        }
    }

    
}
