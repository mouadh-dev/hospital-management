package com.example.stagepfe.Dao

import android.app.Activity
import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import com.example.stagepfe.entite.*
import com.example.stagepfe.util.BaseConstant
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import kotlin.collections.HashMap


class UserDao : IGestionUser {

    private val database = FirebaseDatabase.getInstance()
    private val myRef = database.getReference(BaseConstant.instance().userRef)
    private val mAuth = FirebaseAuth.getInstance()
    private val userRef = FirebaseDatabase.getInstance().getReference("users")
    private val messageRef = FirebaseDatabase.getInstance().getReference("Message")
    private val notificationRef = FirebaseDatabase.getInstance().getReference("Notification")
    private val publicationRef = FirebaseDatabase.getInstance().getReference("Post")
    private val likeRef = FirebaseDatabase.getInstance().getReference("Like")

    private val medicamentRef = FirebaseDatabase.getInstance().getReference("Medicament")
    private val reclamationRef = database.getReference(BaseConstant.instance().reclamation)
    private val storageReference = FirebaseStorage.getInstance().reference

    ////////////////////////////////////////////////Insert user/////////////////////////////////////////
    override fun insertUser(userItem: UserItem) {
        userItem.id = myRef.push().key.toString()
        myRef.child(userItem.id!!).setValue(userItem)
    }
    fun getCurrentUserId():String{
        return  FirebaseAuth.getInstance().uid.toString()
    }

    fun supperUser(uid: String, userItem: UserItem, userCallback: UserCallback) {
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userRef.child(uid).removeValue()

                userCallback.onSuccess(userItem)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun supperMessage(uid: String, userItem: UserItem, userCallback: UserCallback) {
        messageRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                messageRef.child(uid).removeValue()
                userCallback.onSuccess(userItem)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
///////////////////////////////////////////////////////////////////////////////////////////////////

    fun retrieveNotification(notificationCallback: NotificationCallback) {
        notificationRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    for (ds in snapshot.children) {
                        var notif = ds.getValue(Notification::class.java)
                        notificationCallback.successNotification(notif!!)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                notificationCallback.failureNotification()
            }
        })
    }

    fun supperNotification(uid: String, userItem: UserItem, userCallback: UserCallback) {
        notificationRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                notificationRef.child(uid).removeValue()
                userCallback.onSuccess(userItem)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    /////////////////////////////////////////////////sign up////////////////////////////////////////////
    fun signUpUser(activity: Activity, userItem: UserItem, signUpCallback: SignUpCallback) {
        mAuth.createUserWithEmailAndPassword(userItem.mail, userItem.password)
            .addOnCompleteListener(activity, object : OnCompleteListener<AuthResult?> {
                override fun onComplete(task: Task<AuthResult?>) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("FragmentActivity", "createUserWithEmail:success")
                        userItem.id = mAuth!!.getCurrentUser().uid
                        myRef.child(userItem.id!!).setValue(userItem)
                        signUpCallback.success()

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(
                            "FragmentActivity",
                            "createUserWithEmail:failure",
                            task.exception
                        )
                        signUpCallback.failure(task.exception.toString().substring(65))

                    }

                    // ...
                }
            })
    }

    /////////////////////////////////////////////updateUser/////////////////////////////////////////
    fun updateUser(id: String, userItem: UserItem, userCallback: UserCallback) {

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
        jLoginDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var userItem = dataSnapshot.getValue(UserItem::class.java)
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

    fun getUserByUidEventListener( responseCallback: UserCallback) {
        var jLoginDatabase = database.reference.child("users").child(getCurrentUserId())
        jLoginDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var userItem = dataSnapshot.getValue(UserItem::class.java)
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

    //////////////////////////////////////////////////forgotPassword////////////////////////////////

    //////////////////////////////////////////////////retrieve data user////////////////////////////////
    fun retrieveCurrentDataUser(userCallback: UserCallback) {
        getUserByUid(mAuth.currentUser.uid, userCallback)
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
        notification: Notification,
        responseCallback: AppointmentCallback
    ) {
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    for (ds in snapshot.children) {
                        var userItem = ds.getValue(UserItem::class.java)
                        var idUser = userItem!!.id
                        if (appointment.idPatient.equals(idUser)) {
                            var hour = HashMap<String, Appointment>()
                            var day = HashMap<String, HashMap<String, Appointment>>()
                            hour[appointment.hour.toString()] = appointment
                            day[appointment.date.toString()] = hour
                            userItem.rendezVous = day
                            userRef.child(userItem.id.toString())
                                .child("rendezVous").child(appointment.date.toString())
                                .child(appointment.hour.toString()).setValue(appointment)
                            notification.id = messageRef.push().key.toString()
                            notificationRef.child(notification.id!!).setValue(notification)
                            responseCallback.successAppointment(appointment)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("tag ", "insertingAppointment:failure" + error.toException())
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


    fun supprAppointment(
        date: String,
        hour: String,
        idDoc: String,
        idPat: String,
        responseCallback: AppointmentCallback
    ) {
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
                                    userRef.child(idDoc).child("rendezVous").child(date).child(hour)
                                        .removeValue()
                                    userRef.child(idPat).child("rendezVous").child(date).child(hour)
                                        .removeValue()


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
        idPatientRapport: String,
        idDoctorRapport: String,
        notification: Notification,
        responseCallback: ResponseCallback
    ) {
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                var rapport = HashMap<String, Rapports>()
                rapport[rapports.id.toString()] = rapports
                userItem.rapports = rapport

                rapports.id = userRef.push().key
//                userRef.child(idDoctorRapport).child("rapports").child(rapports.id!!)
//                    .setValue(rapports)
                responseCallback.success()
                userRef.child(idPatientRapport).child("rapports").child(rapports.id!!)
                    .setValue(rapports)

                notification.id = messageRef.push().key.toString()
                notificationRef.child(notification.id!!).setValue(notification)
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
                        if (userItem!!.role!!.size == 2) {
                            if (userItem.rapports != null) {
                                var rapport = userItem.rapports
                                for (entry in rapport!!.values) {
                                    responseCallback.success(entry)
                                }
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

    //////////////////////////////////////////updateRapport/////////////////////////////////////////
    fun updateRapport(
        iddoc: String,
        idPat: String,
        idRapport: String,
        rapports: Rapports,
        responseCallback: ResponseCallback
    ) {

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userRef.child(iddoc).child("rapports").child(idRapport).removeValue()
                userRef.child(iddoc).child("rapports").child(idRapport).setValue(rapports)

                userRef.child(idPat).child("rapports").child(idRapport).removeValue()
                userRef.child(idPat).child("rapports").child(idRapport).setValue(rapports)
                responseCallback.success()

            }

            override fun onCancelled(error: DatabaseError) {

            }


        })
    }

    //////////////////////////////////////////RemoveRapport/////////////////////////////////////////
    fun removeRapport(
        iddoc: String,
        idPat: String,
        idRapport: String,
        responseCallback: ResponseCallback
    ) {
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userRef.child(iddoc).child("rapports").child(idRapport).removeValue()
                userRef.child(idPat).child("rapports").child(idRapport).removeValue()
                responseCallback.success()

            }

            override fun onCancelled(error: DatabaseError) {
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
            }

        })


    }

    /////////////////////////////////////////////medicamentSearch/////////////////////////////////////
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

    /////////////////////////////////////////////insert Ordonance/////////////////////////////////////
    override fun insertordonance(
        idDoctor: String,
        idPatient: String,
        ordonance: Ordonance,
        userItem: UserItem,
        notification: Notification,
        ordonanceCallback: OrdonanceCallback
    ) {
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
        notification.id = messageRef.push().key.toString()
        notificationRef.child(notification.id!!).setValue(notification)
        ordonanceCallback.successOrdonance(ordonance)

    }

    ////////////////////////////////////////////remove ordonance/////////////////////////////////////
    fun removeOrdonance(
        iddoc: String,
        idPat: String,
        idOrdonance: String,
        responseCallback: ResponseCallback
    ) {

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userRef.child(iddoc).child("ordonance").child(idOrdonance).removeValue()
                userRef.child(idPat).child("ordonance").child(idOrdonance).removeValue()
                responseCallback.success()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    ////////////////////////////////////////////remove ordonance/////////////////////////////////////
    fun updateOrdonance(
        iddoc: String,
        idPat: String,
        idOrdonance: String,
        ordonance: Ordonance,
        responseCallback: ResponseCallback
    ) {

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                userRef.child(iddoc).child("ordonance").child(idOrdonance).removeValue()
                userRef.child(iddoc).child("ordonance").child(idOrdonance).setValue(ordonance)

                userRef.child(idPat).child("ordonance").child(idOrdonance).removeValue()
                userRef.child(idPat).child("ordonance").child(idOrdonance).setValue(ordonance)
                responseCallback.success()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    ////////////////////////////////////////////change password/////////////////////////////////////
    fun changePassword(
        password: String,
        userItem: UserItem,
        responseCallback: ResponseCallback
    ) {
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


    fun uploadImageToFirebase(uid: String, contentUri: Uri) {
        val fileName = UUID.randomUUID().toString() + ".jpg"
        val image = storageReference.child("pictures/$fileName")
        image.putFile(contentUri).addOnSuccessListener {
            image.downloadUrl.addOnSuccessListener { uri ->
                Log.d("tag", "onSuccess: Uploaded Image URl is $uri")
                userRef.child(uid).child("profilPhotos").setValue(uri.toString())

            }.addOnFailureListener {
                Log.d("tag", "onFailureMessage is $it")
            }
        }
    }


    ///////////////////////////////////////Send Message/////////////////////////////////////////////////
    fun sendMesage(message: Message) {
        message.id = messageRef.push().key.toString()
        messageRef.child(message.id!!).setValue(message)
    }

    fun getMessage(messageCallback: MessageCallback) {
        messageRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    for (ds in snapshot.children) {
                        var msg = ds.getValue(Message::class.java)
                        messageCallback.success(msg!!)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("tag", "mouadh  $error")
                messageCallback.failure(error)
            }
        })
    }

    //////////////////////////////////////////////get Notification//////////////////////////////////
    fun getNotification(notificationCallback: NotificationCallback) {
        notificationRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    var notification = ds.getValue(Notification::class.java)
                    notificationCallback.successNotification(notification)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                notificationCallback.failureNotification()
            }
        })
    }

    ///////////////////////////////////////////////get reclamation//////////////////////////////////
    fun getReclamation(reclamationCallback: ReclamationCallback) {
        reclamationRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    for (ds in snapshot.children) {
                        var reclamation = ds.getValue(Reclamation::class.java)
                        reclamationCallback.success(reclamation!!)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                reclamationCallback.failure()
            }
        })
    }

    fun supprReclamation(uid: String, reclamationCallback: ReclamationCallback) {
        reclamationRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                reclamationRef.child(uid).removeValue()
            }

            override fun onCancelled(error: DatabaseError) {
                reclamationCallback.failure()
            }
        })
    }

    fun addRole(uid: String, role: ArrayList<String>?) {
        userRef.child(uid).child("role").setValue(role)
    }

    fun supprDemande(uid: String, userCallback: UserCallback) {
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userRef.child(uid).child("demande").removeValue()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

    }

    ///////////////////////////////////////////Post send and get////////////////////////////////////////
    fun sendPost(
//        contentUri: Uri,
        publication: Publication) {
        publication.id = publicationRef.child(publication.idUser!!).push().key.toString()
        publicationRef.child(publication.idUser!!).child(publication.id!!).setValue(publication)
//        uploadImagePostToFirebase(publication.idUser!!, contentUri)
    }

    private fun uploadImagePostToFirebase(id: String, contentUri: Uri)  {

        val fileName = UUID.randomUUID().toString() + ".jpg"
        val image = storageReference.child("posts/$fileName")
        image.putFile(contentUri).addOnSuccessListener {
            image.downloadUrl.addOnSuccessListener { uri ->
                Log.d("tag", "onSuccess: Uploaded Image URl is $uri")
                publicationRef.child(id).child("imagePublication").setValue(uri.toString())


            }.addOnFailureListener {
                Log.d("tag", "onFailureMessage is $it")
            }
        }
    }

    fun getPost(postCallback: PostCallback) {
        publicationRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    for (post in ds.children) {
                        var pub = post.getValue(Publication::class.java)
                        postCallback.successPost(pub!!)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                postCallback.failurePost()
            }
        })
    }

    //////////////////////////////////////////send like////////////////////////////////////////////////
    fun sendLike(uid:String,publication: Publication, like: LikePost) {
        publicationRef.child(uid).child(publication.id!!).child("likes").setValue(like)
    }
    fun removeLike(uid:String,publication: Publication) {
        publicationRef.child(uid).child(publication.id!!).child("likes").child(getCurrentUserId()).removeValue()
    }


    fun removeLike(uid:String,likeCallback: LikeCallback) {
        publicationRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}




