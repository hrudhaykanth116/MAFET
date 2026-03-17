package com.hrudhaykanth116.auth.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.hrudhaykanth116.auth.data.datasources.remote.FirebaseIAuthRemoteDataSource
import com.hrudhaykanth116.auth.data.datasources.remote.IAuthRemoteDataSource
import com.hrudhaykanth116.auth.data.datasources.remote.user.IUserRemoteDataSource
import com.hrudhaykanth116.auth.data.datasources.remote.user.UserRemoteDataSourceImpl
import com.hrudhaykanth116.auth.data.repository.AuthRepositoryImpl
import com.hrudhaykanth116.auth.data.repository.IAuthRepository
import com.hrudhaykanth116.auth.data.repository.user.IUserRepository
import com.hrudhaykanth116.auth.data.repository.user.UserRepositoryImpl
import com.hrudhaykanth116.auth.domain.usecases.LoginUseCase
import com.hrudhaykanth116.auth.domain.usecases.SignUpUseCase
import com.hrudhaykanth116.auth.domain.usecases.ValidateEmailUseCase
import com.hrudhaykanth116.auth.domain.usecases.ValidatePasswordUseCase
import com.hrudhaykanth116.auth.ui.screens.login.LoginViewModel
import com.hrudhaykanth116.auth.ui.screens.signup.SignUpViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val authModule = module {
    // Firebase dependencies
    single<FirebaseAuth> { FirebaseAuth.getInstance() }

    single<DatabaseReference> { Firebase.database.reference }

    single<FirebaseStorage> { Firebase.storage }

    // Data sources
    single<IAuthRemoteDataSource> {
        FirebaseIAuthRemoteDataSource(
            get(),
            get(),
            get()
        )
    }

    single<IUserRemoteDataSource> {
        UserRemoteDataSourceImpl(
            get(),
            get(),
            get()
        )
    }

    // Repositories
    single<IAuthRepository> {
        AuthRepositoryImpl(get())
    }

    single<IUserRepository> {
        UserRepositoryImpl(
            get(),
            get(named("IoDispatcher"))
        )
    }

    // Use cases
    factory { ValidateEmailUseCase() }

    factory { ValidatePasswordUseCase() }

    factory { LoginUseCase(get()) }

    factory {
        SignUpUseCase(
            get(),
            get(),
            get()
        )
    }

    // ViewModels
    viewModel {
        LoginViewModel(get())
    }

    viewModel {
        SignUpViewModel(get())
    }
}
