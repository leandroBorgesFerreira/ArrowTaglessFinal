package br.com.leandro.arrowtaglessfinal.githubRepos.domain

import br.com.leandro.arrowtry.githubrepos.domain.User
import com.google.gson.annotations.SerializedName

    class Repository(val id: Long,
                     val name: String,
                     val description: String,
                     val owner: User,
                     @SerializedName("forks_count") val forksCount: Int,
                     @SerializedName("stargazers_count") val stargazersCount: Int)
