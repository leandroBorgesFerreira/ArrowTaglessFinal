package br.com.leandro.arrowtaglessfinal.retrofit

import arrow.integrations.retrofit.adapter.CallK
import br.com.leandro.arrowtaglessfinal.githubRepos.dto.GithubAnswerDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {

    @GET("search/repositories")
    fun getRepositories(@Query("q") language: String,
                        @Query("sort") order: String,
                        @Query("page") page: Int): CallK<GithubAnswerDto>
}

fun apiClient() : ApiClient = retrofit().create(ApiClient::class.java)
