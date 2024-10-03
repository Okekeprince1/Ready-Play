package com.example.readyplay.domain.mapper.movie

import com.example.readyplay.data.model.MovieDetailsDto
import com.example.readyplay.domain.mapper.BaseMapper
import com.example.readyplay.domain.model.MovieDetails

class MovieDetailsMapper : BaseMapper<MovieDetailsDto, MovieDetails> {
    override fun mapFrom(to: MovieDetails): MovieDetailsDto {
        TODO("Not yet implemented")
    }

    override fun mapTo(from: MovieDetailsDto): MovieDetails =
        MovieDetails(
            id = from.id,
            name = from.title,
            image = from.fullImagePath,
            overview = from.overview,
            isAdult = from.adult,
            revenue = from.revenue.toString(),
        )
}
