package by.kleban.dogdairy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.kleban.dogdairy.R
import by.kleban.dogdairy.data.entities.dogbreeds.DogBreed
import com.squareup.picasso.Picasso


class ShowBreedsAdapter : RecyclerView.Adapter<ShowBreedsAdapter.ShowBreedsViewHolder>() {

    private val breedList = mutableListOf<DogBreed>()

    fun setItems(list: List<DogBreed>) {
        breedList.clear()
        breedList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowBreedsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_breed, parent, false)
        return ShowBreedsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShowBreedsViewHolder, position: Int) {
        val breed = breedList[position]

        holder.breedText.text = breed.breed
        Picasso.get()
            .load(breed.image.url)
            .error(R.drawable.error_image)
            .into(holder.breedImage)

    }

    override fun getItemCount(): Int {
        return breedList.size
    }

    inner class ShowBreedsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val breedImage: ImageView = view.findViewById(R.id.item_image)
        val breedText: TextView = view.findViewById(R.id.item_breed)

    }
}