package br.com.leandro.arrowtaglessfinal.githubRepos.data

import arrow.Kind
import arrow.effects.typeclasses.Async
import arrow.integrations.retrofit.adapter.unwrapBody
import br.com.leandro.arrowtaglessfinal.githubRepos.domain.Repository
import br.com.leandro.arrowtaglessfinal.retrofit.ApiClient

class RepositoryDataSource<F>(
    private val apiClient: ApiClient,
    async: Async<F>
) : Async<F> by async {

    fun fetchAllRepositories(): Kind<F, List<Repository>> =
        apiClient.getRepositories("Java", "star", 1)
            .async(this)
            .flatMap { response -> response.unwrapBody(this) }
            .map { dto -> dto.items }

}
