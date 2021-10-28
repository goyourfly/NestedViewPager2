package com.abc.nestedscrolltest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val pager2 = findViewById<ViewPager2>(R.id.pager)
        pager2.adapter = MyAdapter()
    }

    class MyAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view =LayoutInflater.from(parent.context).inflate(R.layout.item_scrollview,parent,false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        }

        override fun getItemCount(): Int {
            return 10
        }

        class MyViewHolder(val view: View):RecyclerView.ViewHolder(view){

            init {
//                val subRecyclerView = view.findViewById<RecyclerView>(R.id.recycler)
//                subRecyclerView.layoutManager = LinearLayoutManager(view.context,LinearLayoutManager.VERTICAL,false)
//                subRecyclerView.setHasFixedSize(true)
//                subRecyclerView.adapter = MySubAdapter()
//                subRecyclerView.isNestedScrollingEnabled = false
//                ViewCompat.setNestedScrollingEnabled(subRecyclerView,true)
            }

            class MySubAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>(){
                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): RecyclerView.ViewHolder {
                    val view =LayoutInflater.from(parent.context).inflate(R.layout.item_sub,parent,false)
                 return MySubViewHolder(view)
                }

                override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

                }

                override fun getItemCount(): Int {
                    return 3
                }

                class MySubViewHolder(val view:View):RecyclerView.ViewHolder(view){

                }
            }
        }
    }
}