package com.adl.mobileroom


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.adl.mobileroom.adapter.UserAdapter
import com.adl.mobileroom.database.UserDatabases
import com.adl.mobileroom.model.User
import com.mcdev.splitbuttonlibrary.OnButtonClickListener
import kotlinx.android.synthetic.main.activity_create_user.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CreateUser : AppCompatActivity() {

    lateinit var useradapter: UserAdapter
    var gen:String? ="-"
    var stats:String?="-"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
        var user =intent?.getParcelableExtra<User>("data")

        et_nama.setText(user?.name)
        Log.d("nama mau update","${user?.id}")
        gen = user?.gender
        et_umur.setText(user?.age)
        stats = user?.status

        if(user != null){
            menuGen.setSelection(user?.gender.toInt())
            menuStats.setSelection(user?.status.toInt())
        }
        initComponent()



        btn_add.setOnClickListener({
            if(user != null){

                val data = User(user?.id,et_nama.text.toString(), menuGen.selectedItemPosition.toString(), et_umur.text.toString(),menuStats.selectedItemPosition.toString())
                GlobalScope.launch {
                    updateDataUser(data)
                }
                Toast.makeText(this,"data Updated",Toast.LENGTH_SHORT).show()
            }else{

                val data = User(user?.id,et_nama.text.toString(),menuGen.selectedItemPosition.toString(), et_umur.text.toString(),menuStats.selectedItemPosition.toString())
                GlobalScope.launch {
                    saveDataUser(data)
                }
                Toast.makeText(this,"New User Added",Toast.LENGTH_SHORT).show()
            }



        })


    }

    fun initComponent(){

        splitBtn.setTextColor(R.color.black)
        splitBtn.setIconColor(android.R.color.white)
        splitBtn.setBgColor(android.R.color.holo_orange_light)
        splitBtn.setMenuItems(R.menu.split_menu)
        splitBtn.itemColor = android.R.color.holo_blue_dark





        splitBtn.setOnButtonClickListener(object : OnButtonClickListener {
            override fun onClick(itemId: Int, itemTitle: String?) {
                Log.d("TAG", "onClick: id :$itemId")
                Log.d("TAG", "onClick: title :$itemTitle")
                if (itemId == R.id.femaleInput) {
                    gen = "Female"

                    Toast.makeText(this@CreateUser, "female", Toast.LENGTH_SHORT).show()
                    Log.d("TAG", "onClick: send  ")
                }else if (itemId == R.id.maleInput) {
                    gen = "Male"
                    Toast.makeText(this@CreateUser, "male", Toast.LENGTH_SHORT).show()
                    Log.d("TAG", "onClick: bookmark ")
                } else if (itemId == R.id.noAct) {
                    Toast.makeText(this@CreateUser, "Draft", Toast.LENGTH_SHORT).show()
                }
            }
        })


        splitStatus.setTextColor(R.color.black)
        splitStatus.setIconColor(android.R.color.white)
        splitStatus.setBgColor(android.R.color.holo_orange_light)
        splitStatus.setMenuItems(R.menu.split_status)
        splitStatus.itemColor = android.R.color.holo_blue_dark



        splitStatus.setOnButtonClickListener(object : OnButtonClickListener {
            override fun onClick(itemId: Int, itemTitle: String?) {
                Log.d("TAG", "onClick: id :$itemId")
                Log.d("TAG", "onClick: title :$itemTitle")
                if (itemId == R.id.singleInput) {
                    stats = "Single"
                    Toast.makeText(this@CreateUser, "female", Toast.LENGTH_SHORT).show()
                    Log.d("TAG", "onClick: send  ")
                }else if (itemId == R.id.marriedInput) {
                    stats = "Married"
                    Toast.makeText(this@CreateUser, "male", Toast.LENGTH_SHORT).show()
                    Log.d("TAG", "onClick: bookmark ")
                } else if (itemId == R.id.noActStatus) {
                    Toast.makeText(this@CreateUser, "Draft", Toast.LENGTH_SHORT).show()
                }
            }
        })


    }

    fun updateDataUser(editUser:User){
        UserDatabases.getInstance(this@CreateUser).userDao().updateUser(editUser)
        finish()
    }

    fun saveDataUser(newUser:User){
        UserDatabases.getInstance(this@CreateUser).userDao().addUser(newUser)
        val intent = Intent()
        intent.putExtra("data",newUser)
        setResult(Activity.RESULT_OK,intent)
        finish()
    }


    fun getAllData(): List<User> {

        return UserDatabases.getInstance(this@CreateUser).userDao().getUser()

    }


}
