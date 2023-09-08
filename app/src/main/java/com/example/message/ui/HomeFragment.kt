package com.example.message.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.message.databinding.FragmentHomeBinding
import com.example.message.ui.adapter.UserAdapter
import com.example.message.util.Temp
import com.example.message.viewmodel.HomeViewModel
import com.example.message.viewmodel.HomeViewModelFactory

/**
 * [HomeFragment] Display list user from firebase(Realtime database)
 */
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by activityViewModels {
        HomeViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = UserAdapter {
            var uid = it.uid ?: return@UserAdapter
                val action = HomeFragmentDirections
                    .actionHomeFragmentToChatFragment(uid)
                findNavController().navigate(action)
            Temp.retriever = it
            Log.d("HomeFragment", Temp.retriever.toString())
        }

        binding.recyclerViewUsers.adapter = adapter

        viewModel.users.observe(this@HomeFragment.viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }
    }
}