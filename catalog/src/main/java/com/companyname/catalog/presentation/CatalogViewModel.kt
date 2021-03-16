package com.companyname.catalog.presentation

import android.content.Context
import androidx.lifecycle.*
import com.companyname.catalog.R
import com.companyname.catalog.logic.Logic
import com.companyname.catalog.misc.CatalogSettings
import com.companyname.catalog.presentation.view.adapter.AdapterType
import com.companyname.catalog.presentation.view.adapter.generateAdapterType
import com.companyname.common.entities.BaseMovie
import com.companyname.common.entities.RepositoryError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Provider

class CatalogViewModel @Inject constructor(private val logic: Logic): ViewModel() {
    private var currentPage = 1
    private var isDataLoading = false
    private var moviesData: MutableLiveData<List<BaseMovie>>? = null
    private val progressBarIsVisible: MutableLiveData<Boolean> = MutableLiveData(false)
    private val errorOnLoadingData: MutableLiveData<RepositoryError?> = MutableLiveData(null)
    private val menuCoversTitle: MutableLiveData<Int> = MutableLiveData(-1)
    private val adapterType: MutableLiveData<AdapterType> = MutableLiveData(AdapterType.FULL)
    private val showSkeleton: MutableLiveData<Boolean> = MutableLiveData(false)
    private var isLastPage = false

    fun getMovies(): LiveData<List<BaseMovie>> {
        if (moviesData == null || moviesData?.value == null){
            moviesData = MutableLiveData()
            showSkeleton.value = true
            loadMoreMovies()
        }
        return moviesData!!
    }

    fun progressBarVisible(): LiveData<Boolean> = progressBarIsVisible

    fun getErrorOnLoadingData(): LiveData<RepositoryError?> = errorOnLoadingData

    fun loadMoreMovies() {
        if (isDataLoading || isLastPage) return
        isDataLoading = true
        progressBarIsVisible.value = true && !(showSkeleton.value?:false)
        viewModelScope.launch {
            val nextMovies = withContext(Dispatchers.IO) { logic.getMovies(currentPage, true) }
            val moviesPage = nextMovies.data
            if (moviesPage != null && !moviesPage.data.isNullOrEmpty()) {
                showSkeleton.value = false
                val movies = mutableListOf<BaseMovie>()
                movies.addAll(moviesData?.value ?: listOf())
                movies.addAll(moviesPage.data)
                moviesData?.value = movies
                if (moviesPage.page == moviesPage.allPages)
                    isLastPage = true
            } else {
                nextMovies.error?.let {
                    errorOnLoadingData.value = it
                }
            }
            isDataLoading = false
            progressBarIsVisible.value = false
            currentPage++
        }
    }

    fun refreshData(){
        if (isDataLoading) return
        currentPage = 1
        moviesData?.value = listOf()
        showSkeleton.value = true
        loadMoreMovies()
        isLastPage = false
    }

    fun switchCoversClicked(context: Context) {
        CatalogSettings.setShowImages(context, !CatalogSettings.shouldShowImages(context))
        updateMenuCoversTitle(context)
        updateAdapterType(context)
    }

    fun getCoversMenuTitle(context: Context): LiveData<Int>{
        updateMenuCoversTitle(context)
        return menuCoversTitle
    }

    fun getAdapterType(context: Context): LiveData<AdapterType> {
        updateAdapterType(context)
        return adapterType
    }

    fun showSkeleton(): LiveData<Boolean> {
        return showSkeleton
    }

    private fun updateMenuCoversTitle(context: Context){
        val title = if (CatalogSettings.shouldShowImages(context))
            R.string.show_covers
        else
            R.string.hide_covers
        menuCoversTitle.value = title
    }

    private fun updateAdapterType(context: Context){
        adapterType.value = generateAdapterType(CatalogSettings.shouldShowImages(context))
    }
}

class CatalogViewModelFactory @Inject constructor(private val viewModel: Provider<CatalogViewModel>): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.name == CatalogViewModel::class.qualifiedName){
            return viewModel.get() as T
        } else {
            throw RuntimeException("unsupported view model: ${modelClass.canonicalName}")
        }
    }
}