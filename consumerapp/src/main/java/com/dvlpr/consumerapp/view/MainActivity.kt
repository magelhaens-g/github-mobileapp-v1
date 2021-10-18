package com.dvlpr.consumerapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dvlpr.consumerapp.adapter.UserListAdapter
import com.dvlpr.consumerapp.model.UserModel
import com.dvlpr.consumerapp.databinding.ActivityMainBinding
import com.dvlpr.consumerapp.viewModel.FavoriteViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userListAdapter: UserListAdapter
    private lateinit var favViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userListAdapter = UserListAdapter()
        userListAdapter.notifyDataSetChanged()

        favViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        favViewModel.setFavUser(this)
        favViewModel.getFavUser()?.observe( this, {
            if (it != null) {
                userListAdapter.setterList(it)
            }
        })

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.adapter = userListAdapter
        }
    }
}