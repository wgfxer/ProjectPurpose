package com.wgfxer.projectpurpose.presentation.view.purposeinfo.notesfragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wgfxer.projectpurpose.R;
import com.wgfxer.projectpurpose.models.data.Purpose;
import com.wgfxer.projectpurpose.presentation.viewmodel.MainViewModel;
import com.wgfxer.projectpurpose.presentation.viewmodel.MainViewModelFactory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

public class NotesListFragment extends Fragment {

    private static final String KEY_CURRENT_PAGE = "KEY_CURRENT_PAGE";
    private static final String KEY_PURPOSE_ID = "KEY_PURPOSE_ID";
    private ViewPager notesViewPager;
    private NotesAdapter notesAdapter;

    private Purpose purpose;
    private MainViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        return inflater.inflate(R.layout.notes_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        notesViewPager = view.findViewById(R.id.notes_view_pager);
        float dip = 10f;
        Resources r = getResources();
        float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dip,
                r.getDisplayMetrics()
        );
        notesViewPager.setPageMargin((int) px);
        notesAdapter = new NotesAdapter(getChildFragmentManager());
        notesViewPager.setAdapter(notesAdapter);
        viewModel = ViewModelProviders.of(this, new MainViewModelFactory(getContext()))
                .get(MainViewModel.class);
        viewModel.getPurposeById(getArguments().getInt(KEY_PURPOSE_ID)).observe(this, new Observer<Purpose>() {
            @Override
            public void onChanged(Purpose purpose) {
                if (purpose != null) {
                    NotesListFragment.this.purpose = purpose;
                    notesAdapter.setNotesList(purpose.getNotesList());
                }
            }
        });
        if (savedInstanceState != null) {
            notesViewPager.postDelayed(new Runnable() {

                @Override
                public void run() {
                    notesViewPager.setCurrentItem(savedInstanceState.getInt(KEY_CURRENT_PAGE));
                }
            }, 1);
            //notesViewPager.setCurrentItem(savedInstanceState.getInt(KEY_CURRENT_PAGE)); почему-то не работает

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        purpose.setNotesList(notesAdapter.getNotesList());
        viewModel.updatePurpose(purpose);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_PAGE, notesViewPager.getCurrentItem());
    }

    public static NotesListFragment newInstance(int purposeId) {

        Bundle args = new Bundle();
        args.putInt(KEY_PURPOSE_ID, purposeId);
        NotesListFragment fragment = new NotesListFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
