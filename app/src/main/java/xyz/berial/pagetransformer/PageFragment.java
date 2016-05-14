package xyz.berial.pagetransformer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * ViewPager的Page
 * Created by Berial on 16/5/13.
 */
public class PageFragment extends Fragment {

    private static final String TAG = "PageFragment";
    private static final String LAYOUT_ID = "layoutId";

    private int layoutId;

    public static PageFragment newInstance(int layoutId) {
        PageFragment fragment = new PageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(LAYOUT_ID, layoutId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null)
            layoutId = arguments.getInt(LAYOUT_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutId, container, false);
        Log.d(TAG, "layoutId: " + layoutId + "\nview: " + view);
        return view;
    }
}
