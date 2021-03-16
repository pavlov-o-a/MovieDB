package com.companyname.movie.presentation

import androidx.lifecycle.*
import com.companyname.shared.entities.*
import com.companyname.movie.di.MovieScope
import com.companyname.movie.logic.Logic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Provider

@MovieScope
class MovieViewModel @Inject constructor(private val logic: Logic): ViewModel() {
    private lateinit var baseMovie: BaseMovie
    private var fullMovie: Movie? = null
    private var credits: Credits? = null
    private val errorOnLoading = MutableLiveData<RepositoryError?>()
    private val cardDescription = MutableLiveData<String>()
    private val posterPath = MutableLiveData<String>()
    private val genres = MutableLiveData<List<Genre>>()
    private val rating = MutableLiveData<Float>()
    private val imdb = MutableLiveData<String>()
    private val showProgress = MutableLiveData(true)
    private val castList = MutableLiveData<List<Actor>>()
    private val crewList = MutableLiveData<List<CrewMember>>()

    fun setBaseMovie(baseMovie: BaseMovie) {
        this.baseMovie = baseMovie
        posterPath.value = baseMovie.posterPath
        loadMovieData()
        loadCredits()
    }

    private fun loadMovieData() {
        if (fullMovie == null) {
            showProgress.value = true
            viewModelScope.launch {
                val repositoryData = withContext(Dispatchers.IO) {
                    logic.getMovie(baseMovie.id)
                }
                val movie = repositoryData.data
                val error = repositoryData.error
                launch(Dispatchers.Main) {
                    showProgress.value = false
                    if (movie != null) {
                        fullMovie = movie
                        setCard()
                    } else {
                        error?.let {
                            errorOnLoading.value = it
                        }
                    }
                }
            }
        } else {
            setCard()
        }
    }

    private fun loadCredits(){
        if (credits == null) {
            viewModelScope.launch {
                val repositoryData = withContext(Dispatchers.IO) {
                    logic.getCredits(baseMovie.id)
                }
                val credits = repositoryData.data
                val error = repositoryData.error
                launch(Dispatchers.Main) {
                    if (credits != null) {
                        this@MovieViewModel.credits = credits
                        setCredits()
                    } else {
                        error?.let {
                            errorOnLoading.value = it
                        }
                    }
                }
            }
        } else {
            setCredits()
        }
    }

    private fun setCard() {
        fullMovie?.let {
            cardDescription.value = it.description
            genres.value = it.genres
            rating.value = it.rating
            imdb.value = it.imdb
        }
    }

    private fun setCredits() {
        credits?.let {
            crewList.value = it.crew
            castList.value = it.cast
        }
    }

    fun getToolbarTitle(): String {
        return baseMovie.title
    }

    fun showProgress(): LiveData<Boolean> = showProgress
    fun getErrorOnLoading(): LiveData<RepositoryError?> = errorOnLoading
    fun getDescription(): LiveData<String> = cardDescription
    fun getPosterPath(): LiveData<String> = posterPath
    fun getGenres(): LiveData<List<Genre>> = genres
    fun getRating(): LiveData<Float> = rating
    fun getImdb(): LiveData<String> = imdb
    fun getCrew(): LiveData<List<CrewMember>> = crewList
    fun getCast(): LiveData<List<Actor>> = castList
}

@MovieScope
class MovieViewModelFactory @Inject constructor(private val viewModel: Provider<MovieViewModel>): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.name == MovieViewModel::class.qualifiedName)
            return viewModel.get() as T
        else
            throw RuntimeException("viewModel is not MovieViewModel - ${modelClass.name}")
    }

}