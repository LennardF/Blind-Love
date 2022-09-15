package eu.application.blindlove

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val regist: Button = findViewById(R.id.registrieren)

        regist.setOnClickListener {
            val intent = Intent(this, registrierung::class.java)
            startActivity(intent)
        }

    }
}