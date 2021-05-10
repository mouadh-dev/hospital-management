package com.example.stagepfe.Activity.Pharmacien

import android.Manifest
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.UserItem
import com.google.android.gms.location.*
import java.text.SimpleDateFormat
import java.util.*

class ChangeInformationPharmacienActivity : AppCompatActivity() {

    var moveBackIcon: ImageView? = null
    var saveUpdate: Button? = null
    private var firstNameProfilPharmacienET: EditText? = null
    private var secondNameProfilPharmacienET: EditText? = null
    private var adresseProfilPharmacienET: EditText? = null
    private var dateNaissProfilPharmacienET: EditText? = null
    private var telephoneProfilPharmacienET: EditText? = null


    private var returnProfilButton: Button? = null
    val myProfilPharmacienCalendar = Calendar.getInstance()
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    val PERMISSION_ID = 1010
    lateinit var locationRequest: LocationRequest
    var text: String = ""
    var userDao = UserDao()
    var user = UserItem()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_information_pharmacien)
        initView()
    }

    private fun initView() {
        moveBackIcon = findViewById(R.id.back_modifyProfilePharmacien)
        saveUpdate = findViewById(R.id.SaveProfilPharmacienButton)
        firstNameProfilPharmacienET = findViewById(R.id.FirstNameProfilPharmacien)
        secondNameProfilPharmacienET = findViewById(R.id.SecondNameProfilPharmacien)
        adresseProfilPharmacienET = findViewById(R.id.AdresseProfilPharmacien)
        dateNaissProfilPharmacienET = findViewById(R.id.DateNaissProfilPharmacien)
        telephoneProfilPharmacienET = findViewById(R.id.TelephoneProfilPharmacien)

        returnProfilButton = findViewById<Button>(R.id.ReturnbuttonProfilDoctor)


        adresseProfilPharmacienET!!.isFocusable = false
        dateNaissProfilPharmacienET!!.isFocusable = false

////////////////////////////////////////////////////////////////////////////////////////////////////

        userDao.retrieveCurrentDataUser(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                firstNameProfilPharmacienET!!.setText(userItem.nom)
                secondNameProfilPharmacienET!!.setText(userItem.prenom)
                dateNaissProfilPharmacienET!!.setText(userItem.datenaiss)
                telephoneProfilPharmacienET!!.setText(userItem.phonenumber)
                adresseProfilPharmacienET!!.setText(userItem.adresse)
                var groupesanguin = userItem.groupesanguin.toString()
                var id = userItem.id.toString()
                var mail = userItem.mail.toString()
                var matricule = userItem.matricule.toString()
                var numCIN = userItem.numCIN.toString()
                var rendezVous = userItem.rendezVous
                var role = userItem.role
                var sexe = userItem.sexe.toString()
                var speciality = userItem.speciality
                var password = userItem.password
                var confirmpassword = userItem.confirmpassword
                var maladi = userItem.maladi.toString()
                var  medicament = userItem.medicament
                var ordonance = userItem.ordonance
                var rapport = userItem.rapports
                ////


                saveUpdate!!.setOnClickListener {
                    user.nom = firstNameProfilPharmacienET!!.text.toString()
                    user.prenom = secondNameProfilPharmacienET!!.text.toString()
                    user.datenaiss = dateNaissProfilPharmacienET!!.text.toString()
                    user.phonenumber = telephoneProfilPharmacienET!!.text.toString()
                    user.adresse = adresseProfilPharmacienET!!.text.toString()
                    user.id = id
                    user.mail = mail
                    user.matricule = matricule
                    user.numCIN = numCIN
                    user.rendezVous = rendezVous
                    user.role = role
                    user.sexe = sexe
                    user.speciality = speciality
                    user.password = password
                    user.confirmpassword = confirmpassword
                    user.maladi = maladi
                    user.medicament = medicament
                    user.ordonance = ordonance
                    user.rapports = rapport
                    user.groupesanguin = groupesanguin

                    text = "Modification terminée avec succes"
                    dialog(text)
                    userDao.updateUser(user.id.toString(),user, object : UserCallback {
                        override fun onSuccess(user: UserItem) {

                        }

                        override fun failure() {

                        }
                    })

                }

            }

            override fun failure() {
            }

        })


////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        moveBackIcon!!.setOnClickListener {
            finish()
        }
        returnProfilButton!!.setOnClickListener {

            finish()

        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this)
        adresseProfilPharmacienET!!.setOnClickListener {
            Log.d("Debug:", checkPermission().toString())
            Log.d("Debug:", isLocationEnabled(this).toString())
            RequestPermission()
//            fusedLocationProviderClient.lastLocation.addOnSuccessListener{location: Location? ->
//                 textView.text = location?.latitude.toString() + "," + location?.longitude.toString()
            getLastLocation()
        }
///////////////////////////////////////////////////////////////////////////////////////////////////
//*****************************************phoneNumber***********************************************
        telephoneProfilPharmacienET!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (telephoneProfilPharmacienET!!.length() < 8 || telephoneProfilPharmacienET!!.length() > 8) {
                    saveUpdate!!.isEnabled = false
                    saveUpdate!!.setBackgroundResource(R.drawable.gray_button)

                    saveUpdate!!.error = "le numero n'existe pas"
                } else {
                    saveUpdate!!.isEnabled = true
                    saveUpdate!!.setBackgroundResource(R.drawable.button_style_smaller)

                }
            }

        })
///////////////////////////////////////////////////////////////////////////////////////////////////
//*****************************************calender***********************************************
        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
            myProfilPharmacienCalendar[Calendar.YEAR] = year
            myProfilPharmacienCalendar[Calendar.MONTH] = monthOfYear
            myProfilPharmacienCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
            updateLabel()
        }
//*****************************************dateNaiss***********************************************
        dateNaissProfilPharmacienET!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                // TODO Auto-generated method stub
                DatePickerDialog(
                    this@ChangeInformationPharmacienActivity,
                    date,
                    myProfilPharmacienCalendar[Calendar.YEAR],
                    myProfilPharmacienCalendar[Calendar.MONTH],
                    myProfilPharmacienCalendar[Calendar.DAY_OF_MONTH]
                ).show()
            }
        })
////////////////////////////////////////////////////////////////////////////////////////////////////////
    }
    private fun dialog(text: String){
        var v = View.inflate(
            this,
            R.layout.fragment_dialog,
            null
        )
        var builder = AlertDialog.Builder(this)
        builder.setView(v)

        var dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.findViewById<TextView>(R.id.TitleDialog)
            .setText(text)
        dialog.findViewById<ImageView>(R.id.CheckDialog)
            .setBackgroundResource(R.drawable.ellipse_green)
        dialog.findViewById<ImageView>(R.id.CheckDialog).setImageResource(R.drawable.check)
        dialog.findViewById<TextView>(R.id.msgdialog).visibility = View.GONE

        dialog.findViewById<Button>(R.id.btn_confirm)
            .setOnClickListener {
                dialog.dismiss()


                finish()
            }
    }
    //////////////////////////////////////////////////Location/////////////////////////////////////////
    private fun updateLabel() {
        val myFormat = "MM/dd/yy" //In which you need put here

        val sdf = SimpleDateFormat(myFormat, Locale.US)

        dateNaissProfilPharmacienET!!.setText(sdf.format(myProfilPharmacienCalendar.time))
    }
    //////////////////////////////////////////////////Location/////////////////////////////////////////
    fun getLastLocation() {
        if (checkPermission()) {
            if (isLocationEnabled(this)) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        NewLocationData()
                    } else {
                        Log.d("Debug:", "Your Location:" + location.longitude)
                        adresseProfilPharmacienET!!.setText(
                            getCityName(
                                location.latitude,
                                location.longitude
                            )
                        )
                    }
                }
            } else {
                Toast.makeText(
                    this,
                    "Please Turn on Your device Location",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        } else {
            RequestPermission()
        }
    }
    fun NewLocationData() {
        var locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProviderClient!!.requestLocationUpdates(
            locationRequest, locationCallback, Looper.myLooper()
        )
    }
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var lastLocation: Location = locationResult.lastLocation

            Log.d("Debug:", lastLocation.longitude.toString())

            adresseProfilPharmacienET!!.setText(
                getCityName(
                    lastLocation.latitude,
                    lastLocation.longitude
                )
            )

        }
    }
    private fun getCityName(lat: Double, long: Double): String {
        var address: String = ""
        var cityName: String = ""
        var countryName = ""
        var knownName = ""

        var geoCoder = Geocoder(this, Locale.getDefault())
        var adress = geoCoder.getFromLocation(lat, long, 3)

        cityName = adress[0].locality
        countryName = adress[0].countryName
        Log.d("Debug:", "$cityName ; your Country $countryName")
        return "$cityName,$countryName"
    }
    fun checkPermission(): Boolean {
        //this function will return a boolean
        //true: if we have permission
        //false if not
        if (
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }

        return false
    }
    fun RequestPermission() {
        //this function will allows us to tell the user to requesut the necessary permsiion if they are not garented
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }
    fun isLocationEnabled(mContext: Context): Boolean {
        //this function will return to us the state of the location service
        //if the gps or the network provider is enabled then it will return true otherwise it will return false
//        var locationManager = getSystemService(requireContext(),Context.LOCATION_SERVICE) as LocationManager
        var locationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Debug:", "You have the Permission")
            }
        }
    }

}