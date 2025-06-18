package ntub.imd.afinal

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StoreAdapter(
    private var stores: List<Store>,
    private val onItemClick: (Store) -> Unit,
    private val onFavoriteClick: (Store) -> Unit
) : RecyclerView.Adapter<StoreAdapter.StoreViewHolder>() {
    inner class StoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvPhone: TextView = itemView.findViewById(R.id.tvPhone)
        val tvAddress: TextView = itemView.findViewById(R.id.tvAddress)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
        val imgFavorite: ImageView = itemView.findViewById(R.id.imgFavorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_store, parent, false)
        return StoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val store = stores[position]
        holder.tvName.text = store.name
        holder.tvPhone.text = store.phone
        holder.tvAddress.text = store.address
        holder.ratingBar.rating = store.rating
        holder.itemView.setOnClickListener { onItemClick(store) }
        holder.tvPhone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${store.phone}"))
            holder.itemView.context.startActivity(intent)
        }
        // 最愛星星顯示與點擊
        holder.imgFavorite.setImageResource(
            if (store.isFavorite) android.R.drawable.btn_star_big_on else android.R.drawable.btn_star_big_off
        )
        holder.imgFavorite.setOnClickListener {
            onFavoriteClick(store)
        }
    }

    override fun getItemCount(): Int = stores.size

    fun updateData(newStores: List<Store>) {
        stores = newStores
        notifyDataSetChanged()
    }
}
