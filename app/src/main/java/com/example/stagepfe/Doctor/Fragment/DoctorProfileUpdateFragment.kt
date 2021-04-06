package com.example.stagepfe.Doctor.Fragment

import android.Manifest
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
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
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import com.example.stagepfe.R
import com.google.android.gms.location.*
import java.security.AccessController.checkPermission
import java.text.SimpleDateFormat
import java.util.*

class DoctorProfileUpdateFragment : Fragment() {
    private var firstNameProfilDoctorET: EditText? = null
    private var secondNameProfilDoctorET: EditText? = null
    private var adresseProfilDoctorET: EditText? = null
    private var dateNaissProfilDoctorET: EditText? = null
    private var telephoneProfilDoctorET: EditText? = null
    private var descriptionProfilDoctorET: EditText? = null
    private var saveProfilButton: Button? = null


    val myProfilDoctorCalendar = Calendar.getInstance()

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    val PERMISSION_ID = 1010
    lateinit var locationRequest: LocationRequest

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      var view= inflater.inflate(R.layout.fragment_doctor_profile_update, container, false)
        initView(view)
        return  view
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())

        adresseProfilDoctorET!!.setOnClickListener {
            Log.d("Debug:", checkPermission().toString())
            Log.d("Debug:", isLocationEnabled(requireContext()).toString())
            RequestPermission()
//            fusedLocationProviderClient.lastLocation.addOnSuccessListener{location: Location? ->
//                 textView.text = location?.latitude.toString() + "," + location?.longitude.toString()
            getLastLocation()
        }

    }

    private fun initView(view: View) {
        firstNameProfilDoctorET = view.findViewById(R.id.FirstNameProfilDoctor)
        secondNameProfilDoctorET = view.findViewById(R.id.SecondNameProfilDoctor)
        adresseProfilDoctorET = view.findViewById(R.id.DateNaissProfilDoctor)
        dateNaissProfilDoctorET = view.findViewById(R.id.AdresseProfilDoctor)
        telephoneProfilDoctorET = view.findViewById(R.id.TelephoneProfilDoctor)
        descriptionProfilDoctorET = view.findViewById(R.id.DescriptionProfileDoctor)
        saveProfilButton = view.findViewById<Button>(R.id.SaveProfilDoctorButton)

        adresseProfilDoctorET!!.isFocusable = false
        dateNaissProfilDoctorET!!.isFocusable = false

        saveProfilButton!!.setOnClickListener {
            if (firstNameProfilDoctorET!!.text.isEmpty() || secondNameProfilDoctorET!!.text.isEmpty()
                || adresseProfilDoctorET!!.text.isEmpty() || dateNaissProfilDoctorET!!.text.isEmpty()
                || descriptionProfilDoctorET!!.text.isEmpty()
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

            }
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////

        //*****************************************calender***********************************************
        telephoneProfilDoctorET!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (telephoneProfilDoctorET!!.length() < 8 || telephoneProfilDoctorET!!.length() > 8) {
                    saveProfilButton!!.isEnabled = false
                    saveProfilButton!!.setBackgroundResource(R.drawable.gray_button)

                    saveProfilButton!!.error = "le numero n'existe pas"
                } else {
                    saveProfilButton!!.isEnabled = true
                    saveProfilButton!!.setBackgroundResource(R.drawable.button_style_smaller)

                }
            }

        })
        ///////////////////////////////////////////////////////////////////////////////////////////////////

        //*****************************************calender***********************************************
        val date =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
                myProfilDoctorCalendar[Calendar.YEAR] = year
                myProfilDoctorCalendar[Calendar.MONTH] = monthOfYear
                myProfilDoctorCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                updateLabel()
            }
        dateNaissProfilDoctorET!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                // TODO Auto-generated method stub
                DatePickerDialog(
                    requireContext(),
                    date,
                    myProfilDoctorCalendar[Calendar.YEAR],
                    myProfilDoctorCalendar[Calendar.MONTH],
                    myProfilDoctorCalendar[Calendar.DAY_OF_MONTH]
                ).show()
            }
        })

////////////////////////////////////////////////////////////////////////////////////////////////////////

        //*****************************************calender function*******************************************
    }
    private fun updateLabel() {
        val myFormat = "MM/dd/yy" //In which you need put here

        val sdf = SimpleDateFormat(myFormat, Locale.US)

        dateNaissProfilDoctorET!!.setText(sdf.format(myProfilDoctorCalendar.time))    }

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
                        adresseProfilDoctorET!!.setText(getCityName(location.latitude, location.longitude))
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

            adresseProfilDoctorET!!.setText(
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
/////////////////////////////////////////////////////////////////////////////////////////////////

}