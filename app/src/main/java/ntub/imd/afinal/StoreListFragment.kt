package ntub.imd.afinal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class StoreListFragment : Fragment() {
    private val storeViewModel: StoreViewModel by activityViewModels()
    private lateinit var adapter: StoreAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_store_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        adapter = StoreAdapter(emptyList(), onItemClick = { store ->
            val bundle = Bundle().apply { putInt("storeId", store.id) }
            findNavController().navigate(R.id.action_storeListFragment_to_storeEditFragment, bundle)
        }, onFavoriteClick = { store ->
            storeViewModel.toggleFavorite(store.id)
        })
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // 監聽資料變化
        storeViewModel.stores.observe(viewLifecycleOwner) {
            adapter.updateData(it)
        }

        view.findViewById<View>(R.id.btnAdd).setOnClickListener {
            // 新增店家，storeId 傳 -1
            val bundle = Bundle().apply { putInt("storeId", -1) }
            findNavController().navigate(R.id.action_storeListFragment_to_storeEditFragment, bundle)
        }

        view.findViewById<View>(R.id.btnFavorite).setOnClickListener {
            findNavController().navigate(R.id.action_storeListFragment_to_favoriteStoreFragment)
        }
    }
}
