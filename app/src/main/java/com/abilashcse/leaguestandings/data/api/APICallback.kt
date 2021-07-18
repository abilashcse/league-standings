package com.abilashcse.leaguestandings.data.api

interface APICallback<T> {
    fun onSuccess(data: T)
    fun onError(error: String)
}
