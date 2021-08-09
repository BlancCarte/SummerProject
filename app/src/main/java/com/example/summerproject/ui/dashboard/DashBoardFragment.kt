package com.example.summerproject.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.summerproject.databinding.FragmentDashboradBinding

class DashBoardFragment : Fragment() {

	private var mBinding : FragmentDashboradBinding? = null
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val binding = FragmentDashboradBinding.inflate(inflater,container,false)

		mBinding = binding

		return mBinding?.root
	}

	override fun onDestroyView() {
		mBinding = null
		super.onDestroyView()
	}
}