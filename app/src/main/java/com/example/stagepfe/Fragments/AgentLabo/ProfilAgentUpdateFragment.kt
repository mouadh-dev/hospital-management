package com.example.stagepfe.Fragments.AgentLabo

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
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import com.example.stagepfe.Activity.Doctors.AccountDoctorActivity
import com.example.stagepfe.R
import com.google.android.gms.location.*
import java.text.SimpleDateFormat
import java.util.*


class ProfilAgentUpdateFragment : Fragment() {

    private var firstNameProfilAgentET: EditText? = null
    private var secondNameProfilAgentET: EditText? = null
    private var adresseProfilAgentET: EditText? = null
    private var dateNaissProfilAgentET: EditText? = null
    private var telephoneProfilAgentET: EditText? = null
    private var showNewPasswordAgentIV: ImageView? = null
    private var showConfirmNewPasswordAgentIV: ImageView? = null
    private var newPasswordAgentET: EditText? = null
    private var confirmNewPasswordAgentET: EditText? = null
    private var saveProfilAgentButton: Button? = null
    private var returnProfilAgentButton: Button? = null


    val myProfilAgentCalendar = Calendar.getInstance()

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    val PERMISSION_ID = 1010
    lateinit var locationRequest: LocationRequest



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view=  inflater.inflate(R.layout.fragment_profil_agent_update, container, false)
        initView(view)
        return  view
    }

    private fun initView(view: View) {
        firstNameProfilAgentET = view.findViewById(R.id.FirstNameProfilAgent)
        secondNameProfilAgentET = view.findViewById(R.id.SecondNameProfilAgent)
        adresseProfilAgentET = view.findViewById(R.id.AdresseProfilAgent)
        dateNaissProfilAgentET = view.findViewById(R.id.DateNaissProfilAgent)
        telephoneProfilAgentET = view.findViewById(R.id.TelephoneProfilAgent)
        showNewPasswordAgentIV = view.findViewById(R.id.Eye_Show_newPassword_Agent)
        showConfirmNewPasswordAgentIV = view.findViewById(R.id.Eye_Show_ConfirmnewPassword_Agent)
        newPasswordAgentET = view.findViewById(R.id.NewPasswordAgent)
        confirmNewPasswordAgentET = view.findViewById(R.id.ConfirmNewPasswordAgent)
        saveProfilAgentButton = view.findViewById<Button>(R.id.SaveProfilAgentButton)
        returnProfilAgentButton = view.findViewById<Button>(R.id.ReturnbuttonProfilAgent)

        adresseProfilAgentET!!.isFocusable = false
        dateNaissProfilAgentET!!.isFocusable = false

        ///////////////////////////////////////////////////////////////////////////////////////////////////

        //*****************************************password***********************************************
        newPasswordAgentET!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (newPasswordAgentET!!.length() <= 8){
                    saveProfilAgentButton!!.isEnabled = false
                    saveProfilAgentButton!!.setBackgroundResource(R.drawable.gray_button)
                    newPasswordAgentET!!.error = "le mot de passe est faible "
                }else{
                    saveProfilAgentButton!!.isEnabled = true
                    saveProfilAgentButton!!.setBackgroundResource(R.drawable.button_style_smaller)

                }
            }
        })
        showNewPasswordAgentIV!!.setOnClickListener {
            if (newPasswordAgentET!!.getTransformationMethod()
                    .equals(PasswordTransformationMethod.getInstance())
            ) {
                newPasswordAgentET!!.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                newPasswordAgentET!!.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }
        confirmNewPasswordAgentET!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (notequal()){
                    saveProfilAgentButton!!.isEnabled = false
                    saveProfilAgentButton!!.setBackgroundResource(R.drawable.gray_button)
                    confirmNewPasswordAgentET!!.error = "le mot de passe ne correspond pas"
                }else{
                    saveProfilAgentButton!!.isEnabled = true
                    saveProfilAgentButton!!.setBackgroundResource(R.drawable.button_style_smaller)

                }
            }


        })

        showConfirmNewPasswordAgentIV!!.setOnClickListener{
            if(confirmNewPasswordAgentET!!.getTransformationMethod().equals(PasswordTransformationMethod.getInstance()))
            {
                confirmNewPasswordAgentET!!.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }else {
                confirmNewPasswordAgentET!!.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())

        adresseProfilAgentET!!.setOnClickListener {
            Log.d("Debug:", checkPermission().toString())
            Log.d("Debug:", isLocationEnabled(requireContext()).toString())
            RequestPermission()
//            fusedLocationProviderClient.lastLocation.addOnSuccessListener{location: Location? ->
//                 textView.text = location?.latitude.toString() + "," + location?.longitude.toString()
            getLastLocation()
        }

        saveProfilAgentButton!!.setOnClickListener {
            if (firstNameProfilAgentET!!.text.isEmpty() && secondNameProfilAgentET!!.text.isEmpty()
                &&  adresseProfilAgentET!!.text.isEmpty() &&  dateNaissProfilAgentET!!.text.isEmpty()
                &&  newPasswordAgentET!!.text.isEmpty() &&  confirmNewPasswordAgentET!!.text.isEmpty()
                &&  telephoneProfilAgentET!!.text.isEmpty()
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
                                Intent(this, AccountDoctorActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
            }


        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////

        //*****************************************calender***********************************************
        telephoneProfilAgentET!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (telephoneProfilAgentET!!.length() < 8 || telephoneProfilAgentET!!.length() > 8) {
                    saveProfilAgentButton!!.isEnabled = false
                    saveProfilAgentButton!!.setBackgroundResource(R.drawable.gray_button)

                    saveProfilAgentButton!!.error = "le numero n'existe pas"
                } else {
                    saveProfilAgentButton!!.isEnabled = true
                    saveProfilAgentButton!!.setBackgroundResource(R.drawable.button_style_smaller)

                }
            }

        })
        ///////////////////////////////////////////////////////////////////////////////////////////////////

        //*****************************************calender***********************************************
        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
            myProfilAgentCalendar[Calendar.YEAR] = year
            myProfilAgentCalendar[Calendar.MONTH] = monthOfYear
            myProfilAgentCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
            updateLabel()
        }
        dateNaissProfilAgentET!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                // TODO Auto-generated method stub
                DatePickerDialog(
                    requireContext(),
                    date,
                    myProfilAgentCalendar[Calendar.YEAR],
                    myProfilAgentCalendar[Calendar.MONTH],
                    myProfilAgentCalendar[Calendar.DAY_OF_MONTH]
                ).show()
            }
        })

////////////////////////////////////////////////////////////////////////////////////////////////////////

        //*****************************************calender function*******************************************
    }



    private fun updateLabel() {
        val myFormat = "MM/dd/yy" //In which you need put here

        val sdf = SimpleDateFormat(myFormat, Locale.US)

        dateNaissProfilAgentET!!.setText(sdf.format(myProfilAgentCalendar.time))    }



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
                        adresseProfilAgentET!!.setText(getCityName(location.latitude, location.longitude))
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

            adresseProfilAgentET!!.setText(
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
    private fun notequal(): Boolean {
        return confirmNewPasswordAgentET!!.text.toString() != newPasswordAgentET!!.text.toString()

    }
}