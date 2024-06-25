package com.tigran.theguardiandemo.di

import androidx.room.Room
import com.tigran.data.db.NewsDatabase
import com.tigran.data.network.service.NewsService
import com.tigran.data.repoimpl.LocalArticleRepositoryImpl
import com.tigran.data.repoimpl.RemoteArticleRepoImpl
import com.tigran.domain.repo.LocalArticleRepo
import com.tigran.domain.repo.RemoteArticleRepo
import com.tigran.domain.usecase.ArticleUseCase
import com.tigran.theguardiandemo.viewmodel.ArticleDetailedViewModel
import com.tigran.theguardiandemo.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val koinModule = module {
    viewModel {
        MainViewModel(get())
    }
    viewModel {
        ArticleDetailedViewModel(localArticleRepo = get(qualifier = named(LOCAL_REPO_QUALIFIER)))
    }
    factory {
        ArticleUseCase(
            remoteArticleRepo = get(qualifier = named(REMOTE_REPO_QUALIFIER)),
            localArticleRepo = get(
                qualifier = named(
                    LOCAL_REPO_QUALIFIER
                )
            )
        )
    }
    factory<RemoteArticleRepo>(qualifier = named(REMOTE_REPO_QUALIFIER)) {
        RemoteArticleRepoImpl(get())
    }
    factory<LocalArticleRepo>(qualifier = named(LOCAL_REPO_QUALIFIER)) {
        LocalArticleRepositoryImpl(get())
    }
    single {
        Room.databaseBuilder(get(), NewsDatabase::class.java, "article_database")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<NewsDatabase>().articleDao() }
    single {
        Retrofit.Builder()
            .baseUrl("https://content.guardianapis.com/") // Replace with your base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(NewsService::class.java)
    }
}

const val REMOTE_REPO_QUALIFIER = "remote_repo_qualifier"
const val LOCAL_REPO_QUALIFIER = "local_repo_qualifier"