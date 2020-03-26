package com.wgfxer.projectpurpose.presentation.view.purposeinfo;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.wgfxer.projectpurpose.presentation.view.purposeinfo.notesfragment.NotesFragment;
import com.wgfxer.projectpurpose.presentation.view.purposeinfo.reportsfragment.ReportsFragment;
import com.wgfxer.projectpurpose.presentation.view.purposeinfo.tasksfragment.TasksListFragment;
import java.util.ArrayList;
import java.util.List;

public class PurposeDetailPagerAdapter extends FragmentPagerAdapter {
    private List<String> pages = new ArrayList<>();
    private int purposeId;

    public PurposeDetailPagerAdapter(FragmentManager fm, int purposeId) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        pages.add("Задачи");
        pages.add("Заметки");
        pages.add("Отчеты");
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
