package com.hrudhaykanth116.mafet.auth.data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.hrudhaykanth116.mafet.auth.data.datasources.remote.FirebaseIAuthRemoteDataSource
import com.hrudhaykanth116.mafet.auth.data.datasources.remote.IAuthRemoteDataSource
import com.hrudhaykanth116.mafet.auth.data.datasources.remote.user.IUserRemoteDataSource
import com.hrudhaykanth116.mafet.auth.data.datasources.remote.user.UserRemoteDataSourceImpl
import com.hrudhaykanth116.mafet.auth.data.repository.AuthRepositoryImpl
import com.hrudhaykanth116.mafet.auth.data.repository.IAuthRepository
import com.hrudhaykanth116.mafet.auth.data.repository.user.IUserRepository
import com.hrudhaykanth116.mafet.auth.data.repository.user.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object RepositoryDiModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    // Using default root data base reference
    @Provides
    fun provideFirebaseDb(): DatabaseReference = Firebase.database.reference

    // Using default storage path
    @Provides
    fun provideFirebaseStorage(): FirebaseStorage = Firebase.storage

    @Provides
    fun provideAuthRemoteDataSource(
        firebaseAuth: FirebaseAuth,
        databaseReference: DatabaseReference,
        firebaseStorage: FirebaseStorage,
    ): IAuthRemoteDataSource =
        FirebaseIAuthRemoteDataSource(firebaseAuth, databaseReference, firebaseStorage)

    @Provides
    fun providesAuthRepository(firebaseAuthRemoteDataSource: FirebaseIAuthRemoteDataSource): IAuthRepository =
        AuthRepositoryImpl(firebaseAuthRemoteDataSource)

    @Provides
    fun provideUserRemoteDataSource(
        firebaseAuth: FirebaseAuth,
        databaseReference: DatabaseReference,
        firebaseStorage: FirebaseStorage,
    ): IUserRemoteDataSource =
        UserRemoteDataSourceImpl(firebaseAuth, databaseReference, firebaseStorage)

    @Provides
    fun providesUserRepository(remoteDataSource: IUserRemoteDataSource): IUserRepository =
        UserRepositoryImpl(remoteDataSource)

}