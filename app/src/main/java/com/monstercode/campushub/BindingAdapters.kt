package com.monstercode.campushub

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.monstercode.campushub.domain.Item
import com.monstercode.campushub.domain.Order
import com.monstercode.campushub.domain.Picture
import com.monstercode.campushub.item.PictureListAdapter
import com.monstercode.campushub.itemlist.ItemListAdapter
import com.monstercode.campushub.orderlist.OrderListAdapter
import com.monstercode.campushub.util.setClickableAnimation
import de.hdodenhof.circleimageview.CircleImageView


@BindingAdapter("imageUrl")
fun ImageView.bindImage(imgUrl: String?) {
    imgUrl?.let {
//        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(context)
            .load(imgUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(this)
    }
}

@BindingAdapter("imageUrl")
fun CircleImageView.bindImage(imgUrl: String?) {
    imgUrl?.let {
//        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        val imgUri = imgUrl.toUri().buildUpon().build()
        Glide.with(context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(this)
    }
}

@BindingAdapter("orderList")
fun RecyclerView.bindOrderList(orderList: List<Order>?) {
    (adapter as OrderListAdapter).submitList(orderList)
}

@BindingAdapter("itemList")
fun RecyclerView.bindItemList(itemList: List<Item>?) {
    (adapter as ItemListAdapter).submitList(itemList)
}

@BindingAdapter("pictureList")
fun RecyclerView.bindPictureList(pictureList: List<Picture>?) {
    (adapter as PictureListAdapter).submitList(pictureList)
}

@BindingAdapter("addClickAnimation")
fun View.addClickAnimation(shouldAdd: Boolean?) {
    setClickableAnimation(context = context, view = this)
}

@BindingAdapter("horizontalLayout")
fun RecyclerView.horizontalLayout(asHorizontal: Boolean?) {
    asHorizontal?.let {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }
}

@BindingAdapter("addDivider")
fun RecyclerView.addDivider(shouldAdd: Boolean?) {
    shouldAdd?.let {
        if (it) {
            val itemDec = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            itemDec.setDrawable(ContextCompat.getDrawable(context, R.drawable.divider)!!)
            addItemDecoration(itemDec)
        }
    }
}

