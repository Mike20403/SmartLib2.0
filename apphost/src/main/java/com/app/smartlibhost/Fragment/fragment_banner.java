package com.app.smartlibhost.Fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.smartlibhost.R;
import com.squareup.picasso.Picasso;

import ss.com.bannerslider.ImageLoadingService;
import ss.com.bannerslider.Slider;
import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class fragment_banner extends Fragment {
    View view;
    Slider slider;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_banner, container, false);
        slider = view.findViewById(R.id.banner_slider1);
        slider.setAdapter(new MainSliderAdapter());
        PicassoImageLoadingService picassoImageLoadingService = new PicassoImageLoadingService(getContext());
        Slider.init(picassoImageLoadingService);


        return view;

    }




    public class MainSliderAdapter extends SliderAdapter {

        @Override
        public int getItemCount() {
            return 7;
        }

        @Override
        public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {
            switch (position) {
                case 0:
                    viewHolder.bindImageSlide("https://www.upsieutoc.com/images/2019/06/27/alexferguson.jpg");
                    break;
                case 1:
                    viewHolder.bindImageSlide("https://ioffice.tatthanh.com.vn/pic/news/images/thiet-ke-bia-sach%20(5).jpg");
                    break;
                case 2:
                    viewHolder.bindImageSlide("https://www.upsieutoc.com/images/2019/06/27/dac-nhan-tam.jpg");
                    break;
                case 3:
                    viewHolder.bindImageSlide(("https://ioffice.tatthanh.com.vn/pic/news/images/thiet-ke-bia-sach%20(6).jpg"));
                    break;
                case 4:
                    viewHolder.bindImageSlide("https://ioffice.tatthanh.com.vn/pic/news/images/thiet-ke-bia-sach%20(7).jpg");
                    break;
                case 5:
                    viewHolder.bindImageSlide("https://ioffice.tatthanh.com.vn/pic/news/images/thiet-ke-bia-sach%20(4).jpg");
                    break;
                case 6:
                    viewHolder.bindImageSlide("https://ioffice.tatthanh.com.vn/pic/news/images/thiet-ke-bia-sach%20(8).jpg");
                    break;



            }
        }
    }
    public class PicassoImageLoadingService implements ImageLoadingService {
        public Context context;

        public PicassoImageLoadingService(Context context) {
            this.context = context;
        }

        @Override
        public void loadImage(String url, ImageView imageView) {
            Picasso.get().load(url).into(imageView);
        }

        @Override
        public void loadImage(int resource, ImageView imageView) {
            Picasso.get().load(resource).into(imageView);
        }

        @Override
        public void loadImage(String url, int placeHolder, int errorDrawable, ImageView imageView) {
            Picasso.get().load(url).placeholder(placeHolder).error(errorDrawable).into(imageView);
        }
    }
}