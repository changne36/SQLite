package com.example.sqlite

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sqlite.Model.DatabaseHelper
import com.example.sqlite.ui.theme.SQLiteTheme

class MainActivity : ComponentActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var edtname : EditText
    private lateinit var edtphone : EditText
    private lateinit var btnAdd : Button
    private lateinit var btnUpdate : Button
    private lateinit var btnDelete : Button
    private lateinit var btnView : Button
    private lateinit var txtName : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        dbHelper = DatabaseHelper(this)
        edtname = findViewById(R.id.edtname)
        edtphone = findViewById(R.id.edtphone)
        btnAdd = findViewById(R.id.btnthem)
        btnUpdate = findViewById(R.id.btnsua)
        btnDelete = findViewById(R.id.btnxoa)
        btnView = findViewById(R.id.btnhienthi)
        txtName = findViewById(R.id.txtht)

        btnAdd.setOnClickListener {
            val name = edtname.text.toString()
            val phone = edtphone.text.toString()
            if(name.isNotEmpty() && phone.isNotEmpty()) {
               val success = dbHelper.insertContact(name, phone)
                showToast(if(success) "Them thanh cong!" else "Them that bai!")
            } else {
                showToast("Vui long nhao du thong tin!")
            }
        }
        btnUpdate.setOnClickListener() {
            val name = edtname.text.toString()
            val phone = edtphone.text.toString()
            if(name.isNotEmpty() && phone.isNotEmpty()) {
                val success = dbHelper.updateContact(name, phone)
                showToast(if(success) "Cap nhat thanh cong!" else "Cap nhat that bai!")
            } else {
                showToast("Vui long nhap du thong tin!")
            }
        }
        btnDelete.setOnClickListener() {
            val name = edtname.text.toString()
            if(name.isNotEmpty()) {
                val success = dbHelper.deleteContact(name)
                showToast(if(success) "Xoa thanh cong!" else "Xoa that bai!")
            } else {
                showToast("Vui long nhap du thong tin!")
            }
        }
        btnView.setOnClickListener() {
            val contacts = dbHelper.getContact()

            if(contacts.isNotEmpty()) {
                for(contact in contacts) {
                    val resultText = "Ten: ${contact.first}\nSDT: ${contact.second}\n"
                    txtName.text = resultText
                }
            } else {
                txtName.text = "Khong co du lieu!"
            }
        }

    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}