package com.example.musicplayer.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Toast
import android.content.pm.PackageManager
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation
import androidx.core.app.ActivityCompat
import android.provider.MediaStore
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.musicplayer.R
import com.example.musicplayer.utils.ItemClickListener
import com.example.musicplayer.adapter.SongAdapter
import com.example.musicplayer.database.FavoriteDatabase
import com.example.musicplayer.database.FavoriteEntity
import com.example.musicplayer.databinding.FragmentHomeBinding
import com.example.musicplayer.model.PlaylistModel
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

import java.util.*

class HomeFragment : Fragment(), ItemClickListener {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var arrayList: ArrayList<PlaylistModel>
    private lateinit var adapter: SongAdapter
    lateinit var fetchLIst: List<FavoriteEntity>
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    lateinit var database: FavoriteDatabase
    private var mediaPlayer: MediaPlayer? = null
    private var startTime = 0.0
    private val finalTime = 0.0
    private val myHandler: Handler = Handler()
    private val forwardTime = 10000
    private val backwardTime = 10000
    private var oneTimeOnly = 0
    private var currentIndex: Int = 0
    private var song_index = 0
    private var Pos = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        database = FavoriteDatabase.getInstance(requireContext())
        fetchLIst = listOf<FavoriteEntity>()
//        nextMusic()
//        playSong()
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
        } else {
//
//            for (i in fetchLIst.indices) {
//                database.songDao()
//                    .addData(FavoriteEntity(0,
//                        arrayList[i].artists,
//                        arrayList[i].name,
//                        false, rec = false, 0))
//            }
            binding.getDataBtn.setOnClickListener {
                Snackbar.make(it, "Data Received", Snackbar.LENGTH_SHORT).show()
                for (i in 0 until arrayList.size) {
                    database.songDao()
                        .addData(FavoriteEntity(0,
                            arrayList[i].artists,
                            arrayList[i].name,
                            false, rec = false, 0))
                }
                Log.d("TAG", "onCreateView: ${arrayList.size}")
            }
            fetchLIst = database.songDao().fetchList()
            Log.d("TAG", "onCreateView: fetching data : $fetchLIst ")
            getData()
            nextMusic()
            prevMusic()
        }

//        binding.editText.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
//            }
//
//            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
//            }
//
//            override fun afterTextChanged(editable: Editable) {
//                //after the change calling the method and passing the search input
//                filter(editable.toString())
//            }
//        })

        binding.btn10SecNextCurrent.setOnClickListener {
            val temp = startTime
            if ((temp + forwardTime) < finalTime) {
                startTime += forwardTime
                mediaPlayer?.seekTo(startTime.toInt())

            } else {
                Toast.makeText(
                    context,
                    "Cannot jump forward 10 seconds",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        database = FavoriteDatabase.getInstance(requireContext())

        binding.fetchDataBtn.setOnClickListener {
            val fethch = database.songDao().fetchList()
            Log.d("Fetch", "onCreateView: $fethch")
        }

//        binding.mic.setOnClickListener {
//            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
//            intent.putExtra(
//                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
//            )
//            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
//            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Something...")
//
//            try {
//                activityResultLauncher.launch(intent)
//            } catch (e: ActivityNotFoundException) {
//                Toast.makeText(context, "Device not supported", Toast.LENGTH_SHORT)
//                    .show()
//            }
//        }

        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it!!.resultCode == AppCompatActivity.RESULT_OK && it.data != null) {
                    val speechToText =
                        it.data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<Editable>
                    binding.editText.text = speechToText[0]
                }
            }

        binding.btnPauseCurrent.setOnClickListener {
            mediaPlayer?.pause()
            binding.btnPauseCurrent.isEnabled = false
            binding.btnPlayCurrent.isEnabled = true
        }

        binding.btnPlayCurrent.setOnClickListener {
            playSong(Pos)
        }

        binding.btn10SecBackCurrent.setOnClickListener {
            val temp = startTime
            if ((temp - backwardTime) > 0) {
                startTime -= backwardTime
                mediaPlayer?.seekTo(startTime.toInt())

            } else {
                Toast.makeText(
                    context,
                    "Cannot jump backward 10 seconds",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return binding.root
    }

    @SuppressLint("Recycle")
    private fun getMusic() {
        arrayList = ArrayList()
        val resolver = requireActivity().contentResolver
        val songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val songCursor = resolver.query(songUri, null, null, null, null)
        if (songCursor != null && songCursor.moveToFirst()) {
            val songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val songArtists = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val songPath = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA)
            do {
                val currentTitle = songCursor.getString(songTitle)
                val currentArtists = songCursor.getString(songArtists)
                val currentPath = songCursor.getString(songPath)
                Log.d("TAG", "getMusic: $currentPath")
                arrayList.add(PlaylistModel(currentTitle, currentArtists, currentPath))
            } while (songCursor.moveToNext())
        }
    }

    private fun getData() {
        getMusic()
        adapter = SongAdapter(requireContext(), fetchLIst, arrayList, this)
        binding.recyclerHomeFragment.adapter = adapter
        binding.recyclerHomeFragment.layoutManager = LinearLayoutManager(context)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        Toast.makeText(context, "permission granted", Toast.LENGTH_SHORT).show()
                        getData()
                    } else {
                        Toast.makeText(context, "permission denied", Toast.LENGTH_SHORT).show()
                    }
                    return
                }
            }
        }
    }

    private val UpdateSongTime: Runnable by lazy {
        object : Runnable {
            override fun run() {
                startTime = mediaPlayer!!.currentPosition.toDouble()
                binding.textStartSecond.text = String.format(
                    "%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes(startTime.toLong()),
                    TimeUnit.MILLISECONDS.toSeconds(startTime.toLong()) -
                            TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(
                                    startTime.toLong()
                                )
                            )
                )
                binding.seekbarCurrentSong.progress = startTime.toInt()
                myHandler.postDelayed(this, 100)
            }
        }
    }

    override fun onItemClick(
        name: String,
        artists: String,
        path: String,
        stime: Int,
        etime: Int,
        player: MediaPlayer,
        songIndex: Int,
        position: Int,
    ) {
        binding.textCurrentSongName.text = name
        binding.textArtistCurrent.text = artists
        binding.cardViewHome.visibility = View.VISIBLE
        song_index = songIndex
        Log.d("TAG", "SongIndex: $songIndex")
        Pos = position
        Log.d("TAG", "SongPos: $Pos")

        mediaPlayer = player
        binding.seekbarCurrentSong.isClickable = false
//        binding.button2.isEnabled = false
    }

//    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
//        menuInflater.inflate(R.menu.search_menu, menu)
//        val searchItem = menu.findItem(R.id.item_search)
//        val searchView: SearchView = searchItem?.actionView as SearchView
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                if (newText != null) {
//                    filter(text = newText)
//                }
//                return false
//            }
//        })
//        return super.onCreateOptionsMenu(menu, menuInflater)
//    }

//    private fun filter(text: String) {
//        val filteredNames = ArrayList<PlaylistModel>()
//        arrayList.filterTo(filteredNames) {
//            it.name.toLowerCase().contains(text.toLowerCase())
//        }
//        //calling a method of the adapter class and passing the filtered list
//        adapter.filterList(filteredNames)
//    }

    fun playSong(Pos: Int) {
        mediaPlayer?.start()
        val finalTime = mediaPlayer?.duration
        val startTime = mediaPlayer?.currentPosition

        if (oneTimeOnly == 0) {
            if (finalTime != null) {
                binding.seekbarCurrentSong.max = finalTime
            }
            oneTimeOnly = 1
        }
        if (finalTime != null) {
            binding.textEndSecond.text = String.format(
                "%d:%d",
                TimeUnit.MILLISECONDS.toMinutes(finalTime.toLong()),
                TimeUnit.MILLISECONDS.toSeconds(finalTime.toLong()) -
                        TimeUnit.MINUTES.toSeconds(
                            TimeUnit.MILLISECONDS.toMinutes(
                                finalTime.toLong()
                            )
                        )
            )
        }
        if (startTime != null) {
            binding.textStartSecond.text = String.format(
                "%d:%d",
                TimeUnit.MILLISECONDS.toMinutes(startTime.toLong()),
                TimeUnit.MILLISECONDS.toSeconds(startTime.toLong()) -
                        TimeUnit.MINUTES.toSeconds(
                            TimeUnit.MILLISECONDS.toMinutes(
                                startTime.toLong()
                            )
                        )
            )
        }

        if (startTime != null) {
            binding.seekbarCurrentSong.progress = startTime
        }
        myHandler.postDelayed(UpdateSongTime, 100)
        binding.btnPauseCurrent.isEnabled = true
        binding.btnPlayCurrent.isEnabled = false
    }

    private fun nextMusic() {
        binding.btnNextCurrent.setOnClickListener {
            Log.d("TAG", "nextMusic: song index$song_index")
            Log.d("TAG", "nextMusic: arraylist size " + arrayList.size)
            if (song_index < (arrayList.size) - 1) {
                song_index++
                playSong(song_index)
            } else {
                song_index = 0
                playSong(song_index)
            }
        }
    }

    private fun prevMusic() {
        binding.btnPreviousCurrent.setOnClickListener {
            if (song_index > 0) {
                song_index--
                playSong(song_index)
            } else {
                song_index = arrayList.size - 1
                playSong(song_index)
            }
        }
    }

}