/*package com.example.stagepfe.dao

import android.app.Activity
import android.util.Log
import com.example.stagepfe.entite.UserItem
import com.example.stagepfe.util.BaseConstant
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SendToFireBase : IGestionUser {

    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference(BaseConstant.instance().userRef)
    val mAuth = FirebaseAuth.getInstance();


    override fun insertUser(userItem: UserItem) {

        userItem.id = myRef.push().key.toString()
        myRef.child(userItem.id).setValue(userItem)
    }

    fun signUpUser(activity: Activity, userItem: UserItem, responseCallback: ResponseCallback) {
        mAuth.createUserWithEmailAndPassword("mouadh@yopmail.com", "password")
            .addOnCompleteListener(activity, object : OnCompleteListener<AuthResult?> {
                override fun onComplete(task: Task<AuthResult?>) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("FragmentActivity", "createUserWithEmail:success")
//                        val id: String = mAuth!!.getCurrentUser().uid
                        userItem.id = mAuth!!.getCurrentUser().uid
                        userItem.firas = "mouadh@yopmail.com"
                        myRef.child(userItem.id).setValue(userItem)
                        responseCallback.success(true)

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(
                            "FragmentActivity",
                            "createUserWithEmail:failure",
                            task.getException()
                        )
                        responseCallback.failure(userItem)
//                        Toast.makeText(
//                            this@FirebaseActivity, "Authentication failed.",
//                            Toast.LENGTH_SHORT
//                        ).show()
                    }

                    // ...
                }
            })
    }

    override fun insertUser1(userItem: UserItem) {
        // TODO("Not yet implemented")
    }
}
*/