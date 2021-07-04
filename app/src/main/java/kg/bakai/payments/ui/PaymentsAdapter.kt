package kg.bakai.payments.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kg.bakai.payments.R
import kg.bakai.payments.data.model.Payment

class PaymentsAdapter: RecyclerView.Adapter<PaymentsAdapter.ViewHolder>() {

    private var list = mutableListOf<Payment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentsAdapter.ViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.rv_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PaymentsAdapter.ViewHolder, position: Int) {
        val item = list[position]
        holder.apply {
            if (item.currency.isNullOrEmpty()) currency.text = "Unknown" else currency.text = item.currency
            amount.text = item.amount.toString()
            description.text = item.desc
            created.text = item.created.toString()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun submitItems(items: List<Payment>?) {
        list.clear()
        if (items != null) {
            list.addAll(items)
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val currency: TextView = view.findViewById(R.id.tv_currency)
        val amount: TextView = view.findViewById(R.id.tv_amount)
        val description: TextView = view.findViewById(R.id.tv_desc)
        val created: TextView = view.findViewById(R.id.tv_created)
    }
}