package com.example.musicplayer.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Toast
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import android.provider.MediaStore
import android.util.Log
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.widget.SearchView
import com.example.musicplayer.R
import com.example.musicplayer.utils.ItemClickListener
import com.example.musicplayer.adapter.SongAdapter
import com.example.musicplayer.model.PlaylistModel
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

import com.example.musicplayer.databinding.FragmentHomeBinding
import java.util.*
import java.util.Locale.filter

class HomeFragment : Fragment(), ItemClickListener {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var arrayList: ArrayList<PlaylistModel>
    private lateinit var adapter: SongAdapter
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    private var mediaPlayer: MediaPlayer? = null
    private var startTime = 0.0
    private val finalTime = 0.0
    private val myHandler: Handler = Handler()
    private val forwardTime = 10000
    private val backwardTime = 10000
    private var oneTimeOnly = 0
    private var currentIndex: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)


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
            getData()
        }

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

//        binding.btnNextCurrent.setOnClickListener {
//            if (currentIndex <= arrayList.size - 1) {
//                currentIndex++
//            } else {
//                currentIndex = 0
//            }
//            if (mediaPlayer?.isPlaying!!) {
//                mediaPlayer!!.stop()
//            }
////            mediaPlayer = MediaPlayer.create(requireContext(), arrayList.get(currentIndex))
//            mediaPlayer!!.start()
//        }


        binding.btnPauseCurrent.setOnClickListener {
            mediaPlayer?.pause()
            binding.btnPauseCurrent.isEnabled = false
            binding.btnPlayCurrent.isEnabled = true
        }
        binding.btnPlayCurrent.setOnClickListener {
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

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.item_search)
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
        return super.onCreateOptionsMenu(menu, menuInflater)
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
            Toast.makeText(context, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            adapter.filterList(filteredlist)
        }
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

    private fun nextVideo() {
        binding.btnNextCurrent.setOnClickListener {

        }
    }

    private fun getData() {
        getMusic()
        adapter = SongAdapter(requireContext(), arrayList, this)
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
    ) {
        binding.textCurrentSongName.text = name
        binding.textArtistCurrent.text = artists
        binding.cardViewHome.visibility = View.VISIBLE

        mediaPlayer = player
        binding.seekbarCurrentSong.isClickable = false
//        binding.button2.isEnabled = false
    }
}