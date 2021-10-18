package com.dvlpr.githubapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dvlpr.githubapp.R
import com.dvlpr.githubapp.adapter.UserListAdapter
import com.dvlpr.githubapp.data.FavoriteUser
import com.dvlpr.githubapp.databinding.ActivityFavoriteBinding
import com.dvlpr.githubapp.model.UserModel
import com.dvlpr.githubapp.viewModel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var userListAdapter: UserListAdapter
    private lateinit var favViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userListAdapter = UserListAdapter()
        userListAdapter.notifyDataSetChanged()
        userListAdapter.setOnItemClickCallback(object : UserListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserModel) {
                val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                intent.putExtra(DetailActivity.EXTRA_ID, data.id)
                intent.putExtra(DetailActivity.EXTRA_PHOTO, data.avatar_url)
                intent.putExtra(DetailActivity.EXTRA_URL, data.html_url)
                startActivity(intent)
            }
        })

        favViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        favViewModel.getFavUser()?.observe(this, {
            if (it != null) {
                val list = mapList(it)
                userListAdapter.setterList(list)
            } else if (it == null) {
                binding.nothingContainer.visibility = View.VISIBLE
            }
        })

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUser.adapter = userListAdapter

            btnBack.setOnClickListener {
                this@FavoriteActivity.finish()
            }

            btnSettings.setOnClickListener {
                showMenu()
            }
        }
    }

    private val getFavUserListObserver: Observer<ArrayList<UserModel>> = Observer { user ->
        user?.let { userListAdapter.setterList(it) }
        showNothingAdded(user.isNullOrEmpty())
    }

    private fun showNothingAdded(condition: Boolean) {
        if (condition) {
            binding.nothingContainer.visibility = View.VISIBLE
        } else {
            binding.nothingContainer.visibility = View.GONE
        }
    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<UserModel> {
        val listUser = ArrayList<UserModel>()
        for (user in users) {
            val userMapped = UserModel(
                user.login,
                user.id,
                user.avatar_url,
                user.html_url
            )
            listUser.add(userMapped)
        }
        return listUser
    }

    private fun showMenu() {
        val menu = PopupMenu(this@FavoriteActivity, binding.btnSettings)
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
}
