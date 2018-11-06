package br.com.leandro.arrowtaglessfinal.githubRepos.view

import br.com.leandro.arrowtaglessfinal.githubRepos.domain.Repository

interface RepositoriesView {
    fun showNotFoundError()
    fun showGenericError()
    fun showAuthenticationError()
    fun drawRepositories(repositoryList: List<Repository>)
}
