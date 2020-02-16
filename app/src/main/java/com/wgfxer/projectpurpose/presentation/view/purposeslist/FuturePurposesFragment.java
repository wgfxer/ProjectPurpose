package com.wgfxer.projectpurpose.presentation.view.purposeslist;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wgfxer.projectpurpose.R;
import com.wgfxer.projectpurpose.models.data.Purpose;
import com.wgfxer.projectpurpose.presentation.view.addpurpose.AddPurposeActivity;
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

public class FuturePurposesFragment extends Fragment {

    private TextView noItemsTextView;
    private TextView titleTextView;
    private RecyclerView purposesRecyclerView;
    private PurposesAdapter purposesAdapter;
    private FloatingActionButton createPurposeButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_future_purposes,container,false);
    }

    public static FuturePurposesFragment newInstance() {

        Bundle args = new Bundle();

        FuturePurposesFragment fragment = new FuturePurposesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initAdapter();
        observeViewModel();
    }

    private void initViews(View view) {
        noItemsTextView = view.findViewById(R.id.no_items);
        purposesRecyclerView = view.findViewById(R.id.purposes_list);
        titleTextView = view.findViewById(R.id.purpose_title_text_view);
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

    private void observeViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this, new MainViewModelFactory(getContext()))
                .get(MainViewModel.class);
        LiveData<List<Purpose>> purposesLiveData = viewModel.getFuturePurposes();

        if (purposesLiveData != null) {
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
}
