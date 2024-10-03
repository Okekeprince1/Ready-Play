package com.example.readyplay.domain.mapper.movie

import com.example.readyplay.data.model.MovieDto
import com.example.readyplay.domain.mapper.BaseMapper
import com.example.readyplay.domain.model.Movie

class MovieMapper : BaseMapper<MovieDto, Movie> {
    override fun mapFrom(to: Movie): MovieDto {
        TODO("Not yet implemented")
    }

    override fun mapTo(from: MovieDto): Movie =
        Movie(
            id = from.id.toString(),
            title = from.title,
            image = from.fullImagePath,
            isAdult = from.adult,
        )
}
