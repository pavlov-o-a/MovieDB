package com.companyname.movie.presentation.view

import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.companyname.shared.Constants
import com.companyname.shared.entities.*
import com.companyname.movie.R
import com.companyname.movie.databinding.MovieFragmentBinding
import com.companyname.movie.di.DaggerMovieComponent
import com.companyname.movie.di.MovieComponent
import com.companyname.movie.presentation.MovieViewModel
import com.companyname.movie.presentation.MovieViewModelFactory
import com.companyname.moviedb.di.getAppComponent
import com.companyname.moviedb.getNotification
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject


class MovieCardFragment : Fragment() {
    private lateinit var viewModel: MovieViewModel
    private var shortScreenMenu: MenuItem? = null
    private lateinit var component: MovieComponent
    private lateinit var viewBind: MovieFragmentBinding

    @Inject
    lateinit var viewModelFactory: MovieViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_fragment, container, false).apply {
            viewBind = MovieFragmentBinding.bind(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component = DaggerMovieComponent.factory().create(requireContext().getAppComponent())
        component.inject(this)
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.movie_card_menu, menu)
        shortScreenMenu = menu.findItem(R.id.simulate_short_screen)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MovieViewModel::class.java)
        viewModel.showProgress().observe(viewLifecycleOwner, {
            viewBind.progress.visibility = if (it) View.VISIBLE else View.GONE
        })
        viewModel.setBaseMovie(arguments?.getSerializable(BaseMovie::javaClass.name) as BaseMovie)
        setToolbar()
        viewModel.getPosterPath().observe(viewLifecycleOwner, {
            setCover(it)
        })
        viewModel.getDescription().observe(viewLifecycleOwner, {
            if (it.isNotBlank()) {
                viewBind.descriptionLabel.visibility = View.VISIBLE
                viewBind.description.visibility = View.VISIBLE
                viewBind.description.text = it
            }
        })
        viewModel.getErrorOnLoading().observe(viewLifecycleOwner, { error ->
            error?.let {
                Snackbar.make(
                    viewBind.cardContainer,
                    it.getNotification(requireContext()),
                    BaseTransientBottomBar.LENGTH_SHORT
                ).show()
            }
        })
        viewModel.getGenres().observe(viewLifecycleOwner, {
            setGenres(it)
        })
        viewModel.getImdb().observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                viewBind.imdbLink.visibility = View.VISIBLE
                viewBind.imdbIcon.visibility = View.VISIBLE
                setImdb(it)
            }
        })
        viewModel.getRating().observe(viewLifecycleOwner, {
            if (it > 0) {
                viewBind.ratingLabel.visibility = View.VISIBLE
                viewBind.rating.visibility = View.VISIBLE
                viewBind.rating.rating = it
            }
        })
        viewModel.getCrew().observe(viewLifecycleOwner, {
            setCrew(it)
        })
        viewModel.getCast().observe(viewLifecycleOwner, {
            setCast(it)
        })
    }

    private fun setToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(viewBind.toolbar)
        viewBind.toolbar.title = viewModel.getToolbarTitle()
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        NavigationUI.setupWithNavController(viewBind.toolbar, findNavController())
    }

    private val coverGlobalListener = object :
        ViewTreeObserver.OnGlobalLayoutListener {
        private lateinit var posterPath: String
        fun setPosterPath(path: String): ViewTreeObserver.OnGlobalLayoutListener {
            posterPath = path
            return this
        }

        override fun onGlobalLayout() {
            viewBind.cover.viewTreeObserver.removeOnGlobalLayoutListener(this)
            setCover(posterPath)
        }
    }

    private val modeListener = object : PosterModeListener {
        override fun modeIsTranslate() {
            shortScreenMenu?.isVisible = true
            shortScreenMenu?.title = getString(R.string.show_screen_simulation)
            shortScreenMenu?.setOnMenuItemClickListener {
                viewBind.shortScreenPadding?.visibility = View.VISIBLE; true
            }
        }

        override fun modeIsScale() {
            shortScreenMenu?.title = getString(R.string.hide_screen_simulation)
            shortScreenMenu?.setOnMenuItemClickListener {
                viewBind.shortScreenPadding?.visibility = View.GONE; true
            }
        }
    }

    private fun setCover(posterPath: String) {
        val poster = viewBind.cover as ImageView
        if (poster is TopCenterOrScaleImageView) {
            poster.setModeListener(modeListener)
        }
        val coverWidth = poster.measuredWidth
        if (coverWidth == 0) {
            poster.viewTreeObserver.removeOnGlobalLayoutListener(coverGlobalListener)
            poster.viewTreeObserver.addOnGlobalLayoutListener(
                coverGlobalListener.setPosterPath(
                    posterPath
                )
            )
        } else {
            val posterSize = Constants.POSTER_SIZES.firstOrNull { it >= coverWidth }
                ?: Constants.POSTER_SIZES.last()
            val imageUrl = Constants.IMAGES_URL + "w$posterSize/" + posterPath
            Glide
                .with(requireContext())
                .load(imageUrl)
                .into(poster)
        }
    }

    private fun setGenres(genres: List<Genre>) {
        if (genres.isNotEmpty()) {
            viewBind.genresFlow.visibility = View.VISIBLE
            viewBind.genresLabel.visibility = View.VISIBLE
            viewBind.genresFlow.removeAllViews()
            for (genre in genres) {
                val chip = Chip(requireContext())
                chip.text = genre.name
                viewBind.genresFlow.addView(chip)
                chip.chipBackgroundColor = ContextCompat.getColorStateList(
                    requireContext(),
                    com.companyname.moviedb.R.color.genre_chip_color_list
                )
                val marginParams = ViewGroup.MarginLayoutParams(chip.layoutParams)
                marginParams.marginEnd = resources.getDimensionPixelSize(R.dimen.chip_margin)
                chip.layoutParams = marginParams
            }
        }
    }

    private fun setImdb(imdbID: String) {
        viewBind.imdbLink.text = imdbID
        viewBind.imdbLink.setOnClickListener {
            val link = Constants.IMDB_URL + imdbID
            val text = getString(R.string.open_imdb, link)
            Snackbar
                .make(viewBind.cardContainer, text, Snackbar.LENGTH_SHORT)
                .setAction("OK") {
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(link)
                    startActivity(i)
                }
                .show()
        }
    }

    private fun setCrew(crew: List<CrewMember>) {
        if (crew.isNotEmpty()) {
            viewBind.crewLabel.visibility = View.VISIBLE
            viewBind.crewLayout.visibility = View.VISIBLE
            viewBind.crewLayout.adapter =
                CreditsAdapter(crew.map { CreditData(it.name, it.job, it.photo) })
            viewBind.crewLayout.addEdgeDecoration()
        }
    }

    private fun setCast(cast: List<Actor>) {
        if (cast.isNotEmpty()) {
            viewBind.castLabel.visibility = View.VISIBLE
            viewBind.castLayout.visibility = View.VISIBLE
            viewBind.castLayout.adapter =
                CreditsAdapter(cast.map { CreditData(it.name, it.character, it.photo) })
            viewBind.castLayout.addEdgeDecoration()
        }
    }

    private fun RecyclerView.addEdgeDecoration() {
        this.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val itemsCount = parent.adapter?.itemCount ?: 0
                val position = parent.getChildAdapterPosition(view)
                val padding =
                    resources.getDimensionPixelSize(com.companyname.moviedb.R.dimen.default_small_margin)
                val margin =
                    resources.getDimensionPixelSize(com.companyname.moviedb.R.dimen.default_margin)
                if (position == 0)
                    outRect.left = margin - padding
                if (position == itemsCount - 1)
                    outRect.right = margin - padding
            }
        })
    }
}