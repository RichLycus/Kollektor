package com.lycuscoder.kollektor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class FormAdapter(
    private val formList: List<FormEntry>,
    private val onDeleteClick: (FormEntry) -> Unit
) : RecyclerView.Adapter<FormAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.cardView)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvEmail: TextView = itemView.findViewById(R.id.tvEmail)
        val tvPhone: TextView = itemView.findViewById(R.id.tvPhone)
        val tvAddress: TextView = itemView.findViewById(R.id.tvAddress)
        val tvMessage: TextView = itemView.findViewById(R.id.tvMessage)
        val tvCreatedAt: TextView = itemView.findViewById(R.id.tvCreatedAt)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_form_entry, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val form = formList[position]
        
        holder.tvName.text = form.name
        holder.tvEmail.text = form.email
        holder.tvPhone.text = formatPhoneNumber(form.phone)
        holder.tvAddress.text = form.address
        
        if (form.message.isNotEmpty()) {
            holder.tvMessage.text = form.message
            holder.tvMessage.visibility = View.VISIBLE
        } else {
            holder.tvMessage.visibility = View.GONE
        }
        
        holder.tvCreatedAt.text = formatDate(form.createdAt)
        holder.btnDelete.setOnClickListener { onDeleteClick(form) }
        
        animateCard(holder.cardView, position)
        
        // Optional: Add click listener for item
        holder.cardView.setOnClickListener {
            // Handle item click if needed
        }
    }

    override fun getItemCount(): Int = formList.size

    private fun formatPhoneNumber(phone: String): String {
        return when {
            phone.startsWith("08") -> "+62 ${phone.substring(1)}"
            phone.startsWith("62") -> "+$phone"
            else -> phone
        }
    }

    private fun formatDate(dateString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.forLanguageTag("id-ID"))
            val date = inputFormat.parse(dateString)
            outputFormat.format(date ?: Date())
        } catch (e: Exception) {
            dateString
        }
    }

    private fun animateCard(cardView: CardView, position: Int) {
        cardView.alpha = 0f
        cardView.scaleX = 0.9f
        cardView.scaleY = 0.9f
        cardView.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(300)
            .setStartDelay((position * 50).toLong())
            .start()
    }
}