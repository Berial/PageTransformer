package xyz.berial.pagetransformer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        if (viewPager != null) {
            CustomFragmentPageAdapter adapter = new CustomFragmentPageAdapter(getSupportFragmentManager());
            viewPager.setAdapter(adapter);

            int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16,
                    getResources().getDisplayMetrics());
            viewPager.setPageMargin(pageMargin);

            viewPager.setPageTransformer(false, new CustomPageTransformer());
            viewPager.setOffscreenPageLimit(adapter.getCount());
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

//            if (position < -1 || position > 1) return;
//            int pageWidth = page.getWidth();
//            page.setTranslationX(position < 0 ? 0 : pageWidth * -position);

            if (position < -1 || position > 1) {
                page.setScaleX(MIN_SCALE);
                page.setScaleY(MIN_SCALE);
            } else {
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));

                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
            }
        }
    }
}
