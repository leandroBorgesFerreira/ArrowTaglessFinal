package br.com.leandro.arrowtaglessfinal.githubRepos.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import arrow.effects.ForIO
import arrow.effects.IO
import arrow.effects.fix
import arrow.effects.instances.io.async.async
import arrow.effects.typeclasses.Async
import arrow.syntax.function.pipe
import br.com.leandro.arrowtaglessfinal.R
import br.com.leandro.arrowtaglessfinal.githubRepos.data.RepositoryDataSource
import br.com.leandro.arrowtaglessfinal.githubRepos.data.RepositoryUseCase
import br.com.leandro.arrowtaglessfinal.githubRepos.domain.Repository
import br.com.leandro.arrowtaglessfinal.githubRepos.presentation.RepositoryPresenter
import br.com.leandro.arrowtaglessfinal.githubRepos.view.adapter.RepositoriesAdapter
import br.com.leandro.arrowtaglessfinal.retrofit.apiClient
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class MainActivity : AppCompatActivity(), RepositoriesView {

    private val repoList : MutableList<Repository> = mutableListOf()
    private lateinit var presenter : RepositoryPresenter<ForIO>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupList(repoList)

        presenter = initPresenter(IO.async())
        presenter
            .drawRepositories()
            .fix()
            .unsafeRunAsync { }

//        presenter = initPresenter(ObservableK)
//        presenter
//            .drawRepositories()
//            .fix()
//            .observable
//            .subscribe()



//        apiClient()
//            .getRepositoriesObs("Java", "star", 1)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe { dto ->
//                Log.d("Log!!", "Description: ${dto.items[0].description}")
//            }
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    private fun <F> initPresenter(async: Async<F>) =
        RepositoryDataSource(apiClient(), async) pipe { dataSource ->
            RepositoryUseCase(dataSource)
        } pipe { useCase ->
            RepositoryPresenter(useCase, this)
        }

    private fun setupList(listItems : List<Repository>) {
        repositoryListRV.layoutManager = LinearLayoutManager(this)
        repoList.addAll(listItems)
        repositoryListRV.adapter = RepositoriesAdapter(repoList, { Log.d("Click", "Got a click!") })
    }

    override fun showNotFoundError() {
        alert("Not found!!") {
            yesButton { }
        }.show()
    }

    override fun showGenericError() {
        alert("Generic error!!") {
            yesButton { }
        }.show()

    }

    override fun showAuthenticationError() {
        alert("Auth error!!") {
            yesButton { }
        }.show()
    }

    override fun drawRepositories(repositoryList: List<Repository>) {
        repoList.clear()
        repoList.addAll(repositoryList)
        repositoryListRV.adapter.notifyDataSetChanged()
    }
}
