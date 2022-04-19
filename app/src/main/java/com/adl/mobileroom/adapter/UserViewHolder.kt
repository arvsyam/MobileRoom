package com.adl.mobileroom.adapter

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.adl.mobileroom.R
import kotlinx.android.synthetic.main.activity_create_user.view.*
import kotlinx.android.synthetic.main.user_holder.view.*

class UserViewHolder(view: View): RecyclerView.ViewHolder(view) {
    //    lateinit var parent :View
    var nama = view.txtNama
    var gender = view.txtGender
    var update = view.btn_update
    var umur = view.txtAge
    var status = view.txtStatus
    var loc = view.et_loc
    var context = view.context


    fun bind(adapter: UserAdapter, position: Int) {
        val currentItem = adapter.lstUser.get(position)

        var optGender = ((adapter.parent.context.getResources().getStringArray(R.array.gender_array)));
        var optStatus = ((adapter.parent.context.getResources().getStringArray(R.array.status_array)));
        var s = optStatus.get(currentItem.status.toInt()).toString()

        Log.d("status","${s}")

//        status.setText("${optStatus.get(currentItem.status.toInt())}")
//        gender.setText("${optGender.get(currentItem.gender.toInt()).toString()}")
        nama.setText(currentItem.name)
        gender.setText(currentItem.gender)
        umur.setText(currentItem.age)
        status.setText(currentItem.status)
//        loc.setText(adapter.lstUser.get(position).location)


        update.setOnClickListener({
            adapter.updateUser(position)
//            adapter.del(position)
        })
    }


}