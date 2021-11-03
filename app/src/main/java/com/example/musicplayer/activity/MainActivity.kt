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
import androidx.core.widget.addTextChangedListener
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
        arrayList = ArrayList()


//        editText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//            @Override
//            public void afterTextChanged(Editable s) {
//                filter(s.toString());
//            }
//        });
//    }
//    private void filter(String text) {
//        ArrayList<ExampleItem> filteredList = new ArrayList<>();
//        for (ExampleItem item : mExampleList) {
//            if (item.getText1().toLowerCase().contains(text.toLowerCase())) {
//                filteredList.add(item);
//            }
//        }
//        mAdapter.filterList(filteredList);
//    }

    }

    override fun onResume() {
        super.onResume()
        val transaction =
            supportFragmentManager.beginTransaction()
        transaction.replace(R.id.con, HomeFragment())
        transaction.commit()

        binding.bottomNavView.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {

                R.id.home -> {
                    val transaction12 =
                        supportFragmentManager.beginTransaction()
                    transaction12.replace(R.id.con, HomeFragment(), "Fragment")
                    transaction12.commit()
                }
                R.id.recent -> {
                    val transaction1 =
                        supportFragmentManager.beginTransaction()
                    transaction1.replace(R.id.con, RecentFragment()).addToBackStack("Fragment")
                    transaction1.commit()
                }
                R.id.favourite -> {
                    val transaction2 =
                        supportFragmentManager.beginTransaction()
                    transaction2.replace(R.id.con, FavouriteFragment()).addToBackStack("Fragment")
                    transaction2.commit()
                }
                R.id.settings -> {
                    val transaction3 =
                        supportFragmentManager.beginTransaction()
                    transaction3.replace(R.id.con, SettingsFragment()).addToBackStack("Fragment")
                    transaction3.commit()
                }
            }
            true
        }
    }

//    override fun onBackPressed() {
//        supportFragmentManager.popBackStack()
//        super.onBackPressed()
//    }
}