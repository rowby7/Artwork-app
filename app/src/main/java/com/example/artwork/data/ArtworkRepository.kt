package com.example.artwork.data

import com.example.artwork.R
import com.example.artwork.model.Art

object ArtworkRepository {

    val artworks = listOf (

        Art(
            R.string.art_1_artist,
            R.string.art_1_artist_info,
            R.string.art_1_title,
            R.string.art_1_year,
            R.string.art_1_description,
            R.string.art_1_artist_bio,
            R.drawable.art_1_starry_night,
            R.drawable.vincent_van_gogh
        ),

       Art(
           R.string.art_2_artist,
           R.string.art_2_artist_info,
           R.string.art_2_title,
           R.string.art_2_year,
           R.string.art_2_description,
           R.string.art_2_artist_bio,
           R.drawable.art_2_actor_ichimura_buzaemon,
           R.drawable.kunisada
       ),


        Art(
            R.string.art_3_artist,
            R.string.art_3_artist_info,
            R.string.art_3_title,
            R.string.art_3_year,
            R.string.art_3_description,
            R.string.art_3_artist_bio,
            R.drawable.art_3_liggende_akt,
            R.drawable.edvard_munch
        ),


        Art(
            R.string.art_4_artist,
            R.string.art_4_artist_info,
            R.string.art_4_title,
            R.string.art_4_year,
            R.string.art_4_description,
            R.string.art_4_artist_bio,
            R.drawable.art_4_rest_outdoors,
            R.drawable.albert_m_ller
        ),

        Art(
            R.string.art_5_artist,
            R.string.art_5_artist_info,
            R.string.art_5_title,
            R.string.art_5_year,
            R.string.art_5_description,
            R.string.art_5_artist_bio,
            R.drawable.art_5_a_monograph,
            R.drawable.john_gould
        )


    )
}