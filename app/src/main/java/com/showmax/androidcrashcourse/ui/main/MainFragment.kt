package com.showmax.androidcrashcourse.ui.main

import Items
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.google.android.material.snackbar.Snackbar
import com.showmax.androidcrashcourse.R
import com.showmax.androidcrashcourse.databinding.ItemBinding
import com.showmax.androidcrashcourse.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val mainViewModel: MainViewModel by viewModels()
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            mainViewModel.onLoad(binding.textInputEditText.text.toString())
        }
        binding.textInputEditText.setOnEditorActionListener { v, actionId, event ->
            mainViewModel.onLoad(binding.textInputEditText.text.toString())
            true
        }
        binding.rvItems.adapter = ItemAdapter(object: ItemAdapter.OnItemListener {
            override fun onItemClicked(item: Items) {
                Snackbar.make(binding.root, "Clicked item: ${item.title}", Snackbar.LENGTH_SHORT).show()
            }
        })
        binding.rvItems.layoutManager = GridLayoutManager(
            context,
            resources.getInteger(R.integer.grid_colums),
            resources.getInteger(R.integer.grid_orientation),
            false
        )
        binding.rvItems.setHasFixedSize(true)
        mainViewModel.data.observe(viewLifecycleOwner, Observer {
            when (it) {
                SearchStateData.InProgress -> {
                    enableProgress(true)
                }
                is SearchStateData.DataCompleted -> {
                    enableProgress(false)
                    val size = it.items.size
                    Snackbar.make(binding.root, "Data received: $size", Snackbar.LENGTH_LONG).show()
                    val adapter = binding.rvItems.adapter as ItemAdapter
                    adapter.setItems(it.items)
                }
                is SearchStateData.Error -> {
                    enableProgress(false)
                    Snackbar.make(binding.root, it.error, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun enableProgress(enable: Boolean) {
        binding.progressBar.visibility = if (enable) View.VISIBLE else View.GONE
        binding.button.isEnabled = !enable
        binding.textInputEditText.isEnabled = !enable
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class ItemAdapter(private val listener: OnItemListener): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

        private var items: List<Items> = emptyList()

        class ItemViewHolder(private val itemBinding: ItemBinding, val listener: OnItemListener):RecyclerView.ViewHolder(itemBinding.root) {
            fun bind(item: Items) {
                val imageCandidate = item.images.find { it.type == "poster" && it.orientation == "square" } ?: item.images.first()
                Log.i(TAG, "bind: " + imageCandidate.link)
                itemBinding.imgItem.load(imageCandidate.link)
                itemBinding.txtTitle.text = item.title
                itemBinding.root.setOnClickListener { listener.onItemClicked(item)}
            }

            companion object {
                private const val TAG = "ItemViewHolder"
            }
        }

        interface OnItemListener {
            fun onItemClicked(item: Items)
        }

        fun setItems(items: List<Items>) {
            this.items = items
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val itemBinding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ItemViewHolder(itemBinding, listener)
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val item = items[position]
            holder.bind(item)
        }

    }

}