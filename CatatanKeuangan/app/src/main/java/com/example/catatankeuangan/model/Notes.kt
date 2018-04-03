package com.example.catatankeuangan.model

import com.example.catatankeuangan.enumeration.TypeCome

data class Notes (
        var id : Long? = null,
        var amount: Double = 0.0,
        var typeCome:String = TypeCome.OUT.name,
        var date: String = "",
        var description: String = ""
){
    override fun toString():String{
        return "id : $id, " +
                "amount:$amount, " +
                "typeCome:$typeCome, " +
                "date:$date, " +
                "description:$description"
    }
}