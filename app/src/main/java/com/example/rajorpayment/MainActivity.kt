package com.example.rajorpayment

import android.app.Activity
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.razorpay.*
import org.json.JSONObject


class MainActivity : AppCompatActivity(), PaymentResultListener {

    lateinit var viewStatus: TextView
    lateinit var enterMoney: EditText
    lateinit var payMoney: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        enterMoney = findViewById(R.id.entermoney)
        payMoney = findViewById(R.id.paymoney)

        payMoney.setOnClickListener {
            savePayments(enterMoney.text.toString().trim().toInt())
        }

        Checkout.preload(this@MainActivity)

    }

    private fun savePayments(amount: Int) {
        val checkout = Checkout()
        checkout.setKeyID("rzp_test_0SlY6dlI8RLyI9")

        try {
            val options = JSONObject()
            options.put("name", "RazorPay Demo")
            options.put("Desc", "this is razorpay trial")
            options.put("theme.color", "#3399cc")
            options.put("currency", "INR")
            options.put("amout", "amout*100")


            val retryObj = JSONObject()


            retryObj.put("enabled", "true")
            retryObj.put("max_count", "4")
            options.put("retry", retryObj)

            checkout.open(this@MainActivity, options)
        } catch (e: Exception) {
            Toast.makeText(this@MainActivity, "Error in payment", Toast.LENGTH_SHORT).show()
            e.printStackTrace()

        }
    }

    override fun onPaymentSuccess(p0: String?) {
        viewStatus.text = p0
        viewStatus.setTextColor(Color.GREEN)
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        viewStatus.text = p1
        viewStatus.setTextColor(Color.RED)
    }

}
