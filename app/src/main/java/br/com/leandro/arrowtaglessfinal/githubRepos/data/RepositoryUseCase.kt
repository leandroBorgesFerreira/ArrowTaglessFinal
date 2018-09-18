package br.com.leandro.arrowtaglessfinal.githubRepos.data

import arrow.Kind
import br.com.leandro.arrowtry.githubrepos.domain.Repository

class RepositoryUseCase<F>(
    private val dataSource: RepositoryDataSource<F>
) : Kind<F, List<Repository>> {

    fun getRepositories() : Kind<F, List<Repository>> = dataSource.fetchAllRepositories()

}
