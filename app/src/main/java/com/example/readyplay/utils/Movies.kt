package com.example.readyplay.utils

import com.example.readyplay.data.model.Dates
import com.example.readyplay.data.model.Genre
import com.example.readyplay.data.model.MovieDetailsDto
import com.example.readyplay.data.model.MovieDto
import com.example.readyplay.data.model.MovieResponse
import com.example.readyplay.data.model.ProductionCompany
import com.example.readyplay.data.model.ProductionCountry
import com.example.readyplay.data.model.SpokenLanguage
import com.example.readyplay.domain.model.Movie
import com.example.readyplay.domain.model.MovieDetails

fun getMovies() =
    listOf(
        Movie(
            id = "1",
            title = "Inception",
            image = "https://example.com/inception.jpg",
            isAdult = false,
        ),
        Movie(
            id = "2",
            title = "The Dark Knight",
            image = "https://example.com/dark_knight.jpg",
            isAdult = false,
        ),
        Movie(
            id = "3",
            title = "The Matrix",
            image = "https://example.com/matrix.jpg",
            isAdult = true,
        ),
        Movie(
            id = "4",
            title = "Joker",
            image = "https://example.com/joker.jpg",
            isAdult = true,
        ),
        Movie(
            id = "5",
            title = "Avatar",
            image = "https://example.com/avatar.jpg",
            isAdult = false,
        ),
    )

fun getMovieDetails() =
    MovieDetails(
        id = 1,
        name = "Inception",
        image = "https://example.com/inception.jpg",
        overview = "A thief who steals corporate secrets through the use of dream-sharing technology.",
        isAdult = false,
        revenue = "$829.9M",
    )

fun testMovieResponse(): MovieResponse {
    return MovieResponse(
        dates =
            Dates(
                maximum = "2024-12-31",
                minimum = "2024-01-01",
            ),
        page = 1,
        results =
            listOf(
                MovieDto(
                    adult = false,
                    backdropPath = "/path/to/backdrop1.jpg",
                    genreIds = listOf(28, 12, 878),
                    id = 123,
                    originalLanguage = "en",
                    originalTitle = "Inception",
                    overview = "A mind-bending thriller where dream invasion is possible.",
                    popularity = 100.0,
                    posterPath = "/path/to/poster1.jpg",
                    releaseDate = "2024-10-01",
                    title = "Inception",
                    video = false,
                    voteAverage = 8.8,
                    voteCount = 5000,
                ),
                MovieDto(
                    adult = false,
                    backdropPath = "/path/to/backdrop2.jpg",
                    genreIds = listOf(16, 10751, 35),
                    id = 456,
                    originalLanguage = "en",
                    originalTitle = "Toy Story",
                    overview = "A group of toys come to life and embark on adventures.",
                    popularity = 90.0,
                    posterPath = "/path/to/poster2.jpg",
                    releaseDate = "2024-09-25",
                    title = "Toy Story",
                    video = false,
                    voteAverage = 7.9,
                    voteCount = 3000,
                ),
            ),
        totalPages = 10,
        totalResults = 100,
    )
}

fun testMovieDetailsResponse(): MovieDetailsDto {
    return MovieDetailsDto(
        adult = false,
        backdrop_path = "/path/to/backdrop.jpg",
        belongs_to_collection = null,
        budget = 200000000,
        genres =
            listOf(
                Genre(id = 28, name = "Action"),
                Genre(id = 12, name = "Adventure"),
            ),
        homepage = "https://www.moviehomepage.com",
        id = 550,
        imdb_id = "tt0137523",
        origin_country = listOf("US"),
        original_language = "en",
        original_title = "Fight Club",
        overview = "A depressed man forms a secret fight club that evolves into something much more.",
        popularity = 95.5,
        poster_path = "/path/to/poster.jpg",
        production_companies =
            listOf(
                ProductionCompany(
                    id = 1,
                    logo_path = "/path/to/logo.png",
                    name = "20th Century Fox",
                    origin_country = "US",
                ),
            ),
        production_countries =
            listOf(
                ProductionCountry(iso_3166_1 = "US", name = "United States of America"),
            ),
        release_date = "1999-10-15",
        revenue = 100853753,
        runtime = 139,
        spoken_languages =
            listOf(
                SpokenLanguage(english_name = "English", iso_639_1 = "en", name = "English"),
            ),
        status = "Released",
        tagline = "Mischief. Mayhem. Soap.",
        title = "Fight Club",
        video = false,
        vote_average = 8.8f,
        vote_count = 24000,
    )
}
