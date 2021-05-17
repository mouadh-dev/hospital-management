package com.example.stagepfe.Fragments.Patient

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import com.example.stagepfe.Activity.Authentication.AuthenticationFragmentContainerActivity
import com.example.stagepfe.Activity.Patients.AddRDVToPatientActivity
import com.example.stagepfe.Activity.Patients.CheckRDVPatientActivity
import com.example.stagepfe.Activity.Patients.ProfilePatientActivity
import com.example.stagepfe.R
import com.sothree.slidinguppanel.ScrollableViewHelper

class PatientAccountFragment : Fragment() {
private var rdvImage: ImageView?=null
private var ambulanceImage: ImageView?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_patient_account, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        rdvImage=view.findViewById(R.id.RDVImg)
        ambulanceImage=view.findViewById(R.id.ambulance)
//        var slidingUpPanelLayout = view.findViewById(R.id.sliding_layout)
//        slidingUpPanelLayout.setScrollableViewHelper NestedScrollableViewHelper()
        rdvImage!!.setOnClickListener {
            requireActivity().run {
                var intent =
                    Intent(this, CheckRDVPatientActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        ambulanceImage!!.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + 198)
            startActivity(dialIntent)
        }

    }
//    class NestedScrollableViewHelper : ScrollableViewHelper() {
//        override fun getScrollableViewScrollPosition(
//            scrollableView: View,
//            isSlidingUp: Boolean
//        ): Int {
//            return if (mScrollableView is NestedScrollView) {
//                if (isSlidingUp) {
//                    mScrollableView.getScrollY()
//                } else {
//                    val nsv = mScrollableView as NestedScrollView
//                    val child = nsv.getChildAt(0)
//                    child.bottom - (nsv.height + nsv.scrollY)
//                }
//            } else {
//                0
//            }
//        }
//    }
}



