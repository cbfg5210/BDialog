package com.dialog.demo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dialog.demo.dialog.DDialogFragment
import com.dialog.demo.view.ViewFragment

class MainActivity : AppCompatActivity() {
    private var currentFlag = -1

    private val actionBar: ActionBar by lazy { supportActionBar!! }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showFragment(R.id.dialogDemo)
    }

    private fun showFragment(id: Int) {
        if (currentFlag == id) {
            return
        }

        currentFlag = id

        val fragment: Fragment = when (id) {
            R.id.dialogDemo -> {
                actionBar.title = "重写 DialogFragment.onCreateDialog(...) 方法"
                DDialogFragment()
            }
            R.id.layoutDemo -> {
                actionBar.title = "重写 DialogFragment.onCreateView(...) 方法"
                ViewFragment()
            }
            else -> return
        }

        supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, fragment)
                .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        showFragment(item.itemId)
        return true
    }
}
