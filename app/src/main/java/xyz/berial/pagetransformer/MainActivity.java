package xyz.berial.pagetransformer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        if (viewPager != null) {
            final CustomFragmentPageAdapter adapter = new CustomFragmentPageAdapter(getSupportFragmentManager());
            viewPager.setAdapter(adapter);

//            final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16,
//                    getResources().getDisplayMetrics());
//            viewPager.setPageMargin(pageMargin);

            viewPager.setPageTransformer(false, new CustomPageTransformer());
//            viewPager.setOffscreenPageLimit(adapter.getCount());
        }
    }

    static class CustomFragmentPageAdapter extends FragmentPagerAdapter {

        private final int[] mLayoutIds = {
                R.layout.page1,
                R.layout.page2,
                R.layout.page3
        };

        public CustomFragmentPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mLayoutIds.length;
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstance(mLayoutIds[position]);
        }
    }

    static class CustomPageTransformer implements ViewPager.PageTransformer {

        private static final String TAG = "CustomPageTransformer";

        private static final float MIN_SCALE = 0.9F;

        @Override
        public void transformPage(View page, float position) {
            Log.d(TAG, page + "\nposition -> " + position);

            // part1
//            if (position < -1 || position > 1) return;
//            final int pageWidth = page.getWidth();
//            page.setTranslationX(position < 0 ? 0 : pageWidth * -position);

            // part2
//            if (position < -1 || position > 1) {
//                page.setScaleX(MIN_SCALE);
//                page.setScaleY(MIN_SCALE);
//            } else {
//                final float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
//
//                page.setScaleX(scaleFactor);
//                page.setScaleY(scaleFactor);
//            }

            if (position < -1 || position > 1) return;

            final Random random = new Random();

            // v4 包中的 fragment 会在最外层套上一层 FrameLayout, 所以如果用这个,
            // child 就是 page.xml 里的外层 Layout
            // 所以, 为了减少布局嵌套, 并且不准备兼容低版本, 可以考虑重新写一个 FragmentPagerAdapter,
            // 把 v4 包下的 Fragment 相关类 改成 android.app 包下的

            // final ViewGroup group = (ViewGroup) page;

            final ViewGroup group = (ViewGroup) page.findViewById(R.id.root_layout);

            final int childCount = group.getChildCount();

            for (int i = 0; i < childCount; i++) {
                View view = group.getChildAt(i);

                float factor;
                if (view.getTag() != null) {
                    factor = (float) view.getTag();
                } else {
                    factor = random.nextFloat();
                    view.setTag(factor);
                }
                view.setTranslationX(Math.abs(view.getWidth() * position * factor));
            }
        }
    }
}
