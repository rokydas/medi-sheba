package com.example.medi_sheba.FirestoreAll

import com.example.medi_sheba.model.Appoint
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductsRepository @Inject constructor(
    private val queryProductsByName: Query
) {
    suspend fun getProductsFromFirestore(): DataOrException<List<Appoint>, Exception> {
        val dataOrException = DataOrException<List<Appoint>, Exception>()
        try {
            dataOrException.data = queryProductsByName.get().await().map { document ->
                document.toObject(Appoint::class.java)
            }
        } catch (e: FirebaseFirestoreException) {
            dataOrException.e = e
        }
        return dataOrException
    }
}