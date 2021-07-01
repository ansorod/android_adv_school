package com.ansorod.chat.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ansorod.chat.R
import com.ansorod.chat.presentation.EditUserViewModel
import kotlinx.android.synthetic.main.activity_edit_user.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditUserActivity: AppCompatActivity() {

    private val viewModel: EditUserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user)

        lifecycle.addObserver(viewModel)
        setupObservers()

        initView()
    }

    private fun setupObservers() {
        viewModel.userName.observe(this, { username ->
            usernameInput.setText(username ?: "")
        })
    }

    private fun initView() {
        saveButton.setOnClickListener {
            val input = usernameInput.text
            if(input != null && input.trim().isNotEmpty()) {
                viewModel.saveUser(input.toString())
            }
        }
    }
}