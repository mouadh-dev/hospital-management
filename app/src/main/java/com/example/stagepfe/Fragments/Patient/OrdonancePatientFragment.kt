package com.example.stagepfe.Fragments.Patient

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.fragment.app.Fragment
import com.example.stagepfe.Adapters.Patients.MyAdapterOrdonancePatient
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.Ordonance
import com.example.stagepfe.entite.UserItem
import com.google.gson.Gson
import com.google.zxing.WriterException


class OrdonancePatientFragment : Fragment() {
    var listOrdonancePatient: ListView? = null
    var list = mutableListOf<Ordonance>()
    var adapterOrdonance: MyAdapterOrdonancePatient? = null
    var userDao = UserDao()
    var fullNamePatient: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_ordonance_patient, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        listOrdonancePatient = view.findViewById(R.id.list_Ordonance_Patient)
        initAdapter()

        userDao.retrieveCurrentDataUser(object : UserCallback {
            override fun onSuccess(userItem: UserItem) {
                fullNamePatient = userItem.nom + " " + userItem.prenom
                if (userItem.ordonance != null) {

                    for (entry in userItem.ordonance!!.entries) {
                        var ordonance = entry.value
                        if (ordonance.idPatient!! == userItem.id) {
                            val ordonanceList = Ordonance()
                            fillOrdonanceList(ordonanceList, ordonance)

                            list.add(ordonanceList)
                            adapterOrdonance!!.notifyDataSetChanged()

                        }
                    }
                }
            }

            override fun failure() {
            }
        })

        listOrdonancePatient!!.setOnItemClickListener { parent, view, position, id ->
            val ordonanceList = Ordonance()
                val ordonanceAdapter: Ordonance? = adapterOrdonance!!.getItem(position)
            fillOrdonanceList(ordonanceList, ordonanceAdapter!!)

            val v = View.inflate(requireContext(), R.layout.dialog_ordonance, null)
            val builder = AlertDialog.Builder(requireContext())
            builder.setView(v)
            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.findViewById<TextView>(R.id.nameDoctor)
                .setText("DR" + " " + ordonanceList.nameDoctorOrd)
            dialog.findViewById<TextView>(R.id.namePatient).setText(ordonanceList.namepatientOrdo)
            dialog.findViewById<Button>(R.id.btn_remove).setText("D'accord")
            dialog.findViewById<ImageView>(R.id.QrCodeIv)


            var gson = Gson()
            var jsonString = gson.toJson(ordonanceList)
            // below line is for getting
            // the windowmanager service.
            val manager =
                requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager?
            // initializing a variable for default display.
            val display = manager!!.defaultDisplay

            // creating a variable for point which
            // is to be displayed in QR Code.
            val point = Point()
            display.getSize(point)

            // getting width and
            // height of a point
            val width = point.x
            val height = point.y

            // generating dimension from width and height.
            var dimen = if (width < height) width else height
            dimen = dimen * 3 / 4

                    // setting this dimensions inside our qr code
                    // encoder to generate our qr code.
                    var qrgEncoder = QRGEncoder(jsonString, null, QRGContents.Type.TEXT, dimen)
                    try {
                        // getting our qrcode in the form of bitmap.
                        var bitmap = qrgEncoder.encodeAsBitmap()
                        // the bitmap is set inside our image
                        // view using .setimagebitmap method.
                        dialog.findViewById<ImageView>(R.id.QrCodeIv).setImageBitmap(bitmap)
                    } catch (e: WriterException) {
                        // this method is called for
                        // exception handling.
                        Log.e("Tag", e.toString())
                    }



///////////////////////////////////////////////////////////////////////////////////////////////////

            dialog.findViewById<Button>(R.id.btn_remove).setOnClickListener {
                dialog.dismiss()
            }

        }



    }

    private fun fillOrdonanceList(ordonanceList: Ordonance, ordonance: Ordonance) {
        ordonanceList.dateOrdonanceSend = ordonance.dateOrdonanceSend
        ordonanceList.hourOrdonanceSend = ordonance.hourOrdonanceSend!!.substring(0, 5)
        ordonanceList.nameDoctorOrd = ordonance.nameDoctorOrd
        ordonanceList.idPatient = ordonance.idPatient
        ordonanceList.namepatientOrdo = ordonance.namepatientOrdo
        ordonanceList.medicament = ordonance.medicament
        ordonanceList.idDoctor = ordonance.idDoctor
        ordonanceList.id = ordonance.id
        ordonanceList.color = ordonance.color
        ordonanceList.analyse = ordonance.analyse
        ordonanceList.typeOrdonnance=ordonance.typeOrdonnance
        ordonanceList.taken=ordonance.taken
    }

    private fun initAdapter() {
        adapterOrdonance = MyAdapterOrdonancePatient(requireContext(), R.layout.ordonance_list_patient, list)
        listOrdonancePatient!!.adapter = adapterOrdonance
    }


}

