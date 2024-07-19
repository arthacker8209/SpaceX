package com.deepak.spacex.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Created by Deepak Kumawat on 17/07/24.
 * Base class for all activities of app which mainly fulfill the purpose of creating and destroying
 * binding.
 * Child activity need to implement inflateLayout() with specific binding.
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: ViewBinding? = null
    abstract fun inflateLayout(layoutInflater: LayoutInflater): VB

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateLayout(layoutInflater)
        setupView()
        return binding.root

    }

    abstract fun fetchData()

    abstract fun setupView()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}