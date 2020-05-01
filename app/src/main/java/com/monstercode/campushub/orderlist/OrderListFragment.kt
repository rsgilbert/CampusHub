package com.monstercode.campushub.orderlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.monstercode.campushub.R
import com.monstercode.campushub.database.getDatabase
import com.monstercode.campushub.databinding.OrderListBinding
import com.monstercode.campushub.repository.OrderRepository

class OrderListFragment : Fragment() {

    private lateinit var orderListViewModel: OrderListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding: OrderListBinding = DataBindingUtil.inflate(
            inflater, R.layout.order_list, container, false)

        orderListViewModel = getOrderListViewModel()
        binding.orderListViewModel = orderListViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.orderList.adapter = OrderListAdapter(orderListClickListener)

        return binding.root
    }

    /**
     * Create and return an instance of ListViewModel
     */
    private fun getOrderListViewModel(): OrderListViewModel {
        val activity = requireNotNull(activity)
        val database = getDatabase(activity)
        val repository = OrderRepository(database.orderDao)
        return ViewModelProviders.of(activity, OrderListViewModel.FACTORY(repository))
            .get(OrderListViewModel::class.java)
    }

    private val orderListClickListener =
        OrderListAdapter.OnClickListener {
            Toast.makeText(context, "Order clicked: ${it.phoneNumber}", Toast.LENGTH_LONG).show()
        }
}

