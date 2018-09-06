package com.example.x.bmicalculator

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.text.style.LineHeightSpan
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*
import java.time.Instant.parse

class MainActivity : AppCompatActivity() {

    lateinit var result: TextView
    lateinit var height: EditText
    lateinit var weight: EditText
    lateinit var calculate: Button
    lateinit var mAdView: AdView
    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713")
        mAdView = adview
        var adRequest: AdRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        mInterstitialAd.loadAd(adRequest)

        height = findViewById(R.id.height)
        weight = findViewById(R.id.weight)
        result = findViewById(R.id.result)
        calculate = findViewById(R.id.calculate)
        calculate.setOnClickListener({
            if (mInterstitialAd.isLoaded) {
                mInterstitialAd.show()
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.")
            }
            calculateBMI()
        })

    }

    private fun calculateBMI() {
        var heightString = height.text.toString()
        var weightString = weight.text.toString()

        if (heightString != null && !"".equals(heightString) && weightString != null && !"".equals(weightString)) {
            val heightValue = java.lang.Float.parseFloat(heightString)
            val weightValue = java.lang.Float.parseFloat(weightString)

            var bmi: Float = weightValue / (heightValue * heightValue)
            displayBMI(bmi)

        }
    }

    private fun displayBMI(bmi: Float) {
        var bmiLabel: String = ""
        if (java.lang.Float.compare(bmi, 15f) <= 0) {
            bmiLabel = "Very Severely UnderWeight"
        } else if (java.lang.Float.compare(bmi, 15f) > 0 && java.lang.Float.compare(bmi, 16f) <= 0) {
            bmiLabel = "Severely UnderWeight"
        } else if (java.lang.Float.compare(bmi, 16f) > 0 && java.lang.Float.compare(bmi, 18.5f) <= 0) {
            bmiLabel = "UnderWeight"
        } else if (java.lang.Float.compare(bmi, 18.5f) > 0 && java.lang.Float.compare(bmi, 25f) <= 0) {
            bmiLabel = "Normal"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(bmi, 30f) <= 0) {
            bmiLabel = "OverWeight"
        } else if (java.lang.Float.compare(bmi, 30f) > 0 && java.lang.Float.compare(bmi, 35f) <= 0) {
            bmiLabel = "Obese Class I"
        } else if (java.lang.Float.compare(bmi, 35f) > 0 && java.lang.Float.compare(bmi, 40f) <= 0) {
            bmiLabel = "Obese Class II"
        }else{
            bmiLabel = "Obese Class III"
        }
        bmiLabel = bmi.toString() + "\n" + bmiLabel
        result.setText(bmiLabel)

    }
}