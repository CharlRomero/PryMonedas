package com.example.prymonedas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Spinner
import android.widget.Toast
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val convertButton: Button = findViewById(R.id.btn_convert)
        convertButton.setOnClickListener { convertCurrency() }

        val opciones = arrayOf("USD", "EUR", "JPY", "GBP", "AUD", "CAD", "CHF", "CNH", "HKD", "NZD")

        val spinner: Spinner = findViewById(R.id.sp_from)
        val spinner2: Spinner = findViewById(R.id.sp_to)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opciones)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter
        spinner2.adapter = adapter
    }

    private fun convertCurrency() {
        val fromAmountEditText: EditText = findViewById(R.id.etn_currency)
        val spinner: Spinner = findViewById(R.id.sp_from)
        val spinner2: Spinner = findViewById(R.id.sp_to)

        val fromAmount = fromAmountEditText.text.toString()

        if (fromAmount.isEmpty()) {
            Toast.makeText(this, "Ingresa una cantidad válida", Toast.LENGTH_SHORT).show()
            return
        }

        val fromCurrency = spinner.selectedItem.toString()
        val toCurrency = spinner2.selectedItem.toString()

        if (fromCurrency == toCurrency) {
            Toast.makeText(this, "Selecciona monedas diferentes", Toast.LENGTH_SHORT).show()
            return
        }

        val conversionRate = getConversionRate(fromCurrency, toCurrency)
        val result = fromAmount.toDouble() * conversionRate

        val toastMessage = String.format("%.2f %s = %.2f %s", fromAmount.toDouble(), fromCurrency, result, toCurrency)
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
        showResult(toastMessage)
    }

    private fun getConversionRate(currencyFrom: String, currencyTo: String): Double {
        val conversionRates = mapOf(
            "USD" to mapOf("EUR" to 0.85, "GBP" to 0.75, "JPY" to 104.54, "AUD" to 1.36, "CAD" to 1.31, "CHF" to 0.91, "CNH" to 6.55, "HKD" to 7.75, "NZD" to 1.43),
            "EUR" to mapOf("USD" to 1.18, "GBP" to 0.89, "JPY" to 123.37, "AUD" to 1.62, "CAD" to 1.56, "CHF" to 1.09, "CNH" to 7.81, "HKD" to 9.27, "NZD" to 1.71),
            "GBP" to mapOf("USD" to 1.33, "EUR" to 1.12, "JPY" to 138.59, "AUD" to 1.82, "CAD" to 1.75, "CHF" to 1.22, "CNH" to 8.74, "HKD" to 10.38, "NZD" to 1.92),
        )

        return conversionRates[currencyFrom]?.get(currencyTo) ?: 1.0
    }

    private fun showResult(result: String) {
        val resultTextView: EditText = findViewById(R.id.et_result)
        resultTextView.setText(result)
    }
}