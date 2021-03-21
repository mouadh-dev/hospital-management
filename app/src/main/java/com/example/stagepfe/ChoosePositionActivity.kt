//package com.example.stagepfe
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.view.View
//import android.widget.*
//
//open class ChoosePositionActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
//    // declaration des views
//    var spinner: Spinner? = null
//    var Matricule: EditText? = null
//    var CIN: EditText? = null
//    var DossierMed: EditText? = null
//    var ButtonReturn: Button? = null
//    var ButtonNext: Button? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_choose_position)
//        initView()
//
//        ButtonNext!!.setOnClickListener {
//            if (CIN!!.text.isEmpty() || DossierMed!!.text.isEmpty()){
//                var toast = Toast.makeText( applicationContext,"le champ est obligatoire", Toast.LENGTH_SHORT)
//                    toast.show()
//
//            }else {
//                val intent = Intent(this, PatientInscriptionActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//
//        }
//
//        ButtonReturn!!.setOnClickListener {
//            val intent = Intent(this, InscriptionSecondPageActivity::class.java)
//            startActivity(intent)
//            finish()
//
//        }
//
//
//
//        ArrayAdapter.createFromResource(
//            this,
//            R.array.User_Position,
//            android.R.layout.simple_spinner_item
//        ).also { adapter ->
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            spinner!!.adapter = adapter
//        }
//        spinner!!.setOnItemSelectedListener(this)
//
//    }
//
//    private fun initView() {
//
//        spinner = findViewById(R.id.PositionSpinner)
//        Matricule = findViewById(R.id.MatriculeEditText)
//        CIN = findViewById(R.id.NumeroCINEditText)
//        DossierMed = findViewById(R.id.NumeroDossierEditText)
//        ButtonReturn = findViewById<Button>(R.id.ReturnButtonChoosePosition)
//        ButtonNext = findViewById<Button>(R.id.NextButtonChoosePosition)
//    }
//
//    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//        if (position==0) {
//            Matricule!!.visibility = View.GONE
//            CIN!!.visibility = View.GONE
//            DossierMed!!.visibility = View.GONE
//        }
//        else if (position==2){
//            CIN!!.visibility= View.VISIBLE
//            DossierMed!!.visibility= View.VISIBLE
//            Matricule!!.visibility= View.GONE
//
//
//        }else  {
//            CIN!!.visibility= View.GONE
//            DossierMed!!.visibility= View.GONE
//            Matricule!!.visibility= View.VISIBLE
//
//        }
//    }
//
//    override fun onNothingSelected(parent: AdapterView<*>?) {
//        TODO("Not yet implemented")
//    }
//
//
//}