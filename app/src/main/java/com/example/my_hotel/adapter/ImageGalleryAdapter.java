package com.example.my_hotel.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.my_hotel.adapter.classes.Photo;
import com.example.my_hotel.activity.PhotoActivity;
import com.example.my_hotel.R;

public class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.MyViewHolder> {

    private final Photo[] mPhotos;
    private final Context mContext;

    public ImageGalleryAdapter(Context context, Photo[] Photos) {
        mContext = context;
        mPhotos = Photos;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View photoView = inflater.inflate(R.layout.custom_item_layout, parent, false);
        return new MyViewHolder(photoView);
    }

    @Override
    public void onBindViewHolder(ImageGalleryAdapter.MyViewHolder holder, int position) {
        Photo photo = mPhotos[position];

        Glide.with(mContext)
                .load(photo.getUrl())
                .placeholder(R.drawable.ic_cloud_off_red)
                .into(holder.mPhotoImageView);

        holder.mPhotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Photo Photo = mPhotos[position];
                Intent intent = new Intent(mContext, PhotoActivity.class);
                intent.putExtra(PhotoActivity.EXTRA_PHOTO, Photo);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mPhotos.length);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView mPhotoImageView;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            mPhotoImageView = (ImageView) itemView.findViewById(R.id.iv_photo);
//            itemView.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View view) {
//
//            int position = getAdapterPosition();
//            if(position != RecyclerView.NO_POSITION) {
//                Photo Photo = mPhotos[position];
//                Intent intent = new Intent(mContext, Photo.class);
//                intent.putExtra(PhotoActivity.EXTRA_PHOTO, Photo);
//                startActivity(intent);
//            }
//        }
    }


}