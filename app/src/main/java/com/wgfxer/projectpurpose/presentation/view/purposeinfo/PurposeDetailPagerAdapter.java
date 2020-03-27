package com.wgfxer.projectpurpose.presentation.view.purposeinfo;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.wgfxer.projectpurpose.R;
import com.wgfxer.projectpurpose.presentation.view.purposeinfo.notesfragment.NotesFragment;
import com.wgfxer.projectpurpose.presentation.view.purposeinfo.reportsfragment.ReportsFragment;
import com.wgfxer.projectpurpose.presentation.view.purposeinfo.tasksfragment.TasksListFragment;
import java.util.ArrayList;
import java.util.List;

public class PurposeDetailPagerAdapter extends FragmentPagerAdapter {
    private List<String> pages = new ArrayList<>();
    private int purposeId;

    public PurposeDetailPagerAdapter(FragmentManager fm, int purposeId, Context context) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        pages.add(context.getResources().getString(R.string.tasks_title));
        pages.add(context.getResources().getString(R.string.notes_title));
        pages.add(context.getResources().getString(R.string.reports_title));
        this.purposeId = purposeId;
    }

    public Fragment getItem(int i) {
        switch(i){
            case 0:
                return TasksListFragment.newInstance(purposeId);
            case 1:
                return NotesFragment.newInstance(purposeId);
            case 2:
                return ReportsFragment.newInstance(purposeId);
            default:
                return null;
        }
    }

    public int getCount() {
        return pages.size();
    }

    public CharSequence getPageTitle(int position) {
        return  pages.get(position);
    }
}
