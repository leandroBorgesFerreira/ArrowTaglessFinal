package br.com.leandro.arrowtaglessfinal.githubRepos.data

import arrow.Kind
import arrow.effects.typeclasses.Async
import br.com.leandro.arrowtry.githubrepos.domain.Repository
import kotlinx.coroutines.experimental.android.UI

class RepositoryUseCase<F>(
    private val dataSource: RepositoryDataSource<F>
) : Kind<F, List<Repository>>, Async<F> by dataSource{

    fun getRepositories() : Kind<F, List<Repository>> =
        dataSource.fetchAllRepositories().continueOn(UI)

}
