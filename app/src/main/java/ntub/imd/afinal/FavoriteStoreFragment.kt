package ntub.imd.afinal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.findNavController

class FavoriteStoreFragment : Fragment() {
    private val storeViewModel: StoreViewModel by activityViewModels()
    private lateinit var adapter: StoreAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_store, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewFavorite)
        adapter = StoreAdapter(emptyList(), onItemClick = { store ->
            val bundle = Bundle().apply { putInt("storeId", store.id) }
            findNavController().navigate(R.id.action_favoriteStoreFragment_to_storeEditFragment, bundle)
        }, onFavoriteClick = { store ->
            storeViewModel.toggleFavorite(store.id)
        })
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        storeViewModel.stores.observe(viewLifecycleOwner) { list ->
            adapter.updateData(list.filter { it.isFavorite })
        }

        // 返回按鈕
        view.findViewById<View>(R.id.btnBack)?.setOnClickListener {
            findNavController().popBackStack()
        }

        // 刪除全部最愛按鈕
        view.findViewById<View>(R.id.btnDelete)?.setOnClickListener {
            val list = storeViewModel.stores.value ?: return@setOnClickListener
            list.filter { it.isFavorite }.forEach { store ->
                store.isFavorite = false
            }
            // 透過ViewModel方法觸發LiveData更新
            storeViewModel.updateStoreList(list)
        }
    }
}
