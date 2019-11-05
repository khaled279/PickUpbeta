package com.ksabri.pickupbeta.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.ksabri.pickupbeta.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    public  int getIndex(){
        return getArguments().getInt(ARG_SECTION_NUMBER) ;
    }
    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        final TextView textView = root.findViewById(R.id.appCompatTextView);
        final  TextView textView1 = root.findViewById(R.id.textView);
        final ImageView img = root.findViewById(R.id.image_fragment);


        final int index = getArguments().getInt(ARG_SECTION_NUMBER);
          pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                switch (index) {
                    case 1 : textView.setText(R.string.on_board);
                             textView1.setText(R.string.on_board_grey);
                             img.setImageResource(R.drawable.on_boarding);
                             textView.setTextColor(getResources().getColor(R.color.sea_foam_blue));
                            ;
                             break;
                    case 2 :  textView.setText(R.string.on_board_2);
                        textView1.setText(R.string.on_board_2grey);
                        img.setImageResource(R.drawable.onboarding_2);
                        textView.setTextColor(getResources().getColor(R.color.sea_foam_blue));

                }
            }});
        return root;
    }
}