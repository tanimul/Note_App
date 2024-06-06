package org.tanimul.notes.base

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import java.util.Collections

@SuppressLint("NotifyDataSetChanged")
abstract class BaseRecyclerAdapterView<Binder : ViewBinding, T> : RecyclerView.Adapter<BaseRecyclerAdapterView<Binder, T>.ViewHolder>() {

    protected var data: ArrayList<T> = ArrayList()

    lateinit var mContext: Context

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        mContext = holder.itemView.context
        super.onViewAttachedToWindow(holder)
    }

    inner class ViewHolder(val binding: Binder) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = data.size

    fun setValue(input: ArrayList<T>) {
        this.data.clear();
        this.data.addAll(input)
        notifyDataSetChanged()
    }

    fun setValue(input: List<T>?) {
        input?.let {
            this.data.clear()
            this.data.addAll(it)
            notifyDataSetChanged()
        }

    }

    fun setValueWithoutClear(input: List<T>) {
        this.data.addAll(input)
        notifyDataSetChanged()
    }

    open operator fun get(position: Int): T {
        return data[position]
    }

    open fun getAll(): java.util.ArrayList<T>? {
        return data
    }

    open fun clear(notify: Boolean) {
        this.data.clear()
        if (notify) notifyDataSetChanged()
    }

    open fun add(notify: Boolean, vararg elements: T) {
        Collections.addAll(this.data, *elements)
        if (notify) notifyDataSetChanged()
    }

    open fun add(ele: T, notify: Boolean) {
        this.data.add(ele)
        if (notify) notifyDataSetChanged()
    }

    open fun add(idx: Int, ele: T, notify: Boolean) {
        this.data.add(idx, ele)
        if (notify) notifyItemChanged(idx)
    }

    open fun addAll(ele: List<T>, notify: Boolean) {
        this.data.addAll(ele)
        if (notify) notifyDataSetChanged()
    }

    open operator fun set(idx: Int, ele: T, notify: Boolean) {
        this.data[idx] = ele
        if (notify) notifyItemChanged(idx)
    }

    open fun remove(ele: T, notify: Boolean) {
        this.data.remove(ele)
        if (notify) notifyDataSetChanged()
    }

    open fun remove(idx: Int, notify: Boolean) {
        this.data.removeAt(idx)
        if (notify) notifyItemChanged(idx)
    }

    open fun merge(c: Collection<T>, notify: Boolean) {
        this.data.addAll(c)
        if (notify) notifyDataSetChanged()
    }

    open fun replace(c: Collection<T>, notify: Boolean) {
        this.data.clear()
        this.data.addAll(c)
        if (notify) notifyDataSetChanged()
    }

    fun getSize(): Int {
        return data.size
    }
}
