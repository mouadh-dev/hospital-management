package com.example.stagepfe

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.stagepfe.Fragments.Doctor.ListRdvDoctorFragment

class ViewPagerAdapter (fm: FragmentManager): FragmentPagerAdapter(fm) {
    private val COUNT = 1

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = ListRdvDoctorFragment()
        }

        return fragment!!
    }

    override fun getCount(): Int {
        return COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return "Tab " + (position + 1)
    }

}