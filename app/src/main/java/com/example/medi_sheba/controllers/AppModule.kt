package com.example.medi_sheba.controllers

import android.content.Context
import com.example.medi_sheba.presentation.encryption.EncryptClass
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context ): AppApplication{
        return app as AppApplication
    }

    @Singleton
    @Provides
    fun provideRandomString(): String{
        return "Hey! look a random String!!! "
    }

    @Singleton
    @Provides
    fun provideEncryptClass(): EncryptClass {
        return EncryptClass()
    }
}