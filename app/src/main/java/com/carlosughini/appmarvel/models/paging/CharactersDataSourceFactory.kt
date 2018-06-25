package com.carlosughini.appmarvel.models.paging

import android.arch.paging.DataSource
import com.carlosughini.appmarvel.service.MarvelApi
import com.carlosughini.appmarvel.models.entity.Characters
import io.reactivex.disposables.CompositeDisposable

class CharactersDataSourceFactory(
        private val compositeDisposable: CompositeDisposable,
        private val marvelApi: MarvelApi
) : DataSource.Factory<Int, Characters>() {

    override fun create(): DataSource<Int, Characters> {
        return CharactersDataSource(marvelApi, compositeDisposable)
    }
}