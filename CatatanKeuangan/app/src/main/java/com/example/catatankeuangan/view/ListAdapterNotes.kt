package com.example.catatankeuangan.view

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.catatankeuangan.R
import com.example.catatankeuangan.enumeration.TypeCome
import com.example.catatankeuangan.model.Notes
import com.example.catatankeuangan.utils.ViewUtils
import org.jetbrains.anko.backgroundColor
import java.util.*

class ListAdapterNotes(context: Context, resource:Int, notes:List<Notes>?):ArrayAdapter<Notes> (context, resource, notes){
    private val colorIncome = Color.parseColor("#008b35")
    private val colorOutcome = Color.parseColor("#e42a2a")

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
            val typeComeCard:CardView? = v?.findViewById(R.id.item_list_typecome_card)
            val description = v?.findViewById<TextView>(R.id.item_list_description)

            val localId = Locale("in","ID")
            if (amount != null) amount.text = ViewUtils.amountFormat(p.amount, localId)
            if (date != null) date.text = p.date
            if (description != null) description.text = p.description
            if(typeCome != null){
                if (p.typeCome == TypeCome.IN.name){
                    if (typeComeCard != null){
                        typeComeCard.setCardBackgroundColor(colorIncome)
                    } else {
                        typeCome.backgroundColor = "e42a2a".toInt(16)
                    }
                    typeCome.setImageResource(R.drawable.ic_call_received_white_24dp)
                } else {
                    if (typeComeCard != null){
                        typeComeCard.setCardBackgroundColor(colorOutcome)
                    } else {
                        typeCome.backgroundColor = "008b35".toInt(16)
                    }
                    typeCome.setImageResource(R.drawable.ic_call_made_white_24dp)
                }
            }
        }

        return v!!
    }
}