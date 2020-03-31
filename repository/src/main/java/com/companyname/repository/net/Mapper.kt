package com.companyname.repository.net

import com.companyname.common.entities.*
import com.companyname.repository.net.entities.*
import com.companyname.repository.net.entities.BaseMovieResponse
import com.companyname.repository.net.entities.CreditsResponse
import com.companyname.repository.net.entities.MovieResponse
import com.companyname.repository.net.entities.MoviesTop

internal object Mapper {

    fun MoviesTop?.toMoviesPage(): MoviesPage {
        return MoviesPage(this?.page?:0,
            this?.allPages?:0,
            this?.results?.
                map { it.toBaseMovie() }?.
                filter {
                    it.title.isNotEmpty()
                            && it.year.isNotEmpty()
                            && it.id > 0
                }?: listOf())
    }

    fun BaseMovieResponse?.toBaseMovie(): BaseMovie {
        return BaseMovie(this?.title?:"",
            this?.rating?:0f,
            this?.releaseDate?:"",
            this?.posterPath?:"",
            this?.id?:0)
    }

    fun MovieResponse?.toMovie(): Movie {
        return Movie(this?.title?:"",
            this?.description?:"",
            this?.rating?:0f,
            this?.releaseDate?:"",
            this?.genres?.toGenres() ?: listOf(),
            this?.imdb?:"",
            this?.posterPath?:"",
            this?.id?:0)
    }

    fun List<GenreResponse>.toGenres(): List<Genre> {
        return this.mapNotNull {
            if (it.id != null
                && it.id > 0
                && !it.name.isNullOrEmpty())
                Genre(it.id, it.name)
            else
                null
        }
    }

    fun CreditsResponse.toCredits(): Credits {
        return Credits(
            this.
                cast?.
                map { it.toActor() }?.
                filter { it.name.isNotEmpty() }?: listOf(),
                this.
                crew?.
                map { it.toCrewMember() }?.
                filter { it.name.isNotEmpty() }?: listOf())
    }

    fun ActorResponse.toActor(): Actor {
        return Actor(this.name?:"",
            this.character?:"",
            this.photo?:"")
    }

    fun CrewMemberResponse.toCrewMember(): CrewMember {
        return CrewMember(this.name?:"",
            this.job?:"",
            this.photo?:"")
    }
}
