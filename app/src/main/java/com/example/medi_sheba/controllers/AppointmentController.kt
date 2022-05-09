package com.example.medi_sheba.controllers

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.medi_sheba.model.Appoint
import com.example.medi_sheba.model.Message
import com.example.medi_sheba.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AppointmentController {
    val db = Firebase.firestore
    private val _appointLists = MutableLiveData<List<Appoint>>()
    val appointLists: LiveData<List<Appoint>>
        get() = _appointLists

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    fun getAppointment() {
        val docRef = db.collection("appointment")

        docRef.get().addOnSuccessListener { document ->
//            Log.d("document", "getAppointment: ${document.toString()}")
            val appoint = document.toObjects(Appoint::class.java)
            _appointLists.value = appoint
            Log.d("document", "user: ${_appointLists}")


        }

        /*docRef.get().addOnSuccessListener { task ->
            if(task != null){
//                val user = task.toObject(User::class.java)!!
//                _user.value = user
                Log.d("tasks", "DocumentSnapshot data: ${task.documents}")

                val appoint = mutableListOf<Appoint>()
                for (doc in task.documents) {
                    Log.d("tasks", "===p_uid: ${doc.getString("patient_uid")}")
                    appoint.add(
                        Appoint(
                            patient_uid = doc.getString("patient_uid")!!,
                            doctor_uid = doc.getString("doctor_uid")!!,
                            nurse_uid = doc.getString("nurse_uid")!!,
                            doc_checked = doc.getBoolean("doc_checked")!!,
                            cabin_no = doc.getString("cabin_no")!!,
                            time = doc.getString("time")!!
                        )
                    )
                }
                _appointLists.value = appoint
                Log.d("dataCons", "===p_uid: ${appoint.toString()}")


            } else {
                Log.d("task", "No such document")
            }
        }*/

       /* docRef.addSnapshotListener { data, e ->
//            if (e != null) {
//                Log.d("fireStoreListener", "Listen failed.", e)
//                return@addSnapshotListener
//            }

            val appoint = mutableListOf<Appoint>()
            for (doc in data!!) {
                Log.d("data", "p_uid: ${doc.getString("patient_uid")}")
                appoint.add(
                    Appoint(
                        patient_uid = doc.getString("patient_uid")!!,
                        doctor_uid = doc.getString("doctor_uid")!!,
                        nurse_uid = doc.getString("nurse_uid")!!,
                        doc_checked = doc.getBoolean("doc_checked")!!,
                        cabin_no = doc.getString("cabin_no")!!,
                        time = doc.getString("time")!!
                    )
                )
            }
            _appointLists.value = appoint
        }*/

      /*  docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.d("fireStoreListener", "Listen failed.", e)
                return@addSnapshotListener
            }

            snapshot?.let {
                val allAppointment = ArrayList<Appoint>()
                val documents = snapshot.documents

                documents.forEach{
                    val appoint = it.toObject(Appoint::class.java)
                    appoint?.let {
                        allAppointment.add(appoint)
//                        Log.d("FB", "getMessages: $products")
                    }
                }
                _appointLists.value = allAppointment

                Log.d("FB", "getMessages: $allAppointment")
            }
        }*/
    }

    fun getUserData(context: Context, uid: String){
        Log.d("tasks", "uid::::::   $uid")
        val userRef = db.collection("users").document(uid)



        /*userRef.get().addOnCompleteListener() { task ->
            if(task != null){
                val user = task.result
//                _user.value = user
                Log.d("task", "DocumentSnapshot data: ${user}")
            } else {
                Log.d("task", "No such document")
            }
        }*/







//        userRef.get().addOnSuccessListener { task ->
//            if(task != null){
////                val user = task.toObject(User::class.java)!!
////                _user.value = user
//                Log.d("tasks", "DocumentSnapshot data: ${task.data}")
//            } else {
//                Log.d("task", "No such document")
//            }
//        }.addOnFailureListener{ exception ->
//            Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()
//
//        }

        /*userRef.addSnapshotListener{ snapShot, e ->
            if (e != null) {
                Log.d("fireStoreListener", "Listen failed.", e)
                return@addSnapshotListener
            }

            if(snapShot != null && snapShot.exists()){
                Log.d("fireStoreListener", "data: ${snapShot.data}" )

                val user = snapShot.toObject(User::class.java)!!
                _user.value = user


            }


        }*/
    }
}