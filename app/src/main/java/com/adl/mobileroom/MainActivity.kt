package com.adl.mobileroom

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ActionMode
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.adl.mobileroom.adapter.UserAdapter
import com.adl.mobileroom.database.UserDatabases
import com.adl.mobileroom.model.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var userAdapter: UserAdapter
    var lstUser = ArrayList<User>()
    lateinit var user:User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponent()

        GlobalScope.launch {
            lstUser.addAll(ArrayList(getAllData()))
            countUser.setText("${lstUser.size} Users")
        }

        userAdapter = UserAdapter(lstUser)
        rcUser.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
        }


    }

    fun initComponent(){
        btn_createUser.setOnClickListener({
            addNewUser()
        })
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if(result.resultCode == Activity.RESULT_OK){
            GlobalScope.launch{

                lstUser.clear()
                UserDatabases.getInstance(this@MainActivity).userDao().updateUsers(lstUser)
                lstUser.addAll(ArrayList(getAllData()))

                this@MainActivity.runOnUiThread({
                    userAdapter.notifyDataSetChanged()
                })


            }
        }
    }

    fun addNewUser(){
        val intent = Intent(this@MainActivity,CreateUser::class.java)
        resultLauncher.launch(intent)
    }



    fun getAllData(): List<User> {
        return UserDatabases.getInstance(this@MainActivity).userDao().getUser()

    }

    override fun onRestart() {
        super.onRestart()
        GlobalScope.launch {
            lstUser.clear()
            lstUser.addAll(ArrayList(getAllData()))
            countUser.setText("${lstUser.size} Users")
        }

        userAdapter = UserAdapter(lstUser)
        rcUser.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
        }
    }



}