package com.adl.mobileroom.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.*
import com.adl.mobileroom.CreateUser
import com.adl.mobileroom.R
import com.adl.mobileroom.database.UserDatabases
import com.adl.mobileroom.model.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserAdapter(val lstUser :ArrayList<User>): RecyclerView.Adapter<UserViewHolder>(){
    lateinit var parent: ViewGroup
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        this.parent =parent
        return UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_holder,parent,false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(this@UserAdapter,position)
    }

    override fun getItemCount(): Int {
        return lstUser.size
    }

   fun updateUser(pos:Int) {

       GlobalScope.launch {
           val currentUser = lstUser.get(pos)

           val intent = Intent(parent.context, CreateUser::class.java)
           intent.putExtra("data", currentUser)
           parent.context.startActivity(intent)

           val mainExecutor = ContextCompat.getMainExecutor(parent.context)

           // Execute a task in the main thread
           mainExecutor.execute {

               notifyDataSetChanged()
               Log.d("tag main","1")

           }
           UserDatabases.getInstance(parent.context).userDao().updateUsers(lstUser)

       }
       Log.d("tag 2","2")
   }

    fun del(pos:Int){
        GlobalScope.launch {

            val deleteData = UserDatabases.getInstance(parent.context).userDao()
                .deleteUser(lstUser.get(pos))

            lstUser.clear()
            lstUser.addAll( ArrayList(UserDatabases.getInstance(parent.context).userDao().getUser()))
            val mainExecutor = ContextCompat.getMainExecutor(parent.context)

            // Execute a task in the main thread
            mainExecutor.execute {
                // You code logic goes here.
//                Log.d("hapus","${lstUser.get(pos).name}")
                notifyDataSetChanged()

            }

        }
    }



}