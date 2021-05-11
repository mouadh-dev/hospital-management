package com.example.stagepfe.Dao

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import com.example.stagepfe.entite.*
import com.example.stagepfe.util.BaseConstant
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.coroutines.coroutineContext


class UserDao : IGestionUser {

    private val database = FirebaseDatabase.getInstance()
    private val myRef = database.getReference(BaseConstant.instance().userRef)
    private val mAuth = FirebaseAuth.getInstance()
    private val userRef = FirebaseDatabase.getInstance().getReference("users")
    private val medicamentRef = FirebaseDatabase.getInstance().getReference("Medicament")
    private val reclamationRef = database.getReference(BaseConstant.instance().reclamation)
//  private val rapportRef = database.getReference(BaseConstant.instance().rapport).child(uid).child("rapports")

    ////////////////////////////////////////////////Insert user/////////////////////////////////////////
    override fun insertUser(userItem: UserItem) {
        userItem.id = myRef.push().key.toString()
        myRef.child(userItem.id!!).setValue(userItem)
    }

    /////////////////////////////////////////////////sign up////////////////////////////////////////////
    fun signUpUser(activity: Activity, userItem: UserItem, responseCallback: ResponseCallback) {
        mAuth.createUserWithEmailAndPassword(userItem.mail, userItem.password)
            .addOnCompleteListener(activity, object : OnCompleteListener<AuthResult?> {
                override fun onComplete(task: Task<AuthResult?>) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("FragmentActivity", "createUserWithEmail:success")
                        userItem.id = mAuth!!.getCurrentUser().uid
                        myRef.child(userItem.id!!).setValue(userItem)
                        responseCallback.success()

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(
                            "FragmentActivity",
                            "createUserWithEmail:failure",
                            task.getException()
                        )
                        responseCallback.failure()

                    }

                    // ...
                }
            })
    }

    /////////////////////////////////////////////updateUser/////////////////////////////////////////
    fun updateUser(id:String,userItem: UserItem, userCallback: UserCallback) {

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userRef.child(id).removeValue()
                    userRef.child(id).setValue(userItem)
                    userCallback.onSuccess(userItem)

            }

            override fun onCancelled(error: DatabaseError) {

            }


        })
    }


    ///////////////////////////////////////////Sign in//////////////////////////////////////////////////
    fun signIn(activity: Activity, userItem: UserItem, userCallback: UserCallback) {
        mAuth.signInWithEmailAndPassword(userItem.mail, userItem.password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {

                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = mAuth.currentUser
                    var registredId = user.uid
                    getUserByUid(registredId, userCallback)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)

                    userCallback.failure()
                }
            }

    }

    ///////////////////////////////////////////////get user by id///////////////////////////////////////
    fun getUserByUid(uid: String, responseCallback: UserCallback) {
        var jLoginDatabase = database.reference.child("users").child(uid)
        jLoginDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userItem = dataSnapshot.getValue(UserItem::class.java)
                if (userItem != null) {
                    responseCallback.onSuccess(userItem)
                }


            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "signInWithEmail:failure", error.toException())
                responseCallback.failure()
            }

        })
    }

    //////////////////////////////////////////////////retrieve data user////////////////////////////////
    fun retrieveCurrentDataUser(userCallback: UserCallback) {

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                getUserByUid(mAuth.currentUser.uid, userCallback)
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "signInWithEmail:failure", error.toException())
                userCallback.failure()
            }
        })
    }

    //////////////////////////////////////////sign out methode////////////////////////////////////////
    fun signOut(userItem: UserItem, userCallback: UserCallback) {
        mAuth.signOut()
        mAuth.addAuthStateListener {
            userCallback.onSuccess(userItem)
        }

    }

    //////////////////////////////////////////Insert appointment////////////////////////////////////////
    override fun insertappointment(
        appointment: Appointment,
        userItem: UserItem,
        uid: String,
        responseCallback: AppointmentCallback
    ) {
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    for (ds in snapshot.children) {
                        var userItem = ds.getValue(UserItem::class.java)
                        var firstNAme = userItem!!.nom
                        var lastName = userItem.prenom
                        var fullNAme = firstNAme + " " + lastName
                        if (appointment.namePatient.equals(fullNAme)) {
                            var id = userItem.id
                            responseCallback.successAppointment(appointment)
                            var hour = HashMap<String, Appointment>()
                            var day = HashMap<String, HashMap<String, Appointment>>()
                            hour[appointment.hour.toString()] = appointment
                            day[appointment.date.toString()] = hour
                            userItem.rendezVous = day
                            userRef.child(id.toString())
                                .child("rendezVous").child(appointment.date.toString())
                                .child(appointment.hour.toString()).setValue(appointment)
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "signInWithEmail:failure", error.toException())
            }
        })
    }

    /////////////////////////////////////////////getAppointment/////////////////////////////////////////
    fun getAppointment(responseCallback: AppointmentCallback) {
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (ds in snapshot.children) {
                        var userItem = ds.getValue(UserItem::class.java)
                        if (userItem!!.rendezVous != null) {
                            var map = userItem.rendezVous
                            for (entry in map!!.entries) {
                                for (test in entry.value) {
                                    var appointment = test.value

                                    responseCallback.successAppointment(appointment)
                                }
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                responseCallback.failureAppointment()
            }
        })
    }

    //////////////////////////////////////////insertReclamation/////////////////////////////////////////
    override fun insertReclamation(reclamation: Reclamation) {
        reclamation.id = reclamationRef.push().key.toString()
        reclamationRef.child(reclamation.id!!).setValue(reclamation)
    }

    //////////////////////////////////////////insertRapport/////////////////////////////////////////
    override fun insertRapport(
        rapports: Rapports,
        userItem: UserItem,
        idPatientRapport:String,
        idDoctorRapport: String,
        responseCallback: ResponseCallback
    ) {
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                            var rapport = HashMap<String, Rapports>()
                            rapport[rapports.id.toString()] = rapports
                userItem.rapports = rapport

                           rapports.id = userRef.push().key
                userRef.child(idDoctorRapport).child("rapports").child(rapports.id!!)
                                .setValue(rapports)
                            responseCallback.success()
                userRef.child(idPatientRapport).child("rapports").child(rapports.id!!)
                    .setValue(rapports)
                responseCallback.success()


                        }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
////////////////////////////////////////////get rapport ////////////////////////////////////////////
    fun getRapport(responseCallback: RapportCallback) {
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (ds in snapshot.children) {
                        var userItem = ds.getValue(UserItem::class.java)
                        if (userItem!!.rapports != null) {
                            var rapport = userItem.rapports
                            for (entry in rapport!!.values) {
                                responseCallback.success(entry)
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                responseCallback.failure()
            }
        })
    }
    /////////////////////////////////////////////PopulateSearch/////////////////////////////////////
    fun populateSearch(responseCallback: UserCallback) {
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (ds in snapshot.children) {
                        var userItem = ds.getValue(UserItem::class.java)
                        responseCallback.onSuccess(userItem!!)
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                responseCallback.failure()
                println("mouadh : " + error)
            }

        })


    }



    /////////////////////////////////////////////insert Ordonance/////////////////////////////////////
    override fun insertordonance(
        idDoctor:String,
        idPatient:String,
        ordonance: Ordonance,
        userItem: UserItem,
        ordonanceCallback: OrdonanceCallback
    ) {
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                ordonanceCallback.successOrdonance(ordonance)
                ordonance.id = userRef.push().key
                var ordonances = HashMap<String, Ordonance>()
                ordonances[ordonance.id.toString()] = ordonance


                userItem.ordonance = ordonances
                userRef.child(idDoctor)
                    .child("ordonance").child(ordonance.id.toString())
                    .setValue(ordonance)
                userRef.child(idPatient)
                    .child("ordonance").child(ordonance.id.toString())
                    .setValue(ordonance)

            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

    }
    ////////////////////////////////////////////get Ordonance///////////////////////////////////////
    fun getOrdonance(responseCallback: getOrdonanceCallback) {
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (ds in snapshot.children) {
                        var userItem = ds.getValue(UserItem::class.java)
                        if (userItem!!.rapports != null) {

                            if (userItem.ordonance != null) {
                                var ordonance = userItem.ordonance
                                for (entry in ordonance!!.entries) {
                                    var ord = entry.value
                                    var medicament = entry.value
                                    var test = medicament.medicament
                                    for (med in test) {
                                        responseCallback.successOrdonance(ord, med)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                responseCallback.failureOrdonance()
            }
        })
    }
    ////////////////////////////////////////////change password/////////////////////////////////////
    fun changePassword(password:String,userItem: UserItem,activity: Activity,responseCallback: ResponseCallback){
        val user = FirebaseAuth.getInstance().currentUser
        user!!.updatePassword(password).addOnCompleteListener { Task ->
            if (Task.isSuccessful) {
                println("Update Success")
                userRef.child(userItem.id.toString()).child("confirmpassword").setValue(password)
                userRef.child(userItem.id.toString()).child("password").setValue(password)

                responseCallback.success()
            } else {
                println("Error Update" + Task.exception)
                responseCallback.failure()
            }
        }
    }
    /////////////////////////////////////////////PopulateSearch/////////////////////////////////////
    fun MedicamenteSearch(responseCallback: ResponseCallback) {
        medicamentRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (ds in snapshot.children) {
                        var medicament = ds.getValue(String::class.java)
                        responseCallback.success(medicament!!)
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                responseCallback.failure()
            }

        })


    }
    ///////////////////////////////////////////update Photo////////////////////////////////////////
//    private fun updateProfile() {
//        val user = mAuth.currentUser
//        user?.let { user ->
//            val photoURI = Uri.parse("android.resource://$packageName/${R.drawable.logopatient}")
//            val profileUpdates = UserProfileChangeRequest.Builder()
//                .setPhotoUri(photoURI)
//                .build()
//
//            CoroutineScope(Dispatchers.IO).launch {
//                try {
//                    user.updateProfile(profileUpdates).await()
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(this@MainActivity, "Successfully updated profile",
//                            Toast.LENGTH_LONG).show()
//                    }
//                } catch(e: Exception) {
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_LONG).show()
//                    }
//                }
//
//            }
//        }
//
//




}



