package br.com.leandro.arrowtaglessfinal.retrofit

import br.com.leandro.arrowtaglessfinal.githubRepos.dto.GithubAnswerDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {

    @GET("search/repositories")
    fun getRepositories(@Query("q") language: String,
                        @Query("sort") order: String,
                        @Query("page") page: Int): Call<GithubAnswerDto>
}

fun apiClient() : ApiClient = retrofit().create(ApiClient::class.java)
