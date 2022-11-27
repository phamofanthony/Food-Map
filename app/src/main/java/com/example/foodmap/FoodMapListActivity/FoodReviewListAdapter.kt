package com.example.foodmap.FoodMapListActivity

import android.icu.text.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.foodmap.R
import com.example.foodmap.Repository.FoodReviewItem
import java.util.*


class FoodReviewListAdapter(
    val itemClicked: (itemId: Int) -> Unit,
    val checkBoxClicked: (itemId: Int, isChecked: Boolean) -> Unit
) : ListAdapter<FoodReviewItem, FoodReviewListAdapter.ToDoItemViewHolder>(ToDoItemComparator()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoItemViewHolder {
        return ToDoItemViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ToDoItemViewHolder, position: Int) {
        val current = getItem(position)
        current.id?.let {
            holder.bind(
                it, current.title, current.content,
                current.completed, current.dueDate, checkBoxClicked
            )
        }
        holder.itemView.tag = current.id
        holder.itemView.setOnClickListener {
            val itemId = it.tag
            val isClicked = holder.toDoItemCheckbox.isChecked
            Log.d("ToDoListAdapter", "Item Clicked: " + itemId)
            if (isClicked) {
                Log.d("ToDoListAdapter", "Item Checked")
            } else {
                Log.d("ToDoListAdapter", "Item not clicked")
            }
            itemClicked(it.tag as Int)
        }
    }

    class ToDoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val toDoItemTitleView: TextView = itemView.findViewById(R.id.tvTitle)
        private val toDoItemContentView: TextView = itemView.findViewById(R.id.tvContent)
        val toDoItemCheckbox: CheckBox = itemView.findViewById(R.id.cbCompleted)
        private val toDoItemDueView: TextView = itemView.findViewById(R.id.tvDueDate)


        fun bind(
            id: Int,
            title: String?,
            content: String?,
            completed: Int?,
            dueDate: Long?,
            checkBoxClicked: (itemId: Int, isChecked: Boolean) -> Unit
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
            if (completed == 0) {
                toDoItemCheckbox.isChecked = false
            } else {
                toDoItemCheckbox.isChecked = true
            }
            toDoItemCheckbox.isClickable = true
            toDoItemCheckbox.setOnClickListener {
                checkBoxClicked(id, toDoItemCheckbox.isChecked)
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
            return oldItem.id == newItem.id
        }
    }
}
