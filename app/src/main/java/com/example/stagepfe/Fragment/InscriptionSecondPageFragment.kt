package com.example.stagepfe.Fragment

import android.Manifest
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
import com.example.stagepfe.R
import com.google.android.gms.location.*
import java.util.*


class InscriptionSecondPageFragment : Fragment() {

    private var ButtonReturn: Button? = null
    private var ButtonNext: Button? = null
    private var Adresse: EditText? = null
    private var DateNaiss: EditText? = null
    private var PhoneNumber: EditText? = null
    private var BloodGroup: EditText? = null
    private var Male: RadioButton? = null
    private var Female: RadioButton? = null
    private var NoChoice: RadioButton? = null
    private var Sexe: RadioGroup? = null
    private var PickerDate: DatePicker? = null

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

        Adresse!!.setOnClickListener {
            Log.d("Debug:", CheckPermission().toString())
            Log.d("Debug:", isLocationEnabled(requireContext()).toString())
            RequestPermission()
            /* fusedLocationProviderClient.lastLocation.addOnSuccessListener{location: Location? ->
                 textView.text = location?.latitude.toString() + "," + location?.longitude.toString()
             }*/
            getLastLocation()
        }

        return view
    }

    private fun initView(view: View) {
        ButtonReturn = view.findViewById<Button>(R.id.ReurnbuttonSecondePage)
        ButtonNext = view.findViewById<Button>(R.id.NextButtonSecondePage)
        Adresse = view.findViewById(R.id.InscriptionAdresseSecondPage)
        DateNaiss = view.findViewById(R.id.InscriptionDateSecondPage)
        PhoneNumber = view.findViewById(R.id.InscriptionPhoneNumberSecondPage)
        BloodGroup = view.findViewById(R.id.InscriptionBloodSecondPage)
        Male = view.findViewById<RadioButton>(R.id.FirstRadioButtonSecondeInscription)
        Female = view.findViewById<RadioButton>(R.id.SecondeRadioButtonSecondeInscription)
        NoChoice = view.findViewById<RadioButton>(R.id.ThirdRadioButtonSecondeInscription)
        PickerDate = view.findViewById<DatePicker>(R.id.date_Picker)


        ButtonReturn!!.setOnClickListener {
            var firstPage = FragmentInscriptionFirstPage()
            fragmentManager!!.beginTransaction()
                .replace(R.id.ContainerFragmentLayout, firstPage)
                .commit()
        }

        ButtonNext!!.setOnClickListener {
            if (Adresse!!.text.isEmpty() || DateNaiss!!.text.isEmpty() || PhoneNumber!!.text.isEmpty() || BloodGroup!!.text.isEmpty()) {


                var v = View.inflate(requireContext(), R.layout.fragment_dialog, null)
                var builder = AlertDialog.Builder(requireContext())
                builder.setView(v)

                var dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

                dialog.findViewById<Button>(R.id.btn_confirm)!!.setOnClickListener {
                    dialog.dismiss()
                }
            } else if (!Male!!.isChecked && !Female!!.isChecked && !NoChoice!!.isChecked) {
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
                fragmentManager!!.beginTransaction()
                    .replace(R.id.ContainerFragmentLayout, choosePosition).addToBackStack(null)
                    .commit()
            }
        }



        DateNaiss!!.setOnClickListener{
            PickerDate!!.visibility = View.VISIBLE
            val today = Calendar.getInstance()
            PickerDate!!.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH)

            ) { view, year, month, day ->
                val month = month + 1
                val msg: String = "$day/$month/$year"
                DateNaiss!!.setText(msg)
            }

        }






        // phone number case

        PhoneNumber!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (PhoneNumber!!.length() < 8 || PhoneNumber!!.length() > 8) {
                    ButtonNext!!.isEnabled = false
                    PhoneNumber!!.error = "le numero n'existe pas"
                } else {
                    ButtonNext!!.isEnabled = true
                }
            }

        })


    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////


//*****************************************location map*********************************************

    fun getLastLocation() {
        if (CheckPermission()) {
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
                        Adresse!!.setText(getCityName(location.latitude, location.longitude))
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
            Adresse!!.setText(
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
        var Adress = geoCoder.getFromLocation(lat, long, 3)

        cityName = Adress.get(0).locality
        countryName = Adress.get(0).countryName
        Log.d("Debug:", cityName + " ; your Country " + countryName)
        return cityName + "," + countryName
    }


    fun CheckPermission(): Boolean {
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
///////////////////////////////////////////////////////////////////////////////////////////////////

//*****************************************calender***********************************************


}

