package com.example.swipe_layout_slidinguppanel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var itemRy : RecyclerView
    private lateinit var itemList : ArrayList<String>
    private lateinit var itemAdapter: ItemRecyclerAdapter
    private lateinit var archiveRy : RecyclerView
    private lateinit var archieveList : ArrayList<String>
    private lateinit var archiveAdapter: ItemRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemRy = findViewById(R.id.item_rv)
        archiveRy = findViewById(R.id.archieve_rv)

        itemList = ArrayList()
        archieveList = ArrayList()

        for (i in 1..100){
            itemList.add("ITEM-${i}")
        }
        itemAdapter = ItemRecyclerAdapter(itemList,this)
        val itemLayoutManager = LinearLayoutManager(this)
        itemRy.layoutManager=itemLayoutManager
        itemRy.adapter = itemAdapter

        archiveAdapter = ItemRecyclerAdapter(archieveList,this)
        val  archieveLayoutManager = LinearLayoutManager(this)
        archiveRy.layoutManager = archieveLayoutManager
        archiveRy.adapter = archiveAdapter

        SwipeToGesture(itemRy)
    }


    private fun SwipeToGesture(itemRv:RecyclerView?){
        val swipeGesture = object :SwipeGesture(this){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                var actionBtnTapped = false

                try {
                    when(direction){
                        ItemTouchHelper.LEFT->{
                            val  deleteItem = itemList[position]
                            itemList.removeAt(position)
                            itemAdapter.notifyItemRemoved(position)

                            val snackbar = Snackbar.make(this@MainActivity.itemRy,"Item Deleted",Snackbar.LENGTH_LONG)
                                .addCallback(object :BaseTransientBottomBar.BaseCallback<Snackbar>(){
                                    override fun onDismissed(
                                        transientBottomBar: Snackbar?,
                                        event: Int
                                    ) {
                                        super.onDismissed(transientBottomBar, event)
                                    }

                                    override fun onShown(transientBottomBar: Snackbar?) {

                                        transientBottomBar?.setAction("UNDO"){
                                            itemList.add(position,deleteItem)
                                            itemAdapter.notifyItemInserted(position)
                                            actionBtnTapped = true
                                        }
                                        super.onShown(transientBottomBar)
                                    }
                                }).apply {
                                    animationMode = Snackbar.ANIMATION_MODE_FADE
                                }
                            snackbar.setActionTextColor(
                                ContextCompat.getColor(
                                    this@MainActivity,R.color.orangeRed
                                )
                            )
                            snackbar.show()
                        }
                        ItemTouchHelper.RIGHT->{
                            val  archieveItem = itemList[position]
                            itemList.removeAt(position)
                            itemAdapter.notifyItemRemoved(position)
                            archieveList.add(archieveItem)
                            archiveAdapter.notifyItemInserted(position)

                            val snackbar = Snackbar.make(this@MainActivity.itemRy,"Item Archived",Snackbar.LENGTH_LONG)
                                .addCallback(object :BaseTransientBottomBar.BaseCallback<Snackbar>(){
                                    override fun onDismissed(
                                        transientBottomBar: Snackbar?,
                                        event: Int
                                    ) {
                                        super.onDismissed(transientBottomBar, event)
                                    }

                                    override fun onShown(transientBottomBar: Snackbar?) {

                                        transientBottomBar?.setAction("UNDO"){
                                            itemList.add(position,archieveItem)
                                            itemAdapter.notifyItemInserted(position)
                                            archieveList.removeAt(archieveList.size -1)
                                            archiveAdapter.notifyItemRemoved(archieveList.size -1)
                                            actionBtnTapped = true
                                        }
                                        super.onShown(transientBottomBar)
                                    }
                                }).apply {
                                    animationMode = Snackbar.ANIMATION_MODE_FADE
                                }
                            snackbar.setActionTextColor(
                                ContextCompat.getColor(
                                    this@MainActivity,R.color.orangeRed
                                )
                            )
                            snackbar.show()
                        }


                    }
                }
                catch (e:java.lang.Exception){
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        val touchHelper = ItemTouchHelper(swipeGesture)

        touchHelper.attachToRecyclerView(itemRv)
    }

}