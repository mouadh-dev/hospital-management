package com.example.stagepfe.Fragment

import android.Manifest
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.stagepfe.entite.UserItem
import com.example.stagepfe.R
import com.google.android.gms.location.*
import java.text.SimpleDateFormat
import java.util.*


class InscriptionSecondPageFragment : Fragment() {

    private var buttonReturn: Button? = null
    private var buttonNext: Button? = null
    private var adresseET: EditText? = null
    private var dateNaiss: EditText? = null
    private var phoneNumber: EditText? = null
    private var bloodGroup: Spinner? = null
    private var male: RadioButton? = null
    private var female: RadioButton? = null
    private var noChoice: RadioButton? = null
    private var sexeGroup: RadioGroup? = null
    private var pickerDate: DatePicker? = null
    private var msg: String? = null
    val myCalendar = Calendar.getInstance()

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    val PERMISSION_ID = 1010
    lateinit var locationRequest: LocationRequest


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_inscription_second_page, container, false)
        initView(view)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        adresseET!!.setOnClickListener {
            Log.d("Debug:", checkPermission().toString())
            Log.d("Debug:", isLocationEnabled(requireContext()).toString())
            RequestPermission()
            /* fusedLocationProviderClient.lastLocation.addOnSuccessListener{location: Location? ->
                 textView.text = location?.latitude.toString() + "," + location?.longitude.toString()
             }*/
            getLastLocation()
        }

        bloodGroup!!.adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.Group_Sanguin)
        ) as SpinnerAdapter

        return view
    }

    private fun initView(view: View) {
        buttonReturn = view.findViewById<Button>(R.id.ReurnbuttonSecondePage)
        buttonNext = view.findViewById<Button>(R.id.NextButtonSecondePage)
        adresseET = view.findViewById(R.id.InscriptionAdresseSecondPage)
        dateNaiss = view.findViewById(R.id.InscriptionDateSecondPage)
        phoneNumber = view.findViewById(R.id.InscriptionPhoneNumberSecondPage)
        bloodGroup = view.findViewById(R.id.InscriptionBloodSecondPage)
        male = view.findViewById<RadioButton>(R.id.FirstRadioButtonSecondeInscription)
        female = view.findViewById<RadioButton>(R.id.SecondeRadioButtonSecondeInscription)
        noChoice = view.findViewById<RadioButton>(R.id.ThirdRadioButtonSecondeInscription)
//        pickerDate = view.findViewById<DatePicker>(R.id.date_Picker)
        sexeGroup = view.findViewById<RadioGroup>(R.id.Radio_Group_secondPage)

        adresseET!!.isFocusable = false
        dateNaiss!!.isFocusable = false


        buttonReturn!!.setOnClickListener {
            var firstPage = FragmentInscriptionFirstPage()
            fragmentManager!!.beginTransaction()
                .replace(R.id.ContainerFragmentLayout, firstPage)
                .commit()
        }

        buttonNext!!.setOnClickListener {
            if (adresseET!!.text.isEmpty() || dateNaiss!!.text.isEmpty() || phoneNumber!!.text.isEmpty() ) {


                var v = View.inflate(requireContext(), R.layout.fragment_dialog, null)
                var builder = AlertDialog.Builder(requireContext())
                builder.setView(v)

                var dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

                dialog.findViewById<Button>(R.id.btn_confirm)!!.setOnClickListener {
                    dialog.dismiss()
                }
            }
            else if (!male!!.isChecked && !female!!.isChecked && !noChoice!!.isChecked) {
                var v = View.inflate(requireContext(), R.layout.fragment_dialog, null)
                var builder = AlertDialog.Builder(requireContext())
                builder.setView(v)

                var dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.findViewById<TextView>(R.id.TitleDialog)!!.setText("definissez votre sexe")
                dialog.findViewById<Button>(R.id.btn_confirm)!!.setOnClickListener {
                    dialog.dismiss()
                }

            } else {
                var choosePosition = ChoosePositionFragment()
                var bundle= Bundle()
                var user: UserItem = arguments!!.get("datafirstpage") as UserItem

                user.adresse =adresseET!!.text.trim().toString()
                user.datenaiss=dateNaiss!!.text.trim().toString()
                user.phonenumber=phoneNumber!!.text.trim().toString()
                user.sexe= sexeChoose(sexeGroup)
                user.groupesanguin= bloodGroup!!.selectedItem.toString()


                bundle.putParcelable("datasecondpage", user)
                choosePosition.arguments = bundle

                println("mouadh" + user.toString())
                fragmentManager!!.beginTransaction()
                    .replace(R.id.ContainerFragmentLayout, choosePosition).commit()
            }
        }

///////////////////////////////////////////////////////////////////////////////////////////////////

//*****************************************calender***********************************************





        val date =
            OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = monthOfYear
                myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                updateLabel()
            }
        dateNaiss!!.setOnClickListener( object : View.OnClickListener {
            override fun onClick(v: View?) {
                // TODO Auto-generated method stub
                DatePickerDialog(
                    requireContext(), date, myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH],
                    myCalendar[Calendar.DAY_OF_MONTH]
                ).show()
            }
        })



///////////////////////////////////////////////////////////////////////////////////////////////////

//*************************************phone number case*****************************************

        phoneNumber!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (phoneNumber!!.length() < 8 || phoneNumber!!.length() > 8) {
                    buttonNext!!.isEnabled = false
                    buttonNext!!.setBackgroundResource(R.drawable.gray_button)

                    phoneNumber!!.error = "le numero n'existe pas"
                } else {
                    buttonNext!!.isEnabled = true
                    buttonNext!!.setBackgroundResource(R.drawable.button_style_smaller)

                }
            }

        })


    }

    private fun sexeChoose(sexeGroup: RadioGroup?): String? {
        if (male!!.isChecked){
            return male!!.text.toString()
        }else if (female!!.isChecked){
            return female!!.text.toString()
        }else{
            return noChoice!!.text.toString()
        }

    }


////////////////////////////////////////////////////////////////////////////////////////////////////////

//*****************************************calender function*******************************************

    private fun updateLabel() {
        val myFormat = "MM/dd/yy" //In which you need put here

        val sdf = SimpleDateFormat(myFormat, Locale.US)

        dateNaiss!!.setText(sdf.format(myCalendar.time))    }


//*****************************************location map*********************************************

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
                        adresseET!!.setText(getCityName(location.latitude, location.longitude))
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

            adresseET!!.setText(
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

