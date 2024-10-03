package com.example.readyplay.domain.mapper.movie

import com.example.readyplay.data.model.MovieDto
import com.example.readyplay.domain.mapper.BaseMapper
import com.example.readyplay.domain.model.MovieDetails

class FindMovieMapper : BaseMapper<MovieDto, MovieDetails> {
    override fun mapFrom(to: MovieDetails): MovieDto {
        TODO("Not yet implemented")
    }

    override fun mapTo(from: MovieDto): MovieDetails =
        MovieDetails(
            id = from.id,
            name = from.title,
            image = from.fullImagePath,
            overview = from.overview,
            isAdult = from.adult,
            revenue = "12000",
        )
}
