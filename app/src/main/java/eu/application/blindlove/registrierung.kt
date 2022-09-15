package eu.application.blindlove

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*


class registrierung : AppCompatActivity() {

    //Usage for Date selector
    private var dateV: TextView? = null
    private var calendar: Calendar? = null
    private var datePickerDialog: DatePickerDialog? = null
    private var Year = 0
    private var Month = 0
    private var Day = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        //Show Screen
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrierung)

        //Textfield of the Date
        dateV = findViewById<TextView>(R.id.birthDate)

        //Set the Calendar for the Selection,
        calendar = Calendar.getInstance();
        Year = calendar!!.get(Calendar.YEAR) ;
        Month = calendar!!.get(Calendar.MONTH);
        Day = calendar!!.get(Calendar.DAY_OF_MONTH);
        val birth: Button = findViewById(R.id.birthdate)

        //For next Window
        val questions: Button = findViewById(R.id.next)

        //Open Date Selector
        birth.setOnClickListener() {
            setBirthDate()
        }

        //Check if we can get to the next page, if evereything is correct go to the Questions
        questions.setOnClickListener {
            checkInput()
        }
    }

    //Show Window for Birthdate
    private fun setBirthDate() {
        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, Year, Month, Day ->
                var selectedDate = "00.00.0000"

                if (Day in 1..9 && (Month + 1) in 1..9) {
                    selectedDate = ("0$Day.0${Month + 1}.$Year")
                } else if (Day in 1..9 && !((Month + 1) in 1..9)) {
                    selectedDate = ("0$Day.${Month + 1}.$Year")
                } else if (!(Day in 1..9) && (Month + 1) in 1..9) {
                    selectedDate = ("$Day.0${Month + 1}.$Year")
                }else {
                    selectedDate = ("$Day.${Month + 1}.$Year")
                }

                dateV?.text = selectedDate



            },
            Year,
            Month,
            Day
        )

        //After Creating the Datepicker, Display it
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }

    //Checking all Inputs
    private fun checkInput() {

        //All texts for Validation
        val name: TextView = findViewById(R.id.Name)
        val nachname: TextView = findViewById(R.id.nachname)
        val birth: TextView = findViewById(R.id.birthDate)
        val eMail: TextView = findViewById(R.id.eMail)
        val password1: TextView = findViewById(R.id.password1)
        val password2: TextView = findViewById(R.id.password2)

        //Alert Dialog for incorrect Entry Output
        val builder: AlertDialog.Builder = this.let {
            AlertDialog.Builder(it)
        }

        //If name is empty, input incorrect
        if (name.text.toString().isEmpty()) {
            this.let {
                builder.setMessage("Name ist Leer")
                    ?.setTitle("Fehlerhafte Eingabe")

                builder.create()
                builder.show()
            }

        //If second name is empty, input incorret
        } else if(nachname.text.toString().isEmpty()) {
            this.let {
                builder.setMessage("Nachname ist Leer")
                    ?.setTitle("Fehlerhafte Eingabe")

                builder.create()
                builder.show()
            }

        //Birth must be filled, and Person must be legal
        } else if (birth.text.toString() == "00.00.0000" || checkLegal(birth.text.toString())) {
            this.let {
                builder.setMessage("Alter nicht gultig oder nicht Volljärig!")
                    ?.setTitle("Fehlerhafte Eingabe")

                builder.create()
                builder.show()
            }

        //E-Mail must be filled, and has to be a valid E-Mail
        } else if (TextUtils.isEmpty(eMail.toString()) || !android.util.Patterns.EMAIL_ADDRESS.matcher(eMail.text.toString()).matches()) {
            this.let {
                builder.setMessage("E-Mail ist ungültig")
                    ?.setTitle("Fehlerhafte Eingabe")

                builder.create()
                builder.show()
            }

        //Passwords have to be the same
        } else if (password1.text.toString().isEmpty() && password1.text.toString() != password2.text.toString()) {
            this.let {
                builder.setMessage("Passwörter Stimmen nicht überein!")
                    ?.setTitle("Fehlerhafte Eingabe")

                builder.create()
                builder.show()
            }

        //If everything is correct, Display the next window
        } else {
            val intent = Intent(this, registrierung2::class.java)
            startActivity(intent)
        }
    }

    //Check date of birth for legality
    private fun checkLegal(birth: String): Boolean {

        //Set Date Format and Parse it
        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN)
        val theDate = sdf.parse(birth)

        //Get the date in Minutes and the current Date in Minutes, to check the legality
        val dateInMinutes = theDate.getTime() / 60000
        val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
        val currentDateMinutes = currentDate.getTime() / 60000
        val differenceInMinutes = currentDateMinutes - dateInMinutes

        //Return true if not legal and false if legal (18 Years = 94608800 Minutes)
        return differenceInMinutes < 9460800
    }
}

