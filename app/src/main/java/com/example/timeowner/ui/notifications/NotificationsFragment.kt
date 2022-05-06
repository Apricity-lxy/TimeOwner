package com.example.timeowner.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.timeowner.MyDatabaseHelper
import com.example.timeowner.databinding.FragmentNotificationsBinding


//用户

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        intViews()
        return binding.root
    }



    private fun intViews(){
        binding.userManual.userNodeTextView.text = "使用说明"
        binding.userPrivacy.userNodeTextView.text = "隐私协议"
        binding.userProtocol.userNodeTextView.text = "关于我们"
        binding.userAuthors.userNodeTextView.text = "隐私协议"
        binding.userShare.userNodeTextView.text = "分享"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}