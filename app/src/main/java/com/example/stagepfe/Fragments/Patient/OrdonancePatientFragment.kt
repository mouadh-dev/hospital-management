package com.example.stagepfe.Fragments.Patient

import android.app.AlertDialog
import android.content.Context.WINDOW_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import com.example.stagepfe.Adapters.Doctor.MyAdapterOrdonanceReading
import com.example.stagepfe.Adapters.Patients.MyAdapterOrdonancePatient
import com.example.stagepfe.Dao.ResponseCallback
import com.example.stagepfe.Dao.UserCallback
import com.example.stagepfe.Dao.UserDao
import com.example.stagepfe.R
import com.example.stagepfe.entite.MedicamentOrdonance
import com.example.stagepfe.entite.Ordonance
import com.example.stagepfe.entite.UserItem
import com.google.gson.Gson


class OrdonancePatientFragment : Fragment() {
    var listOrdonancePatient: ListView? = null
    var list = mutableListOf<Ordonance>()
    var adapterOrdonance: MyAdapterOrdonancePatient? = null
    var userDao = UserDao()
    var fullNamePatient: String? = null
    var listViewOrdReading: ListView? = null
    val listMedicament = mutableListOf<MedicamentOrdonance>()
    var adapterMedicament: MyAdapterOrdonanceReading? = null

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
                fullNamePatient = userItem.nom  + " " + userItem.prenom
                if (userItem.ordonance!= null) {

                    for (entry in userItem.ordonance!!.entries) {
                        var ordonance = entry.value
                        if (ordonance.namepatientOrdo!! == fullNamePatient) {
                            val ordonanceList = Ordonance()
                            fillOrdonanceList(ordonanceList, ordonance)

                            list.add(ordonance)
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

            var gson = Gson()
            var jsonString = gson.toJson(adapterOrdonance)


             //WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE)
            //dialog.findViewById<ImageView>(R.id.QrCodeIv).setBackgroundResource()

           // listViewOrdReading = dialog.findViewById<ListView>(R.id.List_Medicament_to_show)
           // for (medicament in ordonanceList.medicament) {
            //    listMedicament.add(medicament)
           // }

           // adapterMedicament = MyAdapterOrdonanceReading(requireContext(), R.layout.ordonance_reading_doctor, listMedicament)
            //dialog.findViewById<ListView>(R.id.List_Medicament_to_show)!!.adapter = adapterMedicament
           // adapterMedicament!!.notifyDataSetChanged()

            // generating dimension from width and height.
            int dimen = width < height ? width : height;
            dimen = dimen * 3 / 4;

            // setting this dimensions inside our qr code
            // encoder to generate our qr code.
            qrgEncoder = new QRGEncoder(dataEdt.getText().toString(), null, QRGContents.Type.TEXT, dimen);
            try {
                // getting our qrcode in the form of bitmap.
                bitmap = qrgEncoder.encodeAsBitmap();
                // the bitmap is set inside our image
                // view using .setimagebitmap method.
                qrCodeIV.setImageBitmap(bitmap);
            } catch (WriterException e) {
                // this method is called for
                // exception handling.
                Log.e("Tag", e.toString());
            }

///////////////////////////////////////////////////////////////////////////////////////////////////
            dialog.setOnDismissListener {
                listMedicament.clear()
            }

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

    }

    private fun initAdapter() {
        adapterOrdonance =
            MyAdapterOrdonancePatient(requireContext(), R.layout.ordonance_list_patient, list)
        listOrdonancePatient!!.adapter = adapterOrdonance
    }


}