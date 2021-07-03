package by.kleban.dogdiary.ui.dogpage.adapter

import androidx.recyclerview.widget.GridLayoutManager


class DogSpanSizeLookup(
    private val adapter: DogPageAdapter,
    private val maxSpanCount: Int
) : GridLayoutManager.SpanSizeLookup() {

    override fun getSpanSize(position: Int): Int {
        return when (adapter.getItemViewType(position)) {
            DogPageAdapter.TYPE_HEADER -> maxSpanCount
            DogPageAdapter.TYPE_POST -> 1
            else -> -1
        }
    }
}