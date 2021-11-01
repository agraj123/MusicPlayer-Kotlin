package com.example.musicplayer.activity

import android.content.ActivityNotFoundException
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.text.Editable
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import com.example.musicplayer.fragment.FavouriteFragment
import com.example.musicplayer.fragment.HomeFragment
import com.example.musicplayer.fragment.RecentFragment
import com.example.musicplayer.fragment.SettingsFragment
import com.example.musicplayer.R
import com.example.musicplayer.adapter.SongAdapter
import com.example.musicplayer.databinding.ActivityMainBinding
import com.example.musicplayer.model.PlaylistModel
import java.util.*
import java.util.Locale.filter
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var arrayList: ArrayList<PlaylistModel>
    private lateinit var adapter: SongAdapter

    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mic.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Something...")

            try {
                activityResultLauncher.launch(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(applicationContext, "Device not supported", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it!!.resultCode == RESULT_OK && it!!.data != null) {
                    val speechToText =
                        it!!.data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<Editable>
                    binding.editText.text = speechToText[0]
                }
            }

        val transaction12 =
            supportFragmentManager.beginTransaction()
        transaction12.replace(R.id.con, HomeFragment())
        transaction12.commit()

        binding.bottomNavView.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {

                R.id.home -> {
                    val transaction12 =
                        supportFragmentManager.beginTransaction()
                    transaction12.replace(R.id.con, HomeFragment())
                    transaction12.commit()
                }
                R.id.recent -> {
                    val transaction1 =
                        supportFragmentManager.beginTransaction()
                    transaction1.replace(R.id.con, RecentFragment())
                    transaction1.commit()
                }
                R.id.favourite -> {
                    val transaction2 =
                        supportFragmentManager.beginTransaction()
                    transaction2.replace(R.id.con, FavouriteFragment())
                    transaction2.commit()
                }
                R.id.settings -> {
                    val transaction3 =
                        supportFragmentManager.beginTransaction()
                    transaction3.replace(R.id.con, SettingsFragment())
                    transaction3.commit()
                }
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu!!.findItem(R.id.item_search)
        val searchView: SearchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    filter(text = newText)
                }
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    fun filter(text: String) {
        val filteredlist: ArrayList<PlaylistModel> = ArrayList()
        for (item in arrayList) {
            if (item.getSong().lowercase(Locale.getDefault())
                    .contains(text.lowercase(Locale.getDefault()))
            ) {
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            adapter.filterList(filteredlist)
        }
    }
}