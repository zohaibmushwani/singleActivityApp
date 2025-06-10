package com.example.singleactivityapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.singleactivityapp.R
import android.widget.Button

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.singleactivityapp.HomeViewModel
import com.example.singleactivityapp.ItemAdapter

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = ItemAdapter()
        recyclerView.adapter = adapter

        // Observe ViewModel data
        viewModel.items.observe(viewLifecycleOwner) { items ->
            adapter.setItems(items)
        }

        // Set up buttons
        val pdfButton: Button = view.findViewById(R.id.pdf_button)
        val slidesButton: Button = view.findViewById(R.id.slides_button)
        val docsButton: Button = view.findViewById(R.id.docs_button)
        val allButton: Button = view.findViewById(R.id.all_button)

        pdfButton.setOnClickListener { viewModel.filterItems("PDF") }
        slidesButton.setOnClickListener { viewModel.filterItems("Slides") }
        docsButton.setOnClickListener { viewModel.filterItems("Docs") }
        allButton.setOnClickListener { viewModel.filterItems("All") }

        // Set "All" as default if no saved state
        if (savedInstanceState == null) {
            viewModel.filterItems("All")
        }
    }
}