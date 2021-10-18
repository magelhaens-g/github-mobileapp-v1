package com.dvlpr.githubapp.view


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.KeyEvent
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dvlpr.githubapp.R
import com.dvlpr.githubapp.adapter.UserListAdapter
import com.dvlpr.githubapp.databinding.ActivityMainBinding
import com.dvlpr.githubapp.model.UserModel
import com.dvlpr.githubapp.viewModel.UserViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userListAdapter: UserListAdapter
    private lateinit var userViewModel: UserViewModel
    private var doubledBackPressed = false
    private val delay: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userListAdapter = UserListAdapter()
        userListAdapter.notifyDataSetChanged()
        userListAdapter.setOnItemClickCallback(object : UserListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserModel) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                intent.putExtra(DetailActivity.EXTRA_ID, data.id)
                intent.putExtra(DetailActivity.EXTRA_PHOTO, data.avatar_url)
                intent.putExtra(DetailActivity.EXTRA_URL, data.html_url)
                startActivity(intent)
            }
        })

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.adapter = userListAdapter

            ivSearch.setOnClickListener{
                userSearch()
                showBuffer(true)
            }

            etSearch.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    userSearch()
                    showBuffer(true)
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }

            btnFavorite.setOnClickListener {
                val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(intent)
            }

            btnSettings.setOnClickListener {
                showMenu()
            }
        }

        userViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)
        userViewModel.getSearchUser().observe(this, {
            if (it != null) {
                userListAdapter.setterList(it)
                showBuffer(false)
            }
        })
    }

    private fun showMenu() {
        val menu = PopupMenu(this@MainActivity, binding.btnSettings)
        menu.menuInflater.inflate(R.menu.settings, menu.menu)
        menu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.language -> {
                    startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                    true
                }
                R.id.reminder -> {
                    startActivity(Intent(this, AlarmActivity::class.java))
                    true
                }
                R.id.about -> {
                    startActivity(Intent(this, AboutActivity::class.java))
                    true
                }
                else -> false
            }
        }
        menu.show()
    }

    private fun showBuffer(condition: Boolean) {
        if (condition){
            binding.pb.visibility = View.VISIBLE
            binding.rvUser.visibility = View.GONE
        }
        else{
            binding.pb.visibility = View.GONE
            binding.rvUser.visibility = View.VISIBLE
        }
    }

    private fun userSearch() {
        binding.apply {
            val input = etSearch.text.toString()

            if (input.isEmpty())
                showBuffer(false)
            userViewModel.setUserSearch(input)
        }
    }

    override fun onBackPressed() {
        if (doubledBackPressed) {
            super.onBackPressed()
            return
        }
        this.doubledBackPressed = true
        Toast.makeText(this, getString(R.string.double_back), Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            doubledBackPressed = false
        }, delay)
    }
}

