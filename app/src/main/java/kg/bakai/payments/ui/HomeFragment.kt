package kg.bakai.payments.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import kg.bakai.payments.R
import kg.bakai.payments.data.model.Status
import kg.bakai.payments.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel by viewModel<HomeViewModel>()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = Navigation.findNavController(view)

        homeViewModel.getPayments()

        binding.apply {
            val adapter = PaymentsAdapter()
            recyclerView.adapter = adapter

            swipeLayout.setOnRefreshListener {
                homeViewModel.getPayments()
            }

            homeViewModel.paymentsResponse.observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        adapter.submitItems(resource.data)
                        recyclerView.visibility = View.VISIBLE
                        btnExit.visibility = View.VISIBLE
                        swipeLayout.isRefreshing = false
                    }
                    Status.LOADING -> {
                        recyclerView.visibility = View.GONE
                        btnExit.visibility = View.GONE
                        swipeLayout.isRefreshing = true
                    }
                    Status.ERROR -> {
                        swipeLayout.isRefreshing = false
                        Toast.makeText(requireContext(), resource.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }

            btnExit.setOnClickListener {
                homeViewModel.logOut()
                navController.navigate(R.id.action_navigation_home_to_navigation_login)
            }
        }

    }
}