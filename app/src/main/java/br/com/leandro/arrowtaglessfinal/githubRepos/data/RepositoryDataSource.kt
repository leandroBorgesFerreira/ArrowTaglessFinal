package br.com.leandro.arrowtaglessfinal.githubRepos.data

import arrow.Kind
import arrow.effects.ObservableK
import arrow.effects.async
import arrow.effects.typeclasses.Async
import arrow.typeclasses.binding
import br.com.leandro.arrowtaglessfinal.retrofit.ApiClient
import br.com.leandro.arrowtry.githubrepos.domain.Repository
import kotlinx.coroutines.experimental.Dispatchers

class RepositoryDataSource<F>(
    private val async: Async<F>,
    private val apiClient: ApiClient
) {
    fun fetchAllRepositories(): Kind<F, List<Repository>> {
        return async.binding {
            ObservableK.async()

            async(Dispatchers.Default) {
                apiClient.getRepositories("Java", "star", 1).execute().body()!!.items
            }.bind()
        }
    }
}
