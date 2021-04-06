package com.example.modulotech.ui.device.list

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.modulotech.R
import com.example.modulotech.di.Injection.provideDeviceListViewModelFactory
import com.example.modulotech.ui.device.detail.DeviceDetailActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_device_list.chips_container
import kotlinx.android.synthetic.main.fragment_device_list.device_list
import kotlinx.android.synthetic.main.fragment_device_list.swipe_container

class DeviceListFragment : Fragment() {

    private val mDeviceListViewModel: DeviceListViewModel by viewModels {
        provideDeviceListViewModelFactory(requireActivity().applicationContext)
    }

    private val mAdapter: DeviceAdapter by lazy {
        DeviceAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_device_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(device_list)
        setupSwipeRefreshLayout(swipe_container)
        setupCategoryFilter(chips_container)
    }

    private fun setupCategoryFilter(container: ChipGroup) {
        mDeviceListViewModel.deviceListCategories.observe(viewLifecycleOwner) {
            container.removeAllViews()
            it?.forEach { category ->
                container.addView(createChipCategory(category))
            }
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = mAdapter
        mAdapter.clickListener = {
            startActivity(Intent(requireContext(), DeviceDetailActivity::class.java).apply {
                putExtra(DeviceDetailActivity.ARG_ITEM_ID, it.id)
                putExtra(DeviceDetailActivity.ARG_ITEM_TYPE, it.getDeviceType())
            })
        }
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        mDeviceListViewModel.deviceList.observe(viewLifecycleOwner, {
            mAdapter.submitList(it?.filter(mDeviceListViewModel.categoryFilter))
        })

        setupSwipeDeleteCallback(recyclerView)
    }

    private fun setupSwipeRefreshLayout(swipeRefresh: SwipeRefreshLayout) {
        swipeRefresh.setOnRefreshListener {
            mDeviceListViewModel.refresh().observe(viewLifecycleOwner) {
                swipeRefresh.isRefreshing = false
            }
        }
    }

    private fun createChipCategory(category: String) =
        layoutInflater.inflate(R.layout.chip_category_filter_template, null).apply {
            this as Chip
            text = category
            setOnCheckedChangeListener { _, isChecked ->
                mDeviceListViewModel.setEnableCategory(category, isChecked)
                mAdapter.submitList(
                    mDeviceListViewModel.deviceList.value
                        ?.filter(mDeviceListViewModel.categoryFilter)
                )
            }
        }

    private fun setupSwipeDeleteCallback(recyclerView: RecyclerView) {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            private val mBackground = ColorDrawable(Color.RED)

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                val itemView = viewHolder.itemView
                if (dX > 0) { // Swiping to the right
                    mBackground.setBounds(
                        itemView.left, itemView.top,
                        itemView.left + dX.toInt(), itemView.bottom
                    )
                } else {
                    mBackground.setBounds(0, 0, 0, 0)
                }

                mBackground.draw(c)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                mDeviceListViewModel.deviceList.value?.let { list ->
                    list[viewHolder.adapterPosition].let { deletedItem ->
                        mDeviceListViewModel.deleteDevice(deletedItem)
                        Snackbar.make(
                            recyclerView, getString(
                                R.string.list_item_deleted,
                                deletedItem.name
                            ), Snackbar.LENGTH_LONG
                        )
                            .setAction(
                                getText(R.string.snackbar_undo)
                            ) {
                                mDeviceListViewModel.saveDevice(deletedItem)
                            }.show()
                    }
                }
            }
        }).attachToRecyclerView(recyclerView)
    }
}
