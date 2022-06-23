package com.picpay.desafio.android.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.R
import com.picpay.desafio.android.adapter.UserListAdapter
import com.picpay.desafio.android.data.model.UserModel
import com.picpay.desafio.android.databinding.FragmentUserBinding
import com.picpay.desafio.android.service.PicPayService
import com.picpay.desafio.android.state.ResourceState
import com.picpay.desafio.android.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.list_item_user.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
                      userListAdapter.users = values.users
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
//        progressBar.visibility = View.VISIBLE
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUserBinding = FragmentUserBinding.inflate(inflater, container, false)
}

//private val url = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"
//
//private val gson: Gson by lazy { GsonBuilder().create() }
//
//private val okHttp: OkHttpClient by lazy {
//    OkHttpClient.Builder()
//        .build()
//}
//
//private val retrofit: Retrofit by lazy {
//    Retrofit.Builder()
//        .baseUrl(url)
//        .client(okHttp)
//        .addConverterFactory(GsonConverterFactory.create(gson))
//        .build()
//}
//
//private val service: PicPayService by lazy {
//    retrofit.create(PicPayService::class.java)
//}

//override fun onCreateView(
//    inflater: LayoutInflater,
//    container: ViewGroup?,
//    savedInstanceState: Bundle?
//): View? {
//    super.onResume()
//
//    setupViews()
//
//    progressBar.visibility = View.VISIBLE
//    service.getUsers()
//        .enqueue(object : Callback<List<UserModel>> {
//            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
//                val message = getString(R.string.error)
//
//                progressBar.visibility = View.GONE
//                recyclerView.visibility = View.GONE
//
//                Toast.makeText(context, message, Toast.LENGTH_SHORT)
//                    .show()
//            }
//
//            override fun onResponse(call: Call<List<UserModel>>, response: Response<List<UserModel>>) {
//                progressBar.visibility = View.GONE
//
//                userListAdapter.users = response.body()!!
//            }
//        })
//    return super.onCreateView(inflater, container, savedInstanceState)
//}