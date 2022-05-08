package com.example.medi_sheba.FirestoreAll

import com.example.medi_sheba.FirestoreAll.Constants.PRODUCTS_COLLECTION
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideQueryProductsByName() = FirebaseFirestore.getInstance()
        .collection(PRODUCTS_COLLECTION)
//        .orderBy(NAME_PROPERTY, ASCENDING)
}