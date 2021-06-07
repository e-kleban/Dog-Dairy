package by.kleban.dogdairy.adapter.gridlayoutmanager

import androidx.recyclerview.widget.GridLayoutManager
import by.kleban.dogdairy.adapter.DogPageAdapter


class DogSpanSizeLookup(private val adapter: DogPageAdapter) : GridLayoutManager.SpanSizeLookup() {
    override fun getSpanSize(position: Int): Int {
        return when (adapter.getItemViewType(position)) {
            DogPageAdapter.TYPE_HEADER -> 3
            DogPageAdapter.TYPE_POST -> 1
            else -> -1
        }
    }
}