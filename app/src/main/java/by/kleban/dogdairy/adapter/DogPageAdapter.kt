package by.kleban.dogdairy.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.kleban.dogdairy.R
import by.kleban.dogdairy.adapter.mapper.DogToItemHeaderMapper
import by.kleban.dogdairy.core.picasso.transformation.CircleTransform
import by.kleban.dogdairy.entities.Dog
import by.kleban.dogdairy.entities.Sex
import com.squareup.picasso.Picasso


class DogPageAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<Item>()
    private val headerMapper = DogToItemHeaderMapper()

//    fun submitData(list: List<Item>) {
//        items.clear()
//        items.addAll(list)
//        notifyDataSetChanged()
//    }

    fun setHeader(dog: Dog) {
        items.removeIf { it is Item.Header }
        val header = headerMapper.map(dog)
        items.add(POSITION_HEADER, header)
        notifyItemChanged(POSITION_HEADER)
    }
//
//    fun setPosts() {
//        notifyDataSetChanged()
//    }
//
//    fun addPost() {
//        notifyDataSetChanged()
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_HEADER -> {
                val view = layoutInflater.inflate(R.layout.item_dog_page_header, parent, false)
                HeaderViewHolder(view)
            }
            TYPE_POST -> {
                val view = layoutInflater.inflate(R.layout.item_dog_page_post, parent, false)
                PostViewHolder(view)
            }
            else -> throw Exception()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.bind(position)
            is PostViewHolder -> holder.bind(position)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Item.Header -> TYPE_HEADER
            is Item.Post -> TYPE_POST
        }
    }

    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val dogImage: ImageView = view.findViewById(R.id.page_dog_image)
        private val dogName: TextView = view.findViewById(R.id.page_dog_name)
        private val dogAge: TextView = view.findViewById(R.id.page_dog_age)
        private val dogSex: ImageView = view.findViewById(R.id.page_dog_sex)
        private val dogBreed: TextView = view.findViewById(R.id.page_dog_breed)
        private val dogDescription: TextView = view.findViewById(R.id.page_dog_description)

        fun bind(position: Int) {
            val recyclerViewModel = items[position] as Item.Header
            dogName.text = recyclerViewModel.name
            dogAge.text = context.getString(R.string.dog_age, recyclerViewModel.age)
            dogBreed.text = recyclerViewModel.breed
            dogDescription.text = recyclerViewModel.description
            if (recyclerViewModel.sex == Sex.FEMALE) {
                dogSex.setImageResource(R.drawable.ic_sex_female)
            } else if (recyclerViewModel.sex == Sex.MALE) {
                dogSex.setImageResource(R.drawable.ic_sex_male)
            }
            Picasso.get().cancelRequest(dogImage)
            Picasso.get()
                .load(recyclerViewModel.image)
                .error(R.drawable.error_image)
                .transform(CircleTransform())
                .into(dogImage)
        }
    }

    inner class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val postImage: ImageView = view.findViewById(R.id.item_post_image)

        fun bind(position: Int) {
            val recyclerViewModel = items[position] as Item.Post
            Picasso.get()
                .load(recyclerViewModel.imageUrl)
                .error(R.drawable.error_image)
                .into(postImage)
        }
    }

    sealed class Item {
        class Header(
            val name: String,
            val age: Int,
            val breed: String,
            val image: String,
            val description: String,
            val sex: Sex
        ) : Item()

        class Post(
            val imageUrl: String
        ) : Item()
    }

    companion object {
        const val POSITION_HEADER = 0
        const val TYPE_HEADER = 0
        const val TYPE_POST = 1
    }
}