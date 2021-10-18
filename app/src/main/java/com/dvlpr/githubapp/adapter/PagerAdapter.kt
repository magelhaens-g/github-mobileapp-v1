package com.dvlpr.githubapp.adapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dvlpr.githubapp.R
import com.dvlpr.githubapp.view.FollowersFragment
import com.dvlpr.githubapp.view.FollowingFragment


class PagerAdapter(private val context: Context, fragManager: FragmentManager, data: Bundle) : FragmentPagerAdapter(fragManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private var viewModelBundle: Bundle = data

    @StringRes
    private val tab = intArrayOf(R.string.followers, R.string.following)
    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = this.viewModelBundle
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence = context.resources.getString(tab[position])
}
