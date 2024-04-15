package com.home.brewerhelper.ui.carbonation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.home.brewerhelper.databinding.FragmentCarbonationCalculatorBinding

class CarbonationCalculatorFragment : Fragment() {

    private var _binding: FragmentCarbonationCalculatorBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val carbonationViewModel =
            ViewModelProvider(this).get(CarbonationViewModel::class.java)

        _binding = FragmentCarbonationCalculatorBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textCarbcalc
        carbonationViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}