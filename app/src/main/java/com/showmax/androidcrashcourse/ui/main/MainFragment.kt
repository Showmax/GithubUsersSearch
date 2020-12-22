package com.showmax.androidcrashcourse.ui.main

import Item
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsIntent.SHARE_STATE_ON
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            mainViewModel.onLoad(binding.textInputEditText.text.toString())
        }
        binding.textInputEditText.setOnEditorActionListener { _, _, _ ->
            mainViewModel.onLoad(binding.textInputEditText.text.toString())
            true
        }
        binding.rvItems.adapter = ItemAdapter(object : ItemAdapter.OnItemListener {
            override fun onItemClicked(item: Item) {
                context?.let {
                    CustomTabsIntent.Builder().apply {
                        setDefaultColorSchemeParams(
                            CustomTabColorSchemeParams.Builder()
                                .setToolbarColor(ContextCompat.getColor(it, R.color.colorPrimary))
                                .setSecondaryToolbarColor(
                                    ContextCompat.getColor(
                                        it,
                                        R.color.colorPrimaryDark
                                    )
                                )
                                .build()
                        )
                        setShowTitle(true)
                        setExitAnimations(it, android.R.anim.fade_in, android.R.anim.fade_out)
                        setShareState(SHARE_STATE_ON)
                    }
                        .build()
                        .launchUrl(it, Uri.parse(item.htmlUrl))
                }
            }

            override fun onItemLongClicked(item: Item): Boolean {
                Snackbar.make(binding.root, "Clicked item: ${item.login}", Snackbar.LENGTH_SHORT)
                    .show()
                return true
            }
        })
        binding.rvItems.layoutManager = GridLayoutManager(
            context,
            resources.getInteger(R.integer.grid_colums),
            resources.getInteger(R.integer.grid_orientation),
            false
        )
        binding.rvItems.setHasFixedSize(true)
        mainViewModel.data.observe(viewLifecycleOwner, {
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
        binding.progressBar.isVisible = enable
        binding.button.isEnabled = !enable
        binding.textInputEditText.isEnabled = !enable
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class ItemAdapter(private val listener: OnItemListener) :
        RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

        private var items: List<Item> = emptyList()

        class ItemViewHolder(
            private val itemBinding: ItemBinding,
            private val listener: OnItemListener
        ) : RecyclerView.ViewHolder(itemBinding.root) {
            fun bind(item: Item) {
                Log.i(TAG, "bind: " + item.avatarUrl)
                itemBinding.imgItem.load(item.avatarUrl)
                itemBinding.txtTitle.text = item.login
                itemBinding.root.setOnClickListener { listener.onItemClicked(item) }
                itemBinding.root.setOnLongClickListener { listener.onItemLongClicked(item) }
            }

            companion object {
                private const val TAG = "ItemViewHolder"
            }
        }

        interface OnItemListener {
            fun onItemClicked(item: Item)
            fun onItemLongClicked(item: Item): Boolean
        }

        fun setItems(items: List<Item>) {
            this.items = items
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val itemBinding =
                ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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