package br.com.leandro.arrowtaglessfinal.githubRepos.presentation

import arrow.Kind
import arrow.effects.typeclasses.Disposable
import arrow.effects.typeclasses.MonadDefer
import arrow.effects.typeclasses.bindingCancellable
import br.com.leandro.arrowtaglessfinal.githubRepos.data.RepositoryUseCase
import br.com.leandro.arrowtaglessfinal.githubRepos.view.RepositoriesView

class RepositoryPresenter<F>(
    private val monadDefer: MonadDefer<F>,
    private val repositoryUseCase: RepositoryUseCase<F>,
    private val view: RepositoriesView,
    private val cancelableList: MutableList<Disposable> = mutableListOf()) {

    fun drawRepositories() : Kind<F, Unit> {
        val (kind, disposable) = monadDefer.bindingCancellable {
            val repos = repositoryUseCase.getRepositories().handleError {
                view.showGenericError()
                emptyList()
            }.bind()

            view.drawRepositories(repos)
        }

        cancelableList.add(disposable)
        return kind
    }

    private fun onPause() {
        cancelableList.forEach { disposable -> disposable.invoke() }
    }
}
