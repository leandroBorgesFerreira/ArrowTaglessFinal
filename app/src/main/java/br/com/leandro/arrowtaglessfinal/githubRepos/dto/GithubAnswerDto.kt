package br.com.leandro.arrowtaglessfinal.githubRepos.dto

import br.com.leandro.arrowtaglessfinal.githubRepos.domain.Repository

class GithubAnswerDto(val total_count: Long,
                      val isIncomplete_results: Boolean,
                      val items: List<Repository>)
