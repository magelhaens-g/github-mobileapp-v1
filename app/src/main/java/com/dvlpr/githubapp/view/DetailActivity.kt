package com.dvlpr.githubapp.view


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dvlpr.githubapp.R
import com.dvlpr.githubapp.adapter.PagerAdapter
import com.dvlpr.githubapp.databinding.ActivityDetailBinding
import com.dvlpr.githubapp.viewModel.UserDetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DecimalFormat
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var dataDetailViewModel: UserDetailViewModel

    companion object {
        const val EXTRA_USERNAME = "username"
        const val EXTRA_ID = "id"
        const val EXTRA_PHOTO = "photo"
        const val EXTRA_URL = "url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val photo = intent.getStringExtra(EXTRA_PHOTO)
        val url = intent.getStringExtra(EXTRA_URL)
        val saveData = Bundle()
        saveData.putString(EXTRA_USERNAME, username)

        dataDetailViewModel = ViewModelProvider(this).get(UserDetailViewModel::class.java)
        dataDetailViewModel.setUserDetail(username.toString())
        dataDetailViewModel.getUserDetail().observe(this, {
            if (it != null) {
                binding.apply {
                    tvUsername.text = it.name
                    tvRepositoryValue.text = valueConverter(it.public_repos)
                    tvFollowersValue.text = valueConverter(it.followers)
                    tvFollowingValue.text = valueConverter(it.following)
                    tvCompany.text = it.company ?: "-"
                    tvLocation.text = it.location ?: "-"
                    Glide.with(this@DetailActivity)
                            .load(it.avatar_url)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .placeholder(R.drawable.ic_baseline_person_24)
                            .error(R.drawable.ic_baseline_error_outline_24)
                            .into(civPhoto)
                }
            }
        })

        val sectionPagerAdapter = PagerAdapter(this,supportFragmentManager, saveData)
        var _isChecked = false
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tab.setupWithViewPager(binding.viewPager)

            toggleFav.setOnClickListener {
                _isChecked = !_isChecked
                if (_isChecked) {
                    dataDetailViewModel.addToFav(username, id, photo, url)
                    Toast.makeText(this@DetailActivity, R.string.add_fav, Toast.LENGTH_SHORT).show()
                } else {
                    dataDetailViewModel.removeFromFav(id)
                    Toast.makeText(this@DetailActivity, R.string.remove_fav, Toast.LENGTH_SHORT).show()
                }
                toggleFav.isChecked = _isChecked
            }

            btnBack.setOnClickListener {
                this@DetailActivity.finish()
            }

            btnWeb.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            val count = dataDetailViewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (count != null) {
                    if (count > 0) {
                        binding.toggleFav.isChecked = true
                        _isChecked = true
                    } else {
                        binding.toggleFav.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }
    }

    private fun valueConverter(number: Int?): String {
        var fValue: String = number.toString()
        if (number != null) {
            val suffix = charArrayOf(' ', 'k', 'M', 'B')
            val value = floor(log10(number.toDouble())).toInt()
            val base = value / 3
            fValue = if (value >= 3 && base < suffix.size) {
                DecimalFormat("#0.0").format(
                    number / 10.0.pow(base * 3.toDouble())
                ) + suffix[base]
            } else {
                DecimalFormat("#,##0").format(number)
            }
        }
        return fValue
    }
}