package com.hrudhaykanth116.tv.data.models.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetTvCreditsResponse(
    val cast: List<Cast?>? = null,
    val crew: List<Crew?>? = null,
    val id: Int? = null
) {

    /*
    {
      "adult": false,
      "gender": 1,
      "id": 1223786,
      "known_for_department": "Acting",
      "name": "Emilia Clarke",
      "original_name": "Emilia Clarke",
      "popularity": 14.322,
      "profile_path": "/r6i4C3kYrBRzUzZ8JKAHYQ0T0dD.jpg",
      "character": "Daenerys Targaryen",
      "credit_id": "5256c8af19c2956ff60479f6",
      "order": 0
    }
     */
    @JsonClass(generateAdapter = true)
    data class Cast(
        val adult: Boolean? = null,
        val character: String? = null,
        val credit_id: String? = null,
        val gender: Int? = null,
        val id: Int? = null,
        val known_for_department: String? = null,
        val name: String? = null,
        val order: Int? = null,
        val original_name: String? = null,
        val popularity: Double? = null,
        val profile_path: String? = null
    )

    /*
    {
        "adult": false,
        "gender": 2,
        "id": 33316,
        "known_for_department": "Directing",
        "name": "David Nutter",
        "original_name": "David Nutter",
        "popularity": 2.265,
        "profile_path": null,
        "credit_id": "5ceab0ab92514175e8bb5caf",
        "department": "Production",
        "job": "Executive Producer"
    }
    */
    @JsonClass(generateAdapter = true)
    data class Crew(
        val adult: Boolean? = null,
        val credit_id: String? = null,
        val department: String? = null,
        val gender: Int? = null,
        val id: Int? = null,
        val job: String? = null,
        val known_for_department: String? = null,
        val name: String? = null,
        val original_name: String? = null,
        val popularity: Double? = null,
        val profile_path: String? = null
    )
}