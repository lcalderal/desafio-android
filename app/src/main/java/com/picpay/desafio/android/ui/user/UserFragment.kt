package com.picpay.desafio.android.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.R
import com.picpay.desafio.android.adapter.UserListAdapter
import com.picpay.desafio.android.databinding.FragmentUserBinding
import com.picpay.desafio.android.state.ResourceState
import com.picpay.desafio.android.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserFragment : BaseFragment<FragmentUserBinding, UserViewModel>() {

    override val viewModel: UserViewModel by viewModels()
    private val userListAdapter by lazy { UserListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        getObserver()
    }

    private fun getObserver() = lifecycleScope.launch {
      viewModel.users.collect { resource ->
          when(resource) {
              is ResourceState.Success -> {
                  resource.data?.let { values ->
                      binding.userListProgressBar.visibility = View.GONE
                      userListAdapter.users = values
                  }
              }
              is ResourceState.Error -> {
                  binding.userListProgressBar.visibility = View.GONE
                  resource.message?.let { message ->
                      Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
                  }
              }
              is ResourceState.Loading -> {
                  binding.userListProgressBar.visibility = View.VISIBLE
              }
          }
      }
    }


    private fun setupViews()= with(binding) {
        recyclerView.apply {
            adapter = userListAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUserBinding = FragmentUserBinding.inflate(inflater, container, false)
}