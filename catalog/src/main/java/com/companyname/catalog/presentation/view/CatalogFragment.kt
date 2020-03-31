package com.companyname.catalog.presentation.view

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.companyname.catalog.R
import com.companyname.catalog.presentation.CatalogViewModel
import com.companyname.common.entities.BaseMovie
import com.companyname.common.entities.getNotification
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.catalog_fragment.*

class CatalogFragment: Fragment() {
    private lateinit var catalogViewModel: CatalogViewModel
    private lateinit var moviesAdapter: MoviesAdapter
    private var menuCoversSwitch: MenuItem? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.catalog_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesAdapter = MoviesAdapter(
            requireContext(),
            object : LoadMoreListener {
                override fun loadMore() {
                    catalogViewModel.loadMoreMovies()
                }
            },
            {
                val bundle = bundleOf(BaseMovie::javaClass.name to it)
                findNavController().navigate(R.id.action_catalogFragment_to_movieCard, bundle)
            },
            3
        )
        (rvMovies.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        rvMovies.adapter = moviesAdapter
        srMovies.setOnRefreshListener {
            catalogViewModel.refreshData()
        }
        setToolbar()
    }

    private fun setToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        catalogViewModel = ViewModelProvider(this).get(CatalogViewModel::class.java)
        catalogViewModel.getMovies().observe(viewLifecycleOwner, Observer {
            moviesAdapter.setData(it)
            srMovies.isRefreshing = false
        })
        catalogViewModel.progressBarVisible().observe(viewLifecycleOwner, Observer {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
        catalogViewModel.getErrorOnLoadingData().observe(viewLifecycleOwner, Observer {error ->
            error?.let {
                Snackbar.make(catalogContainer, it.getNotification(requireContext()), Snackbar.LENGTH_SHORT).show();
            }
        })
        catalogViewModel.getAdapterType(requireContext()).observe(viewLifecycleOwner, Observer {
            moviesAdapter.switchRepresentation(it)
        })
        catalogViewModel.showSkeleton().observe(viewLifecycleOwner, Observer {
            if (it) moviesAdapter.showSkeleton()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.catalog_movies_menu, menu)
        menuCoversSwitch = menu.findItem(R.id.menuCoversSwitch)
        menuCoversSwitch?.setOnMenuItemClickListener {
            catalogViewModel.switchCoversClicked(requireContext())
            true
        }
        catalogViewModel.getCoversMenuTitle(requireContext()).observe(viewLifecycleOwner, Observer {
            menuCoversSwitch?.title = requireContext().getString(it)
        })
    }
}