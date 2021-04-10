package com.example.stagepfe.Fragments.Patient

import android.Manifest
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import com.example.stagepfe.Activity.Patients.BottomBarPatientActivity
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.UserItem
import com.google.android.gms.location.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*


class ModifyProfilePatientFragment : Fragment() {
    private var firstNameProfilPatientET: EditText? = null
    private var lastNameProfilPatientET: EditText? = null
    private var adresseProfilPatientET: EditText? = null
    private var dateNaissProfilPatientET: EditText? = null
    private var telephoneProfilPatientET: EditText? = null
    private var saveButtonPatient: Button? = null



    val patientCalendar = Calendar.getInstance()

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    val PERMISSION_ID = 1010
    lateinit var locationRequest: LocationRequest

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_modify_profile_p_atient, container, false)
        initView(view)
        return  view
    }

    private fun initView(view: View) {
        firstNameProfilPatientET = view.findViewById(R.id.FirstNameProfilPatient)
        lastNameProfilPatientET = view.findViewById(R.id.LastNameProfilPatient)
        adresseProfilPatientET = view.findViewById(R.id.AdresseProfilPatient)
        dateNaissProfilPatientET = view.findViewById(R.id.DateNaissProfilPatient)
        telephoneProfilPatientET = view.findViewById(R.id.TelephoneProfilPatient)
        saveButtonPatient = view.findViewById(R.id.SaveProfilButtonPatient)

        adresseProfilPatientET!!.isFocusable = false
        dateNaissProfilPatientET!!.isFocusable = false

///////////////////////////////////////////Button save//////////////////////////////////////////////
        saveButtonPatient!!.setOnClickListener {
            if (firstNameProfilPatientET!!.text.isEmpty() || lastNameProfilPatientET!!.text.isEmpty()
                || adresseProfilPatientET!!.text.isEmpty() || dateNaissProfilPatientET!!.text.isEmpty()
                || telephoneProfilPatientET!!.text.isEmpty()
            ) {
                var v = View.inflate(requireContext(), R.layout.fragment_dialog, null)
                var builder = AlertDialog.Builder(requireContext())
                builder.setView(v)

                var dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

                dialog.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
                    dialog.dismiss()
                }
            } else {
//var test: FirebaseUser = FirebaseAuth.getInstance().currentUser
//                if (test != null) {
//                    // User is signed in
//
//                    var user: UserItem = UserItem()
//                var userDao = UserDao()
//                user.nom = firstNameProfilPatientET!!.text.toString()
//                user.prenom = lastNameProfilPatientET!!.text.toString()
//                user.adresse = adresseProfilPatientET!!.text.toString()
//                user.datenaiss = dateNaissProfilPatientET!!.text.toString()
//                user.phonenumber = telephoneProfilPatientET!!.text.toString()
//
//
//                } else {
//                    // No user is signed in
//                }
//

                var v = View.inflate(
                    requireContext(),
                    R.layout.fragment_dialog,
                    null
                )
                var builder = AlertDialog.Builder(requireContext())
                builder.setView(v)

                var dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.findViewById<TextView>(R.id.TitleDialog)
                    .setText("Modification terminée avec succes")
                dialog.findViewById<ImageView>(R.id.CheckDialog)
                    .setBackgroundResource(R.drawable.ellipse_green)
                dialog.findViewById<ImageView>(R.id.CheckDialog).setImageResource(R.drawable.check)
                dialog.findViewById<TextView>(R.id.msgdialog).visibility = View.GONE

                dialog.findViewById<Button>(R.id.btn_confirm)
                    .setOnClickListener {
                        dialog.dismiss()

                        requireActivity().run {
                            var intent =
                                Intent(this, BottomBarPatientActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
            }


        }

////////////////////////////////////////Telephone EditText///////////////////////////////////////////
        telephoneProfilPatientET!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (telephoneProfilPatientET!!.length() < 8 || telephoneProfilPatientET!!.length() > 8) {
                    saveButtonPatient!!.isEnabled = false
                    saveButtonPatient!!.setBackgroundResource(R.drawable.gray_button)

                    telephoneProfilPatientET!!.error = "le numero n'existe pas"
                } else {
                    saveButtonPatient!!.isEnabled = true
                    saveButtonPatient!!.setBackgroundResource(R.drawable.button_style_smaller)

                }
            }

        })

////////////////////////////////////////Calendar EditText///////////////////////////////////////////
        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
            patientCalendar[Calendar.YEAR] = year
            patientCalendar[Calendar.MONTH] = monthOfYear
            patientCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
            updateLabel()
        }
        dateNaissProfilPatientET!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                // TODO Auto-generated method stub
                DatePickerDialog(
                    requireContext(),
                    date,
                    patientCalendar[Calendar.YEAR],
                    patientCalendar[Calendar.MONTH],
                    patientCalendar[Calendar.DAY_OF_MONTH]
                ).show()
            }
        })

/////////////////////////////////////////Location EditText//////////////////////////////////////////
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())

        adresseProfilPatientET!!.setOnClickListener {
            Log.d("Debug:", checkPermission().toString())
            Log.d("Debug:", isLocationEnabled(requireContext()).toString())
            RequestPermission()
//            fusedLocationProviderClient.lastLocation.addOnSuccessListener{location: Location? ->
//                 textView.text = location?.latitude.toString() + "," + location?.longitude.toString()
            getLastLocation()
        }

    }
//////////////////////////////////////////Calendar Function////////////////////////////////////////
    private fun updateLabel() {
        val myFormat = "MM/dd/yy" //In which you need put here

        val sdf = SimpleDateFormat(myFormat, Locale.US)

        dateNaissProfilPatientET!!.setText(sdf.format(patientCalendar.time))        }
/////////////////////////////////////////////Location Function//////////////////////////////////////
fun getLastLocation() {
    if (checkPermission()) {
        if (isLocationEnabled(requireContext())) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
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
                    adresseProfilPatientET!!.setText(getCityName(location.latitude, location.longitude))
                }
            }
        } else {
            Toast.makeText(
                requireContext(),
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
            LocationServices.getFusedLocationProviderClient(requireContext())
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
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

            adresseProfilPatientET!!.setText(
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

        var geoCoder = Geocoder(requireContext(), Locale.getDefault())
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
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                requireContext(),
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
            requireActivity(),
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
////////////////////////////////////////////////////////////////////////////////////////////////////
}