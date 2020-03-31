package com.companyname.catalog.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.companyname.catalog.R
import com.companyname.common.entities.BaseMovie
import com.companyname.common.entities.RepositoryErrors
import com.companyname.catalog.logic.LogicFactory
import com.companyname.catalog.misc.CatalogSettings
import com.companyname.catalog.presentation.view.FULL_HOLDER
import com.companyname.catalog.presentation.view.LIGHT_HOLDER
import kotlinx.coroutines.*

class CatalogViewModel: ViewModel() {
    private var currentPage = 1
    private var isDataLoading = false
    private var moviesData: MutableLiveData<List<BaseMovie>>? = null
    private val progressBarIsVisible: MutableLiveData<Boolean> = MutableLiveData(false)
    private val errorOnLoadingData: MutableLiveData<RepositoryErrors?> = MutableLiveData(null)
    private val menuCoversTitle: MutableLiveData<Int> = MutableLiveData(-1)
    private val adapterType: MutableLiveData<Int> = MutableLiveData(-1)
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

    fun getErrorOnLoadingData(): LiveData<RepositoryErrors?> = errorOnLoadingData

    fun loadMoreMovies() {
        if (isDataLoading || isLastPage) return
        isDataLoading = true
        progressBarIsVisible.value = true && !(showSkeleton.value?:false)
        CoroutineScope(Dispatchers.IO).launch {
            val nextMovies = LogicFactory.instance().getMovies(currentPage)
            launch(Dispatchers.Main) {
                val moviesPage = nextMovies.data
                if (moviesPage != null && !moviesPage.movies.isNullOrEmpty()){
                    showSkeleton.value = false
                    val movies = mutableListOf<BaseMovie>()
                    movies.addAll(moviesData?.value?: listOf())
                    movies.addAll(moviesPage.movies)
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

    fun getAdapterType(context: Context): LiveData<Int>{
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
        val type = if (CatalogSettings.shouldShowImages(context))
            FULL_HOLDER
        else
            LIGHT_HOLDER
        adapterType.value = type
    }
}