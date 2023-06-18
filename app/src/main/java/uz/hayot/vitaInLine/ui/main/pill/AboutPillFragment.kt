package uz.hayot.vitaInLine.ui.main.pill


import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import uz.hayot.vitaInLine.databinding.FragmentAboutPillBinding


class AboutPillFragment : Fragment() {
    private var _binding: FragmentAboutPillBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAboutPillBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.animationPillAboutView.visibility=View.VISIBLE
        initFakeAboutPill()

        binding.aboutPillBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }


    }

    private fun setVideoView(url: String) {
        lifecycle.addObserver(binding.pillVideoView)
        binding.pillVideoView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                binding.animationPillAboutView.visibility=View.GONE
                val videoId = getLastPath(url)
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })
    }




    private fun initFakeAboutPill() {
        arguments?.let {
            val url = it.getString("link")
            Log.e(TAG, "initFakeAboutPill: $url")
            binding.aboutPillName.text = it.getString("title")
            binding.aboutPillDesc.text = it.getString("desc")
            setVideoView(url.toString())

        }

    }

    private fun getLastPath(url: String): String {
        var index=0
        index= url.lastIndexOf('/')
        return url.substring(index+1)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}