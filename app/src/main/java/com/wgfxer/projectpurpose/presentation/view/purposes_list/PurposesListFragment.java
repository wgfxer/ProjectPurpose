package com.wgfxer.projectpurpose.presentation.view.purposes_list;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wgfxer.projectpurpose.R;
import com.wgfxer.projectpurpose.data.Purpose;
import com.wgfxer.projectpurpose.presentation.view.add_purpose.AddPurposeActivity;
import com.wgfxer.projectpurpose.presentation.view.purpose_info.PurposeInfoActivity;
import com.wgfxer.projectpurpose.presentation.viewmodel.MainViewModel;
import com.wgfxer.projectpurpose.presentation.viewmodel.MainViewModelFactory;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class PurposesListFragment extends Fragment {

    static final int MODE_FUTURE_PURPOSES = 1;
    static final int MODE_DONE_PURPOSES = 2;
    private static final String KEY_MODE = "KEY_MODE";
    private TextView noItemsTextView;
    private RecyclerView purposesRecyclerView;
    private PurposesAdapter purposesAdapter;
    private FloatingActionButton createPurposeButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_purposes_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initAdapter();
        observeViewModel(getArguments().getInt(KEY_MODE));
    }

    private void initViews(View view) {
        noItemsTextView = view.findViewById(R.id.no_items);
        purposesRecyclerView = view.findViewById(R.id.purposes_list);
        purposesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        createPurposeButton = view.findViewById(R.id.add_purpose);
        createPurposeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AddPurposeActivity.newPurpose(getActivity());
                startActivity(intent);
            }
        });
    }

    private void initAdapter() {
        purposesAdapter = new PurposesAdapter();
        purposesAdapter.setOnPurposeClickListener(new PurposesAdapter.OnPurposeClickListener() {
            @Override
            public void onPurposeClick(Purpose purpose) {
                Intent intent = PurposeInfoActivity.newIntent(getActivity(), purpose.getId());
                startActivity(intent);
            }
        });
        purposesRecyclerView.setAdapter(purposesAdapter);
    }

    private void observeViewModel(int mode) {
        MainViewModel viewModel = ViewModelProviders.of(this, new MainViewModelFactory(getContext()))
                .get(MainViewModel.class);
        LiveData<List<Purpose>> purposesLiveData = null;
        if(mode == MODE_FUTURE_PURPOSES){
            purposesLiveData = viewModel.getPurposes();
        }else if(mode == MODE_DONE_PURPOSES){
            purposesLiveData = viewModel.getDonePurposes();
            noItemsTextView.setText(R.string.no_done_purposes_text);
            createPurposeButton.setVisibility(View.INVISIBLE);
        }
        if(purposesLiveData != null){
            purposesLiveData.observe(this, new Observer<List<Purpose>>() {
                @Override
                public void onChanged(@Nullable List<Purpose> purposes) {
                    if (purposes != null && !purposes.isEmpty()) {
                        noItemsTextView.setVisibility(View.GONE);
                    } else {
                        noItemsTextView.setVisibility(View.VISIBLE);
                    }
                    purposesAdapter.setPurposesList(purposes);
                }
            });
        }
    }

    public static PurposesListFragment newInstance(int mode) {

        Bundle args = new Bundle();
        args.putInt(KEY_MODE, mode);
        PurposesListFragment fragment = new PurposesListFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
