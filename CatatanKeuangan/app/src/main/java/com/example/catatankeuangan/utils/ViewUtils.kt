package com.example.catatankeuangan.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class ViewUtils {
    companion object {
        fun amountFormat(amount:Double, locale: Locale? = null, currencySymbol:String? = null, decimalSeparator:Char? = null, groupingSeparator:Char? = null):String{
            val formatter = if (locale == null) {
                DecimalFormat.getCurrencyInstance() as DecimalFormat
            } else {
                DecimalFormat.getCurrencyInstance(locale) as DecimalFormat
            }
            if (currencySymbol != null || groupingSeparator != null || decimalSeparator != null){
                val symbols = DecimalFormatSymbols()
                currencySymbol?.let { symbols.currencySymbol = currencySymbol }
                groupingSeparator?.let { symbols.groupingSeparator = groupingSeparator }
                decimalSeparator?.let { symbols.decimalSeparator = decimalSeparator }
                formatter.decimalFormatSymbols = symbols
            }
            return formatter.format(amount)
        }
    }

}