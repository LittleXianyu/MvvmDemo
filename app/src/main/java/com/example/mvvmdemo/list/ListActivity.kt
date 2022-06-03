package com.example.mvvmdemo.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.mvvmdemo.R
import com.example.mvvmdemo.databinding.ListBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ListBinding = DataBindingUtil.setContentView(this, R.layout.list)
        val adapter = InfoListAdapter()

        binding.plantList.adapter = adapter
//        binding.plantList.layoutManager = StaggeredGridLayoutManager(
//            2,
//            StaggeredGridLayoutManager.VERTICAL
//        )
        lifecycleScope.launch {
            delay(1000)
            val list = mutableListOf<ItemModel>()
            for (i in 0..30) {
                if (i % 2 == 1) {
                    list.add(ItemModel("_" + i, "cat" + "第二列 数据 第二列 数据 第二列 数据 第二列 数据 第二列 数据"))
                } else {
                    list.add(ItemModel("_" + i, "cat"))
                }
            }
            adapter.submitList(list)
        }
    }
}
