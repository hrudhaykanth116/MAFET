package com.hrudhaykanth116.core.common.utils.extensions.iteratorextensions

import android.view.View
import android.view.ViewGroup

operator fun ViewGroup.iterator() = object: MutableIterator<View>{

    private var index = 0

    override fun hasNext(): Boolean {
        return index < childCount
    }

    override fun next(): View {
        return getChildAt(index++)
    }

    override fun remove() {
        removeViewAt(--index)
    }

}