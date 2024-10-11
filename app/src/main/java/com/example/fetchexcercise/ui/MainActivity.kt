package com.example.fetchexcercise.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fetchexcercise.R
import com.example.fetchexcercise.data.HiringItem
import com.example.fetchexcercise.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
//    private val viewModel = ViewModelProvider(this)[MainViewModel::class.java]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.hiringItems.observe(this) { groupedItems ->
            displayItems(groupedItems)
        }

        viewModel.fetchItems()

        binding.toggleSort.setOnCheckedChangeListener { _, isChecked ->
            viewModel.toggleSorting()
            binding.toggleSort.text = if (isChecked) getString(R.string.sort_by_name_including_number)  else getString(R.string.sort_by_name_only)
        }
    }

    private fun displayItems(groupedItems: Map<Int, List<HiringItem>>) {
        val adapter = GroupedItemAdapter(groupedItems, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }
}