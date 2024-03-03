package banquemisr.challenge05.ui.NowPlaying

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import banquemisr.challenge05.databinding.FragmentNowplayingBinding

class NowPlayingFragment : Fragment()
{

    private var _binding: FragmentNowplayingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        val dashboardViewModel = ViewModelProvider(this).get(NowPlayingViewModel::class.java)

        _binding = FragmentNowplayingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}