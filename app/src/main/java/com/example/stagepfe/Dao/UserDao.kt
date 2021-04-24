package com.example.stagepfe.Dao

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import com.example.stagepfe.entite.Appointment
import com.example.stagepfe.entite.UserItem
import com.example.stagepfe.util.BaseConstant
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class UserDao : IGestionUser {

    private val database = FirebaseDatabase.getInstance()
    private val myRef = database.getReference(BaseConstant.instance().userRef)
    private val mAuth = FirebaseAuth.getInstance()
    private val userRef = FirebaseDatabase.getInstance().getReference("users")


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
    private fun getUserByUid(uid: String, responseCallback: UserCallback) {
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
    fun retrieveDataUser(activity: Activity, userItem: UserItem, userCallback: UserCallback) {

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//                val user = mAuth.currentUser

                getUserByUid(mAuth.currentUser.uid, userCallback)
            }

            override fun onCancelled(error: DatabaseError) {
                userCallback.failure()
            }

        })
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
                        var id = userItem.id

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
            }
        })

    }

/////////////////////////////////////////////getAppointment/////////////////////////////////////////
    fun getAppointment(
        appointment: Appointment,
        userItem: UserItem,
        responseCallback: AppointmentCallback
    ) {
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    for (ds in snapshot.children) {
                        var userItem = ds.getValue(UserItem::class.java)
                        if (userItem!!.rendezVous != null) {
                            var map = userItem.rendezVous
                            for (entry in map!!.entries){

                                for (test in entry.value){
                                    var appointment = test.value

                                    responseCallback.successAppointment(appointment)
                                }
                            }

//                            date = date.dropLast(1)
//                            date = date.drop(1)
//                            var hour = ds.child("users").child(id).child("rendezVous").child(date).value as HashMap<String,Appointment>
//
//                            for (n in hour)
//                            hour.get(0)
//                            var v = ds.child("users").child(id).child("rendezVous").child(date).child(hour).getValue(Appointment::class.java)

//                            println("mouadh $v")



                        }
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                responseCallback.failureAppointment()
            }
        })
    }
}



