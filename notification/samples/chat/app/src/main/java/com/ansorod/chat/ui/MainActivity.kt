package com.ansorod.chat.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.ansorod.chat.R
import com.ansorod.chat.notification.NotificationHelper
import com.ansorod.chat.presentation.ChatViewModel
import com.ansorod.chat.ui.adapter.MessageAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: ChatViewModel by viewModel()
    private val adapter = MessageAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycle.addObserver(viewModel)
        initView()
        setupObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_chat, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.editUser) {
//            startActivity(Intent(this, EditUserActivity::class.java))
            NotificationHelper.bubbleNotification(this)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupObservers() {

        viewModel.isUserRegisteredState.observe(this, { isRegistered ->
            sendButton.isEnabled = isRegistered
        })

        viewModel.messageList.observe(this, { messageList ->
            adapter.setData(messageList)
        })
    }

    private fun initView() {

        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true

        messageRecyclerView.layoutManager = layoutManager
        messageRecyclerView.adapter = adapter

        sendButton.setOnClickListener {
            val message = messageInput.text
            if(message != null && message.trim().isNotEmpty()) {
                viewModel.sendMessage(message.toString())
                messageInput.setText("")
            }
        }
    }


}