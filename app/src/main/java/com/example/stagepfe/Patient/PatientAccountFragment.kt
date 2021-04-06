package com.example.stagepfe.Patient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import com.example.stagepfe.R
import com.sothree.slidinguppanel.ScrollableViewHelper


class PatientAccountFragment : Fragment() {

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
//        var slidingUpPanelLayout = view.findViewById(R.id.sliding_layout)
//        slidingUpPanelLayout.setScrollableViewHelper NestedScrollableViewHelper()

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



