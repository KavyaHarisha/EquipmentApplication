package com.kavya.equipmentapplication.equipment

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import androidx.databinding.DataBindingUtil
import com.kavya.equipmentapplication.R
import com.kavya.equipmentapplication.databinding.HeaderListItemBinding
import com.kavya.equipmentapplication.databinding.ListItemBinding
import java.util.*

class CustomExpandableListAdapter internal constructor(
        private val titleList: List<String>,
        private val dataList: HashMap<String, List<String>>
) : BaseExpandableListAdapter() {
    override fun getChild(listPosition: Int, expandedListPosition: Int): Any {
        return this.dataList[this.titleList[listPosition]]!![expandedListPosition]
    }

    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }

    override fun getChildView(
            listPosition: Int,
            expandedListPosition: Int,
            isLastChild: Boolean,
            convertView: View?,
            parent: ViewGroup
    ): View {
        val listItemBinding: ListItemBinding
        val expandedListText = getChild(listPosition, expandedListPosition) as String
        val layoutInflater1 = LayoutInflater.from(parent.context)
        listItemBinding = DataBindingUtil.inflate(
                layoutInflater1,
                R.layout.list_item,
                parent,
                false)
        listItemBinding.listView.text = expandedListText
        return listItemBinding.root
    }

    override fun getChildrenCount(listPosition: Int): Int {
        return this.dataList[this.titleList[listPosition]]!!.size
    }

    override fun getGroup(listPosition: Int): Any {
        return this.titleList[listPosition]
    }

    override fun getGroupCount(): Int {
        return this.titleList.size
    }

    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }

    override fun getGroupView(
            listPosition: Int,
            isExpanded: Boolean,
            convertView: View?,
            parent: ViewGroup
    ): View {
        val headerListItemBinding:HeaderListItemBinding
        val listTitle = getGroup(listPosition) as String
            val layoutInflater1 = LayoutInflater.from(parent.context)
            headerListItemBinding = DataBindingUtil.inflate(
                    layoutInflater1,
                    R.layout.header_list_item,
                    parent,
                    false)
        headerListItemBinding.listView.setTypeface(null, Typeface.BOLD)
        headerListItemBinding.listView.text = listTitle
        return headerListItemBinding.root
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return true
    }
}