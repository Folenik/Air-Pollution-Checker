package com.example.airapi.ui.user

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.airapi.DAO.Repository
import com.example.airapi.R

class LoginUserFragment : Fragment() {
    private lateinit var loginUserViewModel: LoginUserViewModel
    private lateinit var loginUserViewModelFactory: LoginUserViewModelFactory
    private lateinit var database: Repository


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val root = inflater.inflate(R.layout.fragment_user_login, container, false)

        database = Repository(requireParentFragment())
        loginUserViewModelFactory = LoginUserViewModelFactory(database)
        loginUserViewModel =
            ViewModelProvider(this, loginUserViewModelFactory).get(LoginUserViewModel::class.java)

        val button: Button = root.findViewById(R.id.button_login)
        val editEmail: EditText = root.findViewById(R.id.edit_email)
        val editPassword: EditText = root.findViewById(R.id.edit_firstPassword)
        val editPassword2: EditText = root.findViewById(R.id.edit_secondPassword)
        val buttonlogout: Button = root.findViewById(R.id.button_logout)
        val loggedInTextt: TextView = root.findViewById(R.id.already_logged_textview)

        if (loginUserViewModel.checkIfUserLoggedIn() == true) {

            button.visibility = View.INVISIBLE
            editEmail.visibility = View.INVISIBLE
            editPassword.visibility = View.INVISIBLE
            editPassword2.visibility = View.INVISIBLE
            buttonlogout.visibility = View.VISIBLE
            loggedInTextt.visibility = View.VISIBLE
        } else {
            button.visibility = View.VISIBLE
            editEmail.visibility = View.VISIBLE
            editPassword.visibility = View.VISIBLE
            editPassword2.visibility = View.VISIBLE
            buttonlogout.visibility = View.INVISIBLE
            loggedInTextt.visibility = View.INVISIBLE

        }

        buttonlogout.setOnClickListener() {
            loginUserViewModel.setCurrentUserLoggedOut()
            button.visibility = View.VISIBLE
            editEmail.visibility = View.VISIBLE
            editPassword.visibility = View.VISIBLE
            editPassword2.visibility = View.VISIBLE
            buttonlogout.visibility = View.INVISIBLE
            loggedInTextt.visibility = View.INVISIBLE
            Toast.makeText(context, getString(R.string.logged_out), LENGTH_LONG).show()
        }


        button.setOnClickListener {

            try {
                if (loginUserViewModel.isValidAccount(
                        editEmail.text.toString(),
                        editPassword.text.toString()
                    )
                ) {
                    Toast.makeText(context, getString(R.string.logged_in), LENGTH_LONG).show()
                    loginUserViewModel.setCurrentUserLoggedOut()
                    loginUserViewModel.setCurrentUserLoggedIn(editEmail.text.toString())

                    button.visibility = View.INVISIBLE
                    editEmail.visibility = View.INVISIBLE
                    editPassword.visibility = View.INVISIBLE
                    editPassword2.visibility = View.INVISIBLE
                    buttonlogout.visibility = View.VISIBLE
                    loggedInTextt.visibility = View.VISIBLE

                } else {
                    Toast.makeText(context, getString(R.string.wrong_login_details), LENGTH_LONG).show()
                }

            } catch (e: NullPointerException) {
                Toast.makeText(context, getString(R.string.fill_areas), LENGTH_LONG).show()
            }

            val inputMethodManager =
                activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view!!.windowToken, 0)

        }
        return root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}
