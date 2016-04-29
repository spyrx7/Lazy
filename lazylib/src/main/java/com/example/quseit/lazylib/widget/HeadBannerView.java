package com.example.quseit.lazylib.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.quseit.lazylib.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC_QUSEIT on 2016/4/29.
 */
public class HeadBannerView extends RelativeLayout {

    private Context context;
    private View view;
    private ViewPager viewpager;
    private List<ImageView> images = new ArrayList<>();
    private LinearLayout dianGroup;
    private List<ImageView> dians = new ArrayList<>();
    private List<String> urls = new ArrayList<>();
    private List<String> imgUrls = new ArrayList<>();
    private SellViewPagerAdapter adapter;
    private int currentItem = 0;
    int pageindex = 0;

    public HeadBannerView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public HeadBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public HeadBannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init(){
        view = View.inflate(context, R.layout.layout_banner_view,null);
        viewpager = (ViewPager) view.findViewById(R.id.viewpager);
        dianGroup = (LinearLayout) view.findViewById(R.id.diangroup);

        this.addView(view);
    }

    /**
     *  初始化 点
     * @param images
     */
    private void initImagerView(List<ImageView> images){
        if (images == null) return;
        if(dianGroup == null){
            LinearLayout lgroup = (LinearLayout)view.findViewById(R.id.diangroup);
        }

        dians = new ArrayList<>();
        for(int i = 0 ; i < images.size(); i++){
            ImageView imageView = new ImageView(context);
            if(i == 0){
                imageView.setImageResource(R.drawable.ic_dian_red);
            }else{
                imageView.setImageResource(R.drawable.ic_dian_white);
            }
            dians.add(imageView);
            dianGroup.addView(imageView);
        }
    }

    /**
     * 提供外部 入口
     */
    public void initView(List<BannerModel> entity){
        if(entity == null) return;
        int count = entity.size();

        for(int i = 0; i < count; i++){
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(R.drawable.find);
            images.add(imageView);
        }

        currentItem = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % images.size());
        initImagerView(images);
        adapter = new SellViewPagerAdapter(context, viewpager, images, urls);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % images.size()));
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentItem = position;
            }

            @Override
            public void onPageSelected(int position) {
                pageindex = position % images.size();
                for (int i = 0; i < dians.size(); i++) {
                    if (i == pageindex) {
                        dians.get(i).setImageResource(R.drawable.ic_dian_red);
                    } else {
                        dians.get(i).setImageResource(R.drawable.ic_dian_white);
                    }
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



    class  SellViewPagerAdapter extends PagerAdapter {
        private Context context;
        private List<ImageView> images;
        private List<String> urls;
        private ViewPager viewPager;
        private int currentItem = 0;


        public SellViewPagerAdapter(Context context, ViewPager viewPager, List<ImageView> images, List<String> urls) {
            this.context = context;
            this.images = images;
            this.urls = urls;
            this.viewPager = viewPager;
            // timer.schedule(task, 2000, 1000); // 1s后执行task,经过1s再次执行
        }

        public void setUrl(List<String> urls) {
            this.urls = urls;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(images.get(position % images.size()));
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            currentItem = position;
            ((ViewPager) container).addView(images.get(position % images.size()));
            // ((ImageView) images.get(position)).setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
            ((ImageView) images.get(position % images.size())).setScaleType(ImageView.ScaleType.CENTER_CROP);
            images.get(position % images.size()).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (urls.size() > 0) {
                        Toast.makeText(context, urls.get(position % images.size()), Toast.LENGTH_LONG).show();
                    }
                }
            });

            return images.get(position % images.size());
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
