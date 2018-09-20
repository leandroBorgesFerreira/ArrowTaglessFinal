package br.com.leandro.arrowtaglessfinal.githubRepos.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import arrow.effects.ForObservableK
import arrow.effects.ObservableK
import arrow.effects.async
import arrow.effects.fix
import arrow.effects.typeclasses.Async
import arrow.syntax.function.pipe
import br.com.leandro.arrowtaglessfinal.R
import br.com.leandro.arrowtaglessfinal.githubRepos.data.RepositoryDataSource
import br.com.leandro.arrowtaglessfinal.githubRepos.data.RepositoryUseCase
import br.com.leandro.arrowtaglessfinal.githubRepos.presentation.RepositoryPresenter
import br.com.leandro.arrowtaglessfinal.githubRepos.view.adapter.RepositoriesAdapter
import br.com.leandro.arrowtaglessfinal.retrofit.apiClient
import br.com.leandro.arrowtry.githubrepos.domain.Repository
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class MainActivity : AppCompatActivity(), RepositoriesView {

    private val repoList : MutableList<Repository> = mutableListOf()
    lateinit var presenter : RepositoryPresenter<ForObservableK>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupList(repoList)

//        presenter = initPresenter(IO.async())
//        presenter
//            .drawRepositories()
//            .fix()
//            .unsafeRunAsync { }

        presenter = initPresenter(ObservableK.async())
        presenter
            .drawRepositories()
            .fix()
            .observable
            .subscribe()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    private fun <F> initPresenter(async: Async<F>) =
        RepositoryDataSource(async, apiClient()) pipe { dataSource ->
            RepositoryUseCase(dataSource)
        } pipe { useCase ->
            RepositoryPresenter(useCase, this)
        }

    private fun setupList(listItems : List<Repository>) {
        repositoryListRV.layoutManager = LinearLayoutManager(this)
        repositoryListRV.adapter = RepositoriesAdapter(listItems, { Log.d("Click", "Got a click!") })
    }

    override fun showNotFoundError() {
        runOnUiThread {
            alert("Not found!!") {
                yesButton { }
            }.show()
        }
    }

    override fun showGenericError() {
        runOnUiThread {
            alert("Generic error!!") {
                yesButton { }
            }.show()
        }
    }

    override fun showAuthenticationError() {
        runOnUiThread {
            alert("Auth error!!") {
                yesButton { }
            }.show()
        }
    }

    override fun drawRepositories(heroes: List<Repository>) {
        runOnUiThread {
            repoList.addAll(heroes)
            repositoryListRV.adapter.notifyDataSetChanged()
        }
    }
}
