package com.wgfxer.projectpurpose.presentation.view.purposeslist;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wgfxer.projectpurpose.R;
import com.wgfxer.projectpurpose.helper.PreferencesHelper;
import com.wgfxer.projectpurpose.models.data.Purpose;
import com.wgfxer.projectpurpose.presentation.view.purposeinfo.PurposeInfoActivity;
import com.wgfxer.projectpurpose.presentation.viewmodel.MainViewModel;
import com.wgfxer.projectpurpose.presentation.viewmodel.MainViewModelFactory;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ArchivePurposesFragment extends Fragment {

    private TextView noItemsTextView;
    private RecyclerView completedPurposesRecyclerView;
    private PurposesAdapter completedPurposesAdapter;
    private RecyclerView expiredPurposesRecyclerView;
    private PurposesAdapter expiredPurposesAdapter;
    private TextView showExpiredPurposesTextView;
    private boolean isExpiredPurposesShow = false;
    private PreferencesHelper preferencesHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_archive_purposes,container,false);
    }

    public static ArchivePurposesFragment newInstance() {

        Bundle args = new Bundle();

        ArchivePurposesFragment fragment = new ArchivePurposesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initAdapters();
        observeViewModel();
        preferencesHelper = new PreferencesHelper(getActivity());
        isExpiredPurposesShow = preferencesHelper.getIsExpiredPurposeShow();
        setIsExpiredPurposesShow(isExpiredPurposesShow);
    }

    private void initViews(View view) {
        noItemsTextView = view.findViewById(R.id.no_items);
        completedPurposesRecyclerView = view.findViewById(R.id.completed_purposes_list);
        completedPurposesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        expiredPurposesRecyclerView = view.findViewById(R.id.expired_purposes_list);
        expiredPurposesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        showExpiredPurposesTextView = view.findViewById(R.id.show_expired_purposes_text_view);
        showExpiredPurposesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIsExpiredPurposesShow(!isExpiredPurposesShow);
            }
        });
    }


    private void initAdapters() {
        PurposesAdapter.OnPurposeClickListener onPurposeClickListener = new PurposesAdapter.OnPurposeClickListener() {
            @Override
            public void onPurposeClick(Purpose purpose) {
                Intent intent = PurposeInfoActivity.newIntent(getActivity(), purpose.getId());
                startActivity(intent);
            }
        };
        completedPurposesAdapter = new PurposesAdapter();
        completedPurposesAdapter.setOnPurposeClickListener(onPurposeClickListener);
        completedPurposesRecyclerView.setAdapter(completedPurposesAdapter);

        expiredPurposesAdapter = new PurposesAdapter();
        expiredPurposesAdapter.setOnPurposeClickListener(onPurposeClickListener);
        expiredPurposesRecyclerView.setAdapter(expiredPurposesAdapter);
    }

    private void setIsExpiredPurposesShow(boolean isShow){
        if(isShow){
            expiredPurposesRecyclerView.setVisibility(View.VISIBLE);
            showExpiredPurposesTextView.setText(R.string.hide_expired_purposes);
            showExpiredPurposesTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_hide, 0, 0, 0);
        }else{
            expiredPurposesRecyclerView.setVisibility(View.GONE);
            showExpiredPurposesTextView.setText(R.string.show_expired_purposes);
            showExpiredPurposesTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_show, 0, 0, 0);
        }
        isExpiredPurposesShow = isShow;
        preferencesHelper.putIsExpiredPurposeShow(isExpiredPurposesShow);
    }

    private void observeViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this, new MainViewModelFactory(getContext()))
                .get(MainViewModel.class);
        LiveData<List<Purpose>> completedPurposesLiveData = viewModel.getCompletedPurposes();

        if (completedPurposesLiveData != null) {
            completedPurposesLiveData.observe(this, new Observer<List<Purpose>>() {
                @Override
                public void onChanged(@Nullable List<Purpose> purposes) {
                    if (purposes != null && !purposes.isEmpty()) {
                        noItemsTextView.setVisibility(View.GONE);
                    } else {
                        noItemsTextView.setVisibility(View.VISIBLE);
                    }
                    completedPurposesAdapter.setPurposesList(purposes);
                }
            });
        }

        LiveData<List<Purpose>> expiredPurposesLiveData = viewModel.getExpiredPurposes();

        if (expiredPurposesLiveData != null) {
            expiredPurposesLiveData.observe(this, new Observer<List<Purpose>>() {
                @Override
                public void onChanged(@Nullable List<Purpose> purposes) {
                    if (purposes != null && !purposes.isEmpty()) {
                        showExpiredPurposesTextView.setVisibility(View.VISIBLE);
                    } else {
                        showExpiredPurposesTextView.setVisibility(View.INVISIBLE);
                    }
                    expiredPurposesAdapter.setPurposesList(purposes);
                }
            });
        }
    }
}
