package br.com.leandro.arrowtaglessfinal.githubRepos.data

import arrow.Kind
import arrow.effects.typeclasses.Async
import br.com.leandro.arrowtaglessfinal.githubRepos.domain.Repository
import br.com.leandro.arrowtaglessfinal.retrofit.ApiClient
import kotlinx.coroutines.Dispatchers

class RepositoryDataSource<F>(
    private val apiClient: ApiClient,
    async: Async<F>
) : Async<F> by async {
    fun fetchAllRepositories(): Kind<F, List<Repository>> =
                invoke(Dispatchers.IO) {
                    apiClient.getRepositories("Java", "star", 1)
                        .execute()
                        .body()!!
                        .items
                }
}
