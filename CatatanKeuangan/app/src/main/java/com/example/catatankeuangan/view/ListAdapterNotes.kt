package com.example.catatankeuangan.view

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.catatankeuangan.R
import com.example.catatankeuangan.enumeration.TypeCome
import com.example.catatankeuangan.model.Notes

class ListAdapterNotes(context: Context, resource:Int, notes:List<Notes>?):ArrayAdapter<Notes> (context, resource, notes){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var v: View? = convertView
        if (v == null) {
            val vi = LayoutInflater.from(context)
            v = vi.inflate(R.layout.item_list_notes, v)
        }

        val p = getItem(position)
        if (p != null){
            val amount:TextView? = v?.findViewById(R.id.item_list_amount)
            val date:TextView? = v?.findViewById(R.id.item_list_date)
            val typeCome:ImageView? = v?.findViewById(R.id.item_list_typecome)

            if (amount != null) amount.text = p.amount.toString()
            if (date != null) date.text = p.date
            if(typeCome != null){
                if (p.typeCome == TypeCome.IN.name){
                    typeCome.setBackgroundColor(Color.GREEN)
                    typeCome.setImageResource(R.drawable.ic_call_received_white_24dp)
                } else {
                    typeCome.setBackgroundColor(Color.RED)
                    typeCome.setImageResource(R.drawable.ic_call_made_white_24dp)
                }
            }
        }

        return v!!
    }
}