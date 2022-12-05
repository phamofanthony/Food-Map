package com.example.foodmap.FoodMapListActivity

import android.icu.text.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.foodmap.R
import com.example.foodmap.Repository.FoodReviewItem
import java.util.*


class FoodReviewListAdapter(

    val itemClicked: (itemId: Int) -> Unit,
) : ListAdapter<FoodReviewItem, FoodReviewListAdapter.ToDoItemViewHolder>(ToDoItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoItemViewHolder {
        return ToDoItemViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ToDoItemViewHolder, position: Int) {
        val current = getItem(position)
        current.postID?.let {
            holder.bind(
                current.restName, current.restReview, null
            )
        }
        holder.itemView.tag = current.postID
        holder.itemView.setOnClickListener {
            val itemId = it.tag
            Log.d("ToDoListAdapter", "Item Clicked: " + itemId)
            itemClicked(it.tag as Int)
        }
    }

    class ToDoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val toDoItemTitleView: TextView = itemView.findViewById(R.id.tvTitle)
        private val toDoItemContentView: TextView = itemView.findViewById(R.id.tvContent)
        private val toDoItemDueView: TextView = itemView.findViewById(R.id.tvDueDate)


        fun bind(
            title: String?,
            content: String?,
            dueDate: Long?,
        ) {
            toDoItemTitleView.text = title
            toDoItemContentView.text = content
            if (dueDate != null) {
                val cal: Calendar = Calendar.getInstance()
                cal.timeInMillis = dueDate
                if (cal.timeInMillis > System.currentTimeMillis()) {
                    toDoItemDueView.setText(
                        java.text.DateFormat.getDateTimeInstance(
                            DateFormat.DEFAULT,
                            DateFormat.SHORT
                        ).format(cal.timeInMillis)
                    )
                } else {
                    toDoItemDueView.text = ""
                }
            }
        }

        companion object {
            fun create(parent: ViewGroup): ToDoItemViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return ToDoItemViewHolder(view)
            }
        }
    }

    class ToDoItemComparator : DiffUtil.ItemCallback<FoodReviewItem>() {
        override fun areItemsTheSame(oldItem: FoodReviewItem, newItem: FoodReviewItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: FoodReviewItem, newItem: FoodReviewItem): Boolean {
            return oldItem.postID == newItem.postID
        }
    }

}