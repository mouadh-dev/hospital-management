package com.example.stagepfe.Dao

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import com.example.stagepfe.Activity.Patients.BottomBarPatientActivity
import com.example.stagepfe.entite.UserItem
import com.example.stagepfe.util.BaseConstant
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class UserDao : IGestionUser {

    private val database = FirebaseDatabase.getInstance()
    private val myRef = database.getReference(BaseConstant.instance().userRef)
    private val mAuth = FirebaseAuth.getInstance();


    override fun insertUser(userItem: UserItem) {

        userItem.id = myRef.push().key.toString()
        myRef.child(userItem.id!!).setValue(userItem)
    }



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

    fun signIn(activity: Activity, userItem: UserItem, userCallback: UserCallback){
        mAuth.signInWithEmailAndPassword(userItem.mail, userItem.password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {

                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = mAuth.currentUser
                    var registredId = user.uid
                   getUserByUid(mAuth.currentUser.uid,userCallback)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)

                    userCallback.failure()
                }
            }

    }

    private fun getUserByUid(uid: String, responseCallback: UserCallback) {
        var jLoginDatabase = FirebaseDatabase.getInstance().reference.child("users").child(uid)
        jLoginDatabase.addValueEventListener(object : ValueEventListener{
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

    fun retrieveDataUser(activity: Activity, userItem: UserItem, userCallback: UserCallback){

        myRef.addValueEventListener(object : ValueEventListener   {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = mAuth.currentUser

                getUserByUid(mAuth.currentUser.uid,userCallback)
            }

            override fun onCancelled(error: DatabaseError) {
                userCallback.failure()
            }

        })
    }



//    fun retrieveUser(){
//        myRef.addChildEventListener(object : ChildEventListener {
//            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//            }
//
//            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
//            }
//
//            override fun onChildRemoved(snapshot: DataSnapshot) {
//            }
//
//            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//            }
//
//        })
//    }


}
