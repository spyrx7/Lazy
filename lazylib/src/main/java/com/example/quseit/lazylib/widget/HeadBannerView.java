package com.example.quseit.lazylib.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.quseit.lazylib.R;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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

    private OnUrlOnClick listener;
    public interface OnUrlOnClick{
        void onUrlOnClick(String url);
    }

    public void setOnUrlOnClick(OnUrlOnClick callback){
        this.listener = callback;
    }



    public HeadBannerView(Context context) {
        super(context);
        this.context = context;

    }

    public HeadBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

    }

    public HeadBannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

    }

    private void init(){
        view = View.inflate(context, R.layout.layout_banner_view,null);
        viewpager = (ViewPager) view.findViewById(R.id.viewpager);
        dianGroup = (LinearLayout) view.findViewById(R.id.diangroup);

        this.addView(view);
    }

    Timer timer = new Timer();
    MyTimerTask timerTask;
    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            // 需要做的事:发送消息
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            viewpager.setCurrentItem(currentItem + 1, true);
        }
    };

    public void StartLockWindowTimer(){
        if (timer != null){
            if (timerTask != null){
                timerTask.cancel();  //将原任务从队列中移除
            }
            timerTask = new MyTimerTask();  // 新建一个任务
            timer.schedule(timerTask, 6000, 3000); // 3s后执行task,经过3s再次执行
        }
    }

    /**
     *  初始化 点
     * @param images
     */
    private void initImagerView(List<String> images){
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

        init();

        for(int i = 0; i < count; i++){
            ImageView imageView = new ImageView(context);
            urls.add(entity.get(i).getUrl());
            imgUrls.add(entity.get(i).getImgUrl());
            images.add(imageView);
        }


        currentItem = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % images.size());
        initImagerView(imgUrls);
        adapter = new SellViewPagerAdapter(context, viewpager, imgUrls, urls);
        adapter.setUrl(urls);
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
        //StartLockWindowTimer();
    }

    class  SellViewPagerAdapter extends PagerAdapter {
        private Context context;
        private List<String> imageurl;
        private List<String> urls;
        private ViewPager viewPager;
        private int currentItem = 0;


        public SellViewPagerAdapter(Context context, ViewPager viewPager, List<String> images, List<String> urls) {
            this.context = context;
            this.imageurl = images;
            this.urls = urls;
            this.viewPager = viewPager;
            Log.e("TAG",images.size()+"");
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
            ImageView view_img = (ImageView) LayoutInflater.from(context).inflate(R.layout.view_image, container, false);
            container.addView(view_img);
            view_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            x.image().bind(view_img, imageurl.get(position % images.size()));
            view_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (images.size() > 0) {
                        if (listener != null) {
                            listener.onUrlOnClick(urls.get(position % images.size()) + "");
                        }
                    }
                }
            });
            return view_img;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
