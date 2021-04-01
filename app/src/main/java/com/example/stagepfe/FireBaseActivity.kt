//package com.example.stagepfe
//
//import android.os.Bundle
//import android.util.Log
//import android.widget.Button
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.example.stagepfe.dao.ResponseCallback
//import com.example.stagepfe.dao.IGestionUser
//import com.example.stagepfe.entite.UserItem
//import com.google.android.gms.tasks.OnCompleteListener
//import com.google.android.gms.tasks.Task
//import com.google.firebase.auth.AuthResult
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.FirebaseDatabase
//
//
//class FirebaseActivity : AppCompatActivity() {
//
//    private var mAuth: FirebaseAuth? = null
//    var user: UserItem? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_fire_base)
//        var user = UserItem("vvv")
//
//
//        val database = FirebaseDatabase.getInstance()
//        val myRef = database.getReference(BaseConstant.instance().userRef)
//        mAuth = FirebaseAuth.getInstance();
//        user.Condition = myRef.push().key.toString()
//        myRef.child(user.Condition).setValue(user)
//
//        var iGestionUser = GestionUser()
//        iGestionUser.insertUser(user)
//        iGestionUser.signUpUser(this@FirebaseActivity, user, object : ResponseCallback {
//            override fun failure(failure: UserItem) {
//                // dismiss dialog, dismiss loading bar , show error message etc
//                Toast.makeText(
//                    this@FirebaseActivity,
//                    " insertion success continue traitement failure",
//                    Toast.LENGTH_LONG).show()
//
//            }
//
//            override fun success(success: Boolean) {7
//                Toast.makeText(
//                    this@FirebaseActivity,
//                    " insertion success continue traitement succees",
//                    Toast.LENGTH_LONG).show()
//            }
//        })
//
//        mAuth!!.createUserWithEmailAndPassword("mouadh@yopmail.com", "password")
//            .addOnCompleteListener(this, object : OnCompleteListener<AuthResult?> {
//                override fun onComplete(task: Task<AuthResult?>) {
//                    if (task.isSuccessful()) {
//                        // Sign in success, update UI with the signed-in user's information
//                        Log.d("FragmentActivity", "createUserWithEmail:success")
//                        val id: String = mAuth!!.getCurrentUser().uid
//                        user.Condition = mAuth!!.getCurrentUser().uid
//                        user.Condition = "mouadh@yopmail.com"
//                        myRef.child(user.Condition).setValue(user)
//
//                    } else {
//                        // If sign in fails, display a message to the user.
//                        Log.w(
//                            "FragmentActivity",
//                            "createUserWithEmail:failure",
//                            task.getException()
//                        )
//                        Toast.makeText(
//                            this@FirebaseActivity, "Authentication failed.",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//
//                    // ...
//                }
//            })
//    }
//}
