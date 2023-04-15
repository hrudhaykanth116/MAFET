package com.hrudhaykanth116.core.udf

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

// TODO:  Have interfaces to not force all these behavior.
abstract class UDFFragment<STATE, EFFECT, EVENT, BINDING : ViewDataBinding>(
    @LayoutRes private val layoutId: Int,
) : Fragment() {

    protected abstract val viewModel: UDFViewModel<STATE, EFFECT, EVENT>

    protected lateinit var binding: BINDING

    protected abstract fun processNewState(state: STATE)
    protected abstract fun processNewEffect(effect: EFFECT)
    protected abstract fun processNewEvent(event: EVENT)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<BINDING>(
            inflater, layoutId, container, false
        )
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initResumeStateObservers()
    }

    private fun initResumeStateObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    viewModel.stateFlow.collect{
                        processNewState(it)
                    }
                }

                launch {
                    viewModel.effect.collect { effect ->
                        processNewEffect(effect)
                    }
                }

            }
        }
    }

    fun processEvent(event: EVENT){
        viewModel.processEvent(event)
    }


}