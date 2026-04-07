package com.hrudhaykanth116.composeapp.models

enum class Feature(val key: String) {

    TODO("todo"),
    JOURNAL("journal"),
    AI("ai"),
    WEATHER("weather"),
    WATCHLIST("watchlist"),
    MEDIA("media");

     companion object {

         val map: Map<String, Feature> = entries.associateBy(Feature::key)
         val set: Set<Feature> = entries.toSet()


         fun fromKey(key: String) = map[key]
     }

}