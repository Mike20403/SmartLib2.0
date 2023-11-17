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
                    viewHolder.bindImageSlide("https://static.ybox.vn/2020/10/6/1602346585293-121143864_653048828683117_753888090658765399_n.png");
                    break;
                case 1:
                    viewHolder.bindImageSlide("https://nguonluc.com.vn/uploads/images/2023/04/20/sachhay-dac-nhan-tam-1681986383.jpg");
                    break;
                case 2:
                    viewHolder.bindImageSlide("https://cdn0.fahasa.com/media/catalog/product/o/n/ong_gia_va_bien_ca_tai_ban_2022_1_2022_08_01_10_57_46.png");
                    break;
                case 3:
                    viewHolder.bindImageSlide(("https://cdn-images.kiotviet.vn/nhungvisao/e872925e3ceb4f60b4ef32a72f2f1266.jpg"));
                    break;
                case 4:
                    viewHolder.bindImageSlide("https://conhocgioi.com/wp-content/uploads/2019/06/1488509319914_4461293-900x550.jpg");
                    break;
                case 5:
                    viewHolder.bindImageSlide("https://hgth.1cdn.vn/thumbs/1000x0/2023/03/06/hat-giong-tam-hon-gio-qua-1.jpg");
                    break;
                case 6:
                    viewHolder.bindImageSlide("https://bizweb.dktcdn.net/100/180/408/files/tam-ly-hoc-toi-pham-02.jpg?v=1613983850317");
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