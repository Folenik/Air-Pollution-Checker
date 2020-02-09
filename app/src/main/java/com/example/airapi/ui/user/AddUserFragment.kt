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
import com.example.airapi.models.User
import kotlinx.android.synthetic.main.fragment_user_add.*


class AddUserFragment : Fragment() {
    private lateinit var addUserViewModel: AddUserViewModel
    private lateinit var addUserViewModelFactory: AddUserViewModelFactory
    private lateinit var database: Repository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_user_add, container, false)

        database = Repository(requireParentFragment())
        addUserViewModelFactory = AddUserViewModelFactory(database)
        addUserViewModel =
            ViewModelProvider(this, addUserViewModelFactory).get(AddUserViewModel::class.java)

        val button: Button = root.findViewById(R.id.button_add)
        val editName: EditText = root.findViewById(R.id.edit_name)
        val editSurname: EditText = root.findViewById(R.id.edit_surname)
        val editEmail: EditText = root.findViewById(R.id.edit_email)
        val editPassword: EditText = root.findViewById(R.id.edit_firstPassword)
        val editPassword2: EditText = root.findViewById(R.id.edit_secondPassword)
        val userLogged: TextView = root.findViewById(R.id.already_logged_textview)

        if (addUserViewModel.checkIfUserLoggedIn() == true) {
            button.visibility = View.INVISIBLE
            editName.visibility = View.INVISIBLE
            editSurname.visibility = View.INVISIBLE
            editEmail.visibility = View.INVISIBLE
            editPassword.visibility = View.INVISIBLE
            editPassword2.visibility = View.INVISIBLE
            userLogged.visibility = View.VISIBLE
        } else {
            button.visibility = View.VISIBLE
            editName.visibility = View.VISIBLE
            editSurname.visibility = View.VISIBLE
            editEmail.visibility = View.VISIBLE
            editPassword.visibility = View.VISIBLE
            editPassword2.visibility = View.VISIBLE
            userLogged.visibility = View.INVISIBLE
        }

        button.setOnClickListener {
            if (addUserViewModel.checkIfUserExists(editEmail.text.toString()) == false) {
                if (editEmail.text.toString().contains("@")) {
                    if (editPassword.text.toString() == editPassword2.text.toString()) {
                        val user = User(
                            name = editName.text.toString(),
                            surname = editSurname.text.toString(),
                            email = editEmail.text.toString(),
                            password = editPassword.text.toString(),
                            userLogged = 0
                        )
                        addUserViewModel.insertUser(user)
                        editName.setText("")
                        editSurname.setText("")
                        editEmail.setText("")
                        editPassword.setText("")
                        edit_secondPassword.setText("")

                        addUserViewModel.setCurrentUserLoggedOut()

                        Toast.makeText(context, getString(R.string.registered), LENGTH_LONG).show()

                    } else {
                        Toast.makeText(context, getString(R.string.password_wrong), LENGTH_LONG).show()
                        editPassword.setText("")
                        editPassword2.setText("")
                    }
                } else {
                    Toast.makeText(context, getString(R.string.email_doesnt_exist), LENGTH_LONG).show()
                    editEmail.setText("")
                }
            } else {
                Toast.makeText(context, getString(R.string.user_exists), LENGTH_LONG).show()
                editName.setText("")
                editSurname.setText("")
                editEmail.setText("")
                editPassword.setText("")
                edit_secondPassword.setText("")
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
