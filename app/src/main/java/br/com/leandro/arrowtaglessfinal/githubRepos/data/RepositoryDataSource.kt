package br.com.leandro.arrowtaglessfinal.githubRepos.data

import arrow.Kind
import arrow.effects.typeclasses.Async
import arrow.typeclasses.bindingCatch
import br.com.leandro.arrowtaglessfinal.retrofit.ApiClient
import br.com.leandro.arrowtry.githubrepos.domain.Repository
import kotlinx.coroutines.experimental.Dispatchers

class RepositoryDataSource<F>(
    private val apiClient: ApiClient,
    async: Async<F>
) : Async<F> by async {
    fun fetchAllRepositories(): Kind<F, List<Repository>> =
            bindingCatch {
                invoke(Dispatchers.Default) {
                    apiClient.getRepositories("Java", "star", 1)
                        .execute()
                        .body()!!
                        .items
                }.bind()
            }
}
