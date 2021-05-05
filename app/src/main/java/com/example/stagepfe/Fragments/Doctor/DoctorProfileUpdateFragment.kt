//package com.example.stagepfe.Fragments.Doctor
//
//import android.Manifest
//import android.app.AlertDialog
//import android.app.DatePickerDialog
//import android.content.Context
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.location.Geocoder
//import android.location.Location
//import android.location.LocationManager
//import android.os.Bundle
//import android.os.Looper
//import android.text.Editable
//import android.text.TextWatcher
//import android.text.method.HideReturnsTransformationMethod
//import android.text.method.PasswordTransformationMethod
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.*
//import androidx.core.app.ActivityCompat
//import androidx.fragment.app.Fragment
//import com.example.stagepfe.Activity.Authentication.ContainerFragmentPasswordActivity
//import com.example.stagepfe.Activity.Doctors.AccountDoctorActivity
//import com.example.stagepfe.Activity.Doctors.CheckRDVActivity
//import com.example.stagepfe.Activity.Doctors.DoctorProfilActivity
//import com.example.stagepfe.Dao.UserCallback
//import com.example.stagepfe.Dao.UserDao
//import com.example.stagepfe.Fragments.Authentication.InscriptionFirstPageFragment
//import com.example.stagepfe.R
//import com.example.stagepfe.entite.UserItem
//import com.google.android.gms.location.*
//import java.text.SimpleDateFormat
//import java.util.*
//
//class DoctorProfileUpdateFragment : Fragment() {
//
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        var view = inflater.inflate(R.layout.fragment_doctor_profile_update, container, false)
//        initView(view)
//        return view
//    }
//
//    private fun initView(view: View) {
//
//
////*****************************************password***********************************************
//
//}