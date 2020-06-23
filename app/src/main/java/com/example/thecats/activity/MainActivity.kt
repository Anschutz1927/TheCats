package com.example.thecats.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.thecats.R
import com.example.thecats.fragment.FavoriteFragment
import com.example.thecats.fragment.MainFragment
import com.example.thecats.utils.FragmentType

private const val FRAGMENT_TYPE_KEY = "FRAGMENT_TYPE_KEY"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val type = intent.getStringExtra(FRAGMENT_TYPE_KEY)
        if (type != null) {
            onNextFragment(FragmentType.valueOf(type))
        } else {
            onNextFragment(FragmentType.MAIN)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        if (FragmentType.FAVORITES.name == intent.getStringExtra(FRAGMENT_TYPE_KEY)) {
            val menuItem = menu?.findItem(R.id.menu_favorite)
            menuItem?.let {
                it.isChecked = true
                setMenuIconState(it)
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_favorite) {
            item.isChecked = !item.isChecked
            onNextFragment(if (item.isChecked) FragmentType.FAVORITES else FragmentType.MAIN)
            setMenuIconState(item)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onNextFragment(fragmentType: FragmentType) {
        intent.putExtra(FRAGMENT_TYPE_KEY, fragmentType.name)
        setFragment(
            when (fragmentType) {
                FragmentType.MAIN -> MainFragment.getInstance()
                FragmentType.FAVORITES -> FavoriteFragment.getInstance()
            }
        )
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }

    private fun setMenuIconState(item: MenuItem) {
        val icon = if (item.isChecked) R.drawable.ic_baseline_favorite_24
        else R.drawable.ic_baseline_favorite_border_24
        item.icon = ContextCompat.getDrawable(this, icon)
    }
}