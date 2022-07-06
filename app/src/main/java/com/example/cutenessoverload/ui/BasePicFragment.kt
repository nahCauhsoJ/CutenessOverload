package com.example.cutenessoverload.ui

import androidx.fragment.app.Fragment
import com.example.cutenessoverload.databinding.FragmentPicBinding

open class BasePicFragment : Fragment() {
    protected val binding by lazy { FragmentPicBinding.inflate(layoutInflater) }

}