package com.wgfxer.projectpurpose.data.database;

import com.wgfxer.projectpurpose.models.domain.Note;
import com.wgfxer.projectpurpose.models.domain.PurposeTheme;
import com.wgfxer.projectpurpose.models.domain.Report;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class PurposeConverterTest {

    private PurposeConverter converter;

    @Before
    public void setUp() {
        converter = new PurposeConverter();
    }

    @Test
    public void fromThemeTest() {
        PurposeTheme theme = new PurposeTheme();
        String themeString = converter.fromTheme(theme);
        assertEquals(theme, converter.toTheme(themeString));
    }

    @Test
    public void toThemeTest() {
        PurposeTheme theme = new PurposeTheme();
        String themeString = converter.fromTheme(theme);
        assertEquals(theme, converter.toTheme(themeString));
    }

    @Test
    public void fromDateTest() {
        Date date = new Date();
        long dateMillis = converter.fromDate(date);
        assertEquals(date.getTime(), dateMillis);
    }

    @Test
    public void toDateTest() {
        long dateMillis = 131313;
        Date date = converter.toDate(dateMillis);
        assertEquals(new Date(dateMillis), date);
    }

    @Test
    public void fromListNotesTest() {
        List<Note> notesList = getListNotes();
        String notesListString = converter.fromListNotes(notesList);
        assertEquals(notesList, converter.toListNotes(notesListString));
    }

    @Test
    public void toListNotesTest() {
        List<Note> notesList = getListNotes();
        String notesListString = converter.fromListNotes(notesList);
        assertEquals(notesList, converter.toListNotes(notesListString));
    }

    @Test
    public void fromListReportsTest() {
        List<Report> reportsList = getListReports();
        String reportsListString = converter.fromListReports(reportsList);
        assertEquals(reportsList, converter.toListReports(reportsListString));
    }

    @Test
    public void toListReportsTest() {
        List<Report> reportsList = getListReports();
        String reportsListString = converter.fromListReports(reportsList);
        assertEquals(reportsList, converter.toListReports(reportsListString));
    }

    private List<Note> getListNotes() {
        List<Note> noteList = new ArrayList<>();
        Note note1 = new Note(2, 4);
        Note note2 = new Note(1, 2);
        Note note3 = new Note(5, 6);
        noteList.add(note1);
        noteList.add(note2);
        noteList.add(note3);
        return noteList;
    }

    private List<Report> getListReports() {
        List<Report> reportsList = new ArrayList<>();
        Report report1 = new Report(4234324, "sdasd", "asdsad", "232sad", "42dsaasd");
        Report report2 = new Report(432532532, "sdasd", "asdsad", "232sad", "42dsaasd");
        Report report3 = new Report(42343434, "sdasd", "asdsad", "232sad", "42dsaasd");
        reportsList.add(report1);
        reportsList.add(report2);
        reportsList.add(report3);
        return reportsList;
    }
}