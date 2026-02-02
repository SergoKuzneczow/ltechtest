package com.sergokuzneczow.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sergokuzneczow.home.R
import com.sergokuzneczow.home.databinding.ModalBottomSheetSortingBinding
import com.sergokuzneczow.model.Sorting

internal class ModalBottomSheet : BottomSheetDialogFragment(R.layout.modal_bottom_sheet_sorting) {

    val args: ModalBottomSheetArgs by navArgs()

    private lateinit var binding: ModalBottomSheetSortingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ModalBottomSheetSortingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        when (args.selectedSorting) {
            Sorting.DEFAULT -> {
                binding.sortingDefault.isChecked = true
                binding.sortingDate.isChecked = false
            }

            Sorting.DATE -> {
                binding.sortingDefault.isChecked = false
                binding.sortingDate.isChecked = true
            }
        }

        if (args.selectedSorting != Sorting.DEFAULT) binding.sortingDefault.setOnClickListener {
            val action = ModalBottomSheetDirections.actionModalBottomSheetToHomeFragment(Sorting.DEFAULT)
            findNavController().navigate(action)
        }

        if (args.selectedSorting != Sorting.DATE) binding.sortingDate.setOnClickListener {
            val action = ModalBottomSheetDirections.actionModalBottomSheetToHomeFragment(Sorting.DATE)
            findNavController().navigate(action)
        }
    }
}