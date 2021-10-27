package com.chao.search.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.chao.search.R;
import com.chao.search.ui.fragment.MainFragment;
import com.chao.search.ui.fragment.MainViewModel;
import com.chao.search.util.DataState;
import com.chao.search.util.DataStateListener;
import dagger.hilt.android.AndroidEntryPoint;

/**
 * This class is the Java class for MainActivity for math 2 java file requirement
 * It is not hook up by any files yet but It is totally equal to MainActivity.kt
 * **/
@AndroidEntryPoint
public class JavaMainActivity extends AppCompatActivity  implements DataStateListener {
    private MainViewModel viewModel = null;
    private ProgressBar progressBar = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        progressBar = findViewById(R.id.progress_bar);

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, MainFragment.Companion.newInstance());
        }
    }

    @Override
    public void onDataStateChange(@Nullable DataState<?> dataState) {
        handleDataState(dataState);
    }

    private void handleDataState(DataState dataState) {
        if (dataState != null) {
            //handle loading on ui
            showProgressBar(dataState.getLoading());

            //handle message on ui
            if ( dataState.getMessage() != null) {
                if (dataState.getMessage().getContentIfNotHandled() != null) {
                    showToast((String) dataState.getMessage().getContentIfNotHandled());
                }
            }
        }
    }

    private void showProgressBar(Boolean isVisible) {
        if (progressBar != null) {
            if(isVisible) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
