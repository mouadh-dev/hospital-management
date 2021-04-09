package com.example.stagepfe.Activity.Patients

import android.os.Bundle
import android.view.View
import android.view.View.*
import android.widget.TableLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.stagepfe.Fragments.Patient.OrdonancePatientFragment
import com.example.stagepfe.Fragments.Patient.RapportPatientFragment
import com.example.stagepfe.Fragments.Patient.RendezVousPatientFragment
import com.example.stagepfe.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class ProfilePatientActivity : AppCompatActivity() {
    var viewPager: ViewPager? = null
    var tabs: TabLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_patient)
///////////////////////////////////////////////////////////////////////////////////////////////////



///////////////////////////////////////////////////////////////////////////////////////////////////
        initView()
//        var contentProfilPatient = RendezVousPatientFragment()
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.Container_Information_Patient, contentProfilPatient)
//            .commit()
    }

    private fun initView() {

        viewPager = findViewById(R.id.View_Pager)
        tabs = findViewById(R.id.tabs_ViewPager)

////////////////////////////////////////View Pager//////////////////////////////////////////////////

        var adapter = MyViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(RendezVousPatientFragment(), " Mes rendez-vous ")
        adapter.addFragment(RapportPatientFragment(), " Mes rapports ")
        adapter.addFragment(OrdonancePatientFragment(), " Mes ordonances ")

        viewPager!!.adapter = adapter
        tabs!!.setupWithViewPager(viewPager)

////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////

    }
    //////////////////////////////////////////////////////////////////////////////////////////////////

    class MyViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

        private val fragmentList: MutableList<Fragment> = ArrayList()
        private val titleList: MutableList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            titleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
}