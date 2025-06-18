package ntub.imd.afinal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar

class StoreEditFragment : Fragment() {
    private val storeViewModel: StoreViewModel by activityViewModels()
    private var storeId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_store_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storeId = arguments?.getInt("storeId", -1) ?: -1
        val etName = view.findViewById<EditText>(R.id.etName)
        val etPhone = view.findViewById<EditText>(R.id.etPhone)
        val etAddress = view.findViewById<EditText>(R.id.etAddress)
        val ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)
        val btnSave = view.findViewById<Button>(R.id.btnSave)
        val btnDelete = view.findViewById<Button>(R.id.btnDelete)
        btnDelete.visibility = if (storeId == -1) View.GONE else View.VISIBLE

        // 若是編輯，填入原資料
        if (storeId != -1) {
            val store = storeViewModel.getStoreById(storeId)
            store?.let {
                etName.setText(it.name)
                etPhone.setText(it.phone)
                etAddress.setText(it.address)
                ratingBar.rating = it.rating
            }
        }

        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val address = etAddress.text.toString().trim()
            val rating = ratingBar.rating

            if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Snackbar.make(view, "請填寫所有欄位", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (storeId == -1) {
                // 新增
                val newStore = Store(
                    name = name,
                    phone = phone,
                    address = address,
                    rating = rating
                )
                storeViewModel.addStore(newStore)
                Snackbar.make(view, "新增成功", Snackbar.LENGTH_SHORT).show()
            } else {
                // 更新
                val updateStore = Store(
                    id = storeId,
                    name = name,
                    phone = phone,
                    address = address,
                    rating = rating
                )
                storeViewModel.updateStore(updateStore)
                Snackbar.make(view, "更新成功", Snackbar.LENGTH_SHORT).show()
            }
            // 返回上一頁
            findNavController().popBackStack()
        }

        btnDelete.setOnClickListener {
            if (storeId != -1) {
                storeViewModel.deleteStore(storeId)
                Snackbar.make(view, "刪除成功", Snackbar.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
    }
}
