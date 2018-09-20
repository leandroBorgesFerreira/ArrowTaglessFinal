package br.com.leandro.arrowtaglessfinal.githubRepos.presentation

import arrow.Kind
import arrow.effects.typeclasses.Disposable
import arrow.effects.typeclasses.bindingCancellable
import br.com.leandro.arrowtaglessfinal.githubRepos.data.RepositoryUseCase
import br.com.leandro.arrowtaglessfinal.githubRepos.view.RepositoriesView

class RepositoryPresenter<F>(
    private val repositoryUseCase: RepositoryUseCase<F>,
    private val view: RepositoriesView,
    private val cancelableList: MutableList<Disposable> = mutableListOf()) {

    fun drawRepositories() : Kind<F, Unit> =
        with(repositoryUseCase) {
            val (kind, disposable) = bindingCancellable {
                val repos = getRepositories().handleError {
                    view.showGenericError()
                    emptyList()
                }.bind()

                view.drawRepositories(repos)
            }

            cancelableList.add(disposable)
            kind
        }

    fun onPause() {
        cancelableList.forEach { disposable -> disposable.invoke() }
    }
}
