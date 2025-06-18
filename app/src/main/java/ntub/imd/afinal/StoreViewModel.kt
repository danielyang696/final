package ntub.imd.afinal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StoreViewModel : ViewModel() {
    private val _stores = MutableLiveData<MutableList<Store>>(mutableListOf())
    val stores: LiveData<MutableList<Store>> = _stores

    private var nextId = 1

    fun addStore(store: Store) {
        store.id = nextId++
        _stores.value?.add(store)
        _stores.value = _stores.value
    }

    fun updateStore(store: Store) {
        val list = _stores.value ?: return
        val idx = list.indexOfFirst { it.id == store.id }
        if (idx != -1) {
            list[idx] = store
            _stores.value = list
        }
    }

    fun updateStoreList(newList: List<Store>) {
        _stores.value = newList.toMutableList()
    }

    fun getStoreById(id: Int): Store? {
        return _stores.value?.find { it.id == id }
    }

    fun toggleFavorite(id: Int) {
        val list = _stores.value ?: return
        val idx = list.indexOfFirst { it.id == id }
        if (idx != -1) {
            list[idx].isFavorite = !list[idx].isFavorite
            _stores.value = list
        }
    }

    fun deleteStore(id: Int) {
        val list = _stores.value ?: return
        val idx = list.indexOfFirst { it.id == id }
        if (idx != -1) {
            list.removeAt(idx)
            _stores.value = list
        }
    }
}
