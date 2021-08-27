package com.orangemarket.orangemarket.ui.chatdetail


import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import android.view.Gravity
import com.orangemarket.orangemarket.databinding.ItemChatBinding


private var firebaseAuth: FirebaseAuth? = null

class ChatItemAdapter : ListAdapter<ChatItem, ChatItemAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: ItemChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat", "ResourceAsColor")
        fun bind(chatItem: ChatItem) {
            firebaseAuth = FirebaseAuth.getInstance()
            val uid =firebaseAuth!!.currentUser!!.uid
            if(chatItem.senderId==uid) {
                binding.senderTextView.gravity = Gravity.END
                binding.messageTextView.gravity = Gravity.END
                binding.chatCardView.setCardBackgroundColor((Color.parseColor("#fef01b")))

            }
            else{
                binding.senderTextView.gravity = Gravity.START
                binding.messageTextView.gravity = Gravity.START
                binding.chatCardView.setCardBackgroundColor((Color.parseColor("#FFFFFF")))
            }
            binding.senderTextView.text = chatItem.senderNickname
            binding.messageTextView.text = chatItem.message
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemChatBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ChatItem>() {
            override fun areItemsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
                // 현재 노출하고 있는 아이템과 새로운 아이템이 같은지 비교;
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
                // equals 비교;
                return oldItem == newItem

            }

        }
    }
}