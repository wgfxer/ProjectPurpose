package com.wgfxer.projectpurpose.domain;

import java.util.Date;

public class Report {
    private long dateReport;
    private String titleReport;
    private String descriptionReport;
    private String whatDidGood;
    private String whatCouldBetter;

    public Report() {
    }

    public Report(long dateReport, String titleReport, String descriptionReport, String whatDidGood, String whatCouldBetter) {
        this.dateReport = dateReport;
        this.titleReport = titleReport;
        this.descriptionReport = descriptionReport;
        this.whatDidGood = whatDidGood;
        this.whatCouldBetter = whatCouldBetter;
    }

    public long getDateReport() {
        return dateReport;
    }

    public void setDateReport(long dateReport) {
        this.dateReport = dateReport;
    }

    public String getTitleReport() {
        return titleReport;
    }

    public void setTitleReport(String titleReport) {
        this.titleReport = titleReport;
    }

    public String getDescriptionReport() {
        return descriptionReport;
    }

    public void setDescriptionReport(String descriptionReport) {
        this.descriptionReport = descriptionReport;
    }

    public String getWhatDidGood() {
        return whatDidGood;
    }

    public void setWhatDidGood(String whatDidGood) {
        this.whatDidGood = whatDidGood;
    }

    public String getWhatCouldBetter() {
        return whatCouldBetter;
    }

    public void setWhatCouldBetter(String whatCouldBetter) {
        this.whatCouldBetter = whatCouldBetter;
    }
}
