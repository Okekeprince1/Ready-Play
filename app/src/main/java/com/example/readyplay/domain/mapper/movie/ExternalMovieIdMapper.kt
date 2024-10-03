package com.example.readyplay.domain.mapper.movie

import com.example.readyplay.data.model.ExternalIDDto
import com.example.readyplay.domain.mapper.BaseMapper
import com.example.readyplay.domain.model.ExternalID

class ExternalMovieIdMapper : BaseMapper<ExternalIDDto, ExternalID> {
    override fun mapFrom(to: ExternalID): ExternalIDDto {
        TODO("Not yet implemented")
    }

    override fun mapTo(from: ExternalIDDto): ExternalID =
        ExternalID(
            id = from.imdb_id,
        )
}
