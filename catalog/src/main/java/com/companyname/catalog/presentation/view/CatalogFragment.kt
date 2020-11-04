package com.companyname.catalog.presentation.view

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.companyname.catalog.R
import com.companyname.catalog.di.CatalogComponent
import com.companyname.catalog.di.DaggerCatalogComponent
import com.companyname.catalog.presentation.CatalogViewModel
import com.companyname.catalog.presentation.CatalogViewModelFactory
import com.companyname.common.entities.BaseMovie
import com.companyname.common.entities.getNotification
import com.companyname.moviedb.di.getAppComponent
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.catalog_fragment.*
import javax.inject.Inject

class CatalogFragment: Fragment() {
    private lateinit var catalogViewModel: CatalogViewModel
    private lateinit var moviesAdapter: MoviesAdapter
    private var menuCoversSwitch: MenuItem? = null
    private lateinit var component: CatalogComponent
    @Inject
    lateinit var viewModelFactory: CatalogViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        component = DaggerCatalogComponent.factory().create(requireContext().getAppComponent())
        component.inject(this)
        super.onCreate(savedInstanceState)
    }

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
                findNavController().navigate(com.companyname.moviedb.R.id.action_catalogFragment_to_movieCard, bundle)
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
        catalogViewModel = ViewModelProvider(this, viewModelFactory).get(CatalogViewModel::class.java)
        catalogViewModel.getMovies().observe(viewLifecycleOwner, {
            moviesAdapter.setData(it)
            srMovies.isRefreshing = false
        })
        catalogViewModel.progressBarVisible().observe(viewLifecycleOwner, {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
        catalogViewModel.getErrorOnLoadingData().observe(viewLifecycleOwner, {error ->
            error?.let {
                Snackbar.make(catalogContainer, it.getNotification(requireContext()), Snackbar.LENGTH_SHORT).show()
            }
        })
        catalogViewModel.getAdapterType(requireContext()).observe(viewLifecycleOwner, {
            moviesAdapter.switchRepresentation(it)
        })
        catalogViewModel.showSkeleton().observe(viewLifecycleOwner, {
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
        catalogViewModel.getCoversMenuTitle(requireContext()).observe(viewLifecycleOwner, {
            menuCoversSwitch?.title = requireContext().getString(it)
        })
    }
}