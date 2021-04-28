package com.example.stagepfe.Fragments.Doctor

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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.stagepfe.Activity.Doctors.AccountDoctorActivity
import com.example.stagepfe.Activity.Doctors.DoctorProfilActivity
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.UserItem
import com.google.android.gms.location.*
import java.text.SimpleDateFormat
import java.util.*

class DoctorProfileUpdateFragment : Fragment() {
    private var firstNameProfilDoctorET: EditText? = null
    private var secondNameProfilDoctorET: EditText? = null
    private var adresseProfilDoctorET: EditText? = null
    private var dateNaissProfilDoctorET: EditText? = null
    private var telephoneProfilDoctorET: EditText? = null
    private var newPasswordDoctorET: EditText? = null
    private var bioDoctorET: EditText? = null
    private var confirmNewPasswordDoctorET: EditText? = null
    private var saveProfilButton: Button? = null
    private var returnProfilButton: Button? = null
    private var showNewPasswordIV: ImageView? = null
    private var showNewPassworConfirmdIV: ImageView? = null


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
        var view = inflater.inflate(R.layout.fragment_doctor_profile_update, container, false)
        initView(view)
        return view


    }

    private fun initView(view: View) {
        firstNameProfilDoctorET = view.findViewById(R.id.FirstNameProfilDoctor)
        secondNameProfilDoctorET = view.findViewById(R.id.SecondNameProfilDoctor)
        adresseProfilDoctorET = view.findViewById(R.id.AdresseProfilDoctor)
        dateNaissProfilDoctorET = view.findViewById(R.id.DateNaissProfilDoctor)
        telephoneProfilDoctorET = view.findViewById(R.id.TelephoneProfilDoctor)
        newPasswordDoctorET = view.findViewById(R.id.NewPasswordDoctor)
        confirmNewPasswordDoctorET = view.findViewById(R.id.ConfirmNewPasswordDoctor)
        bioDoctorET = view.findViewById(R.id.DescriptionProfileDoctor)
        saveProfilButton = view.findViewById<Button>(R.id.SaveProfilDoctorButton)
        returnProfilButton = view.findViewById<Button>(R.id.ReturnbuttonProfilDoctor)
        showNewPasswordIV = view.findViewById(R.id.Eye_Show_newPassword)
        showNewPassworConfirmdIV = view.findViewById(R.id.Eye_Show_ConfirmnewPassword_Doctor)

        adresseProfilDoctorET!!.isFocusable = false
        dateNaissProfilDoctorET!!.isFocusable = false


////////////////////////////////////////////////////////////////////////////////////////////////////
        var userDao = UserDao()
        var userItem = UserItem()
        userDao.retrieveCurrentDataUser(requireActivity(),
            UserItem(),
            object : UserCallback {
                override fun onSuccess(userItem: UserItem) {
                    firstNameProfilDoctorET!!.setText(userItem.nom)
                    secondNameProfilDoctorET!!.setText(userItem.prenom)
                    dateNaissProfilDoctorET!!.setText(userItem.datenaiss)
                    telephoneProfilDoctorET!!.setText(userItem.phonenumber)
                    adresseProfilDoctorET!!.setText(userItem.adresse)
                    bioDoctorET!!.setText(userItem.bio)
                }

                override fun failure() {
                }

            })

////////////////////////////////////////////////////////////////////////////////////////////////////
        saveProfilButton!!.setOnClickListener {
            if (firstNameProfilDoctorET!!.text.isEmpty() && secondNameProfilDoctorET!!.text.isEmpty()
                && adresseProfilDoctorET!!.text.isEmpty() && dateNaissProfilDoctorET!!.text.isEmpty()
                && telephoneProfilDoctorET!!.text.isEmpty() && bioDoctorET!!.text.isEmpty()
                && newPasswordDoctorET!!.text.isEmpty() && confirmNewPasswordDoctorET!!.text.isEmpty()
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
            }
            else {
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
////////////////////////////////////////////////////////////////////////////////////////////////////
        returnProfilButton!!.setOnClickListener {
            requireActivity().run {
                startActivity(Intent(this, DoctorProfilActivity::class.java))
                finish() // If activity no more needed in back stack
            }
        }

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        adresseProfilDoctorET!!.setOnClickListener {
            Log.d("Debug:", checkPermission().toString())
            Log.d("Debug:", isLocationEnabled(requireContext()).toString())
            RequestPermission()
//            fusedLocationProviderClient.lastLocation.addOnSuccessListener{location: Location? ->
//                 textView.text = location?.latitude.toString() + "," + location?.longitude.toString()
            getLastLocation()
        }


///////////////////////////////////////////////////////////////////////////////////////////////////
//*****************************************password***********************************************
        newPasswordDoctorET!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (newPasswordDoctorET!!.length() <= 8) {
                    saveProfilButton!!.isEnabled = false
                    saveProfilButton!!.setBackgroundResource(R.drawable.gray_button)
                    newPasswordDoctorET!!.error = "le mot de passe est faible "
                } else {

                    saveProfilButton!!.setBackgroundResource(R.drawable.button_style_smaller)

                }
            }
        })
        showNewPasswordIV!!.setOnClickListener {
            if (newPasswordDoctorET!!.getTransformationMethod()
                    .equals(PasswordTransformationMethod.getInstance())
            ) {
                newPasswordDoctorET!!.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            } else {
                newPasswordDoctorET!!.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            }
        }
////////////////////////////////////////////////////////////////////////////////////////////////////
        confirmNewPasswordDoctorET!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (notequal()) {
                    saveProfilButton!!.setBackgroundResource(R.drawable.gray_button)
                    confirmNewPasswordDoctorET!!.error = "le mot de passe ne correspond pas"
                } else {
                    saveProfilButton!!.isEnabled = true
                    saveProfilButton!!.setBackgroundResource(R.drawable.button_style_smaller)

                }
            }


        })
        showNewPassworConfirmdIV!!.setOnClickListener {
            if (confirmNewPasswordDoctorET!!.getTransformationMethod()
                    .equals(PasswordTransformationMethod.getInstance())
            ) {
                confirmNewPasswordDoctorET!!.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            } else {
                confirmNewPasswordDoctorET!!.transformationMethod =
                    PasswordTransformationMethod.getInstance()
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

        dateNaissProfilDoctorET!!.setText(sdf.format(myProfilDoctorCalendar.time))
    }

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
                        adresseProfilDoctorET!!.setText(
                            getCityName(
                                location.latitude,
                                location.longitude
                            )
                        )
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
    private fun notequal(): Boolean {
        return confirmNewPasswordDoctorET!!.text.toString() != newPasswordDoctorET!!.text.toString()
    }
}