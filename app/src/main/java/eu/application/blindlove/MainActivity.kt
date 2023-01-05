package eu.application.blindlove

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import java.sql.*
import java.util.*

class MainActivity : AppCompatActivity() {

    data class User(val id: Int, val name: String)
    private val jdbcUrl = "jdbc:mysql://127.0.0.1:3306/BlindLove?"

    override fun onCreate(savedInstanceState: Bundle?) {

        val builder: AlertDialog.Builder = this.let {
            AlertDialog.Builder(it)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val regist: Button = findViewById(R.id.registrieren)

        //Create Connection to the Database Server, so the User can log in
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance()
            val connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/BlindLove?", "root", "NEWPASS")

            Toast.makeText(
                this,
                "${connection.isValid(0)}", Toast.LENGTH_SHORT

            ).show()
        }catch (ex: SQLException) {
            this.let {
                builder.setMessage("Could not Create Connection to Database")
                    ?.setTitle("Database Error")

                builder.create()
                builder.show()
            }
        }

        // if the User have no Account, register
        regist.setOnClickListener {
            val intent = Intent(this, registrierung::class.java)
            startActivity(intent)
        }

    }
}

