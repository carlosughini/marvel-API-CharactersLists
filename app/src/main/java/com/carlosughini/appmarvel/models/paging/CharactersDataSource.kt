package com.carlosughini.appmarvel.models.paging

import android.arch.paging.PageKeyedDataSource
import android.util.Log
import com.carlosughini.appmarvel.service.MarvelApi
import com.carlosughini.appmarvel.models.entity.Characters
import io.reactivex.disposables.CompositeDisposable

class CharactersDataSource(
        private val marvelApi: MarvelApi,
        private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Characters>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Characters>) {
        val numberOfItems = params.requestedLoadSize
        createObservable(0, 1, numberOfItems, callback, null)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Characters>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        createObservable(page, page + 1, numberOfItems, null, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Characters>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        createObservable(page, page - 1, numberOfItems, null, callback)
    }

    private fun createObservable(requestedPage: Int,
                                 adjacentPage: Int,
                                 requestedLoadSize: Int,
                                 initialCallback: LoadInitialCallback<Int, Characters>?,
                                 callback: LoadCallback<Int, Characters>?) {
        compositeDisposable.add(
                marvelApi.allCharacters(requestedPage * requestedLoadSize)
                        .subscribe(
                                { response ->
                                    Log.d("NGVL", "Loading page: $requestedPage")
                                    initialCallback?.onResult(response.data.results, null, adjacentPage)
                                    callback?.onResult(response.data.results, adjacentPage)
                                },
                                { t ->
                                    Log.d("NGVL", "Error loading page: $requestedPage", t)
                                }
                        )
        );
    }
}