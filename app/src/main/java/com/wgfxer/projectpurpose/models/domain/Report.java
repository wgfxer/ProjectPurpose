package com.wgfxer.projectpurpose.models.domain;

import androidx.annotation.NonNull;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Report report = (Report) o;

        if (dateReport != report.dateReport) return false;
        if (titleReport != null ? !titleReport.equals(report.titleReport) : report.titleReport != null)
            return false;
        if (descriptionReport != null ? !descriptionReport.equals(report.descriptionReport) : report.descriptionReport != null)
            return false;
        if (whatDidGood != null ? !whatDidGood.equals(report.whatDidGood) : report.whatDidGood != null)
            return false;
        return whatCouldBetter != null ? whatCouldBetter.equals(report.whatCouldBetter) : report.whatCouldBetter == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (dateReport ^ (dateReport >>> 32));
        result = 31 * result + (titleReport != null ? titleReport.hashCode() : 0);
        result = 31 * result + (descriptionReport != null ? descriptionReport.hashCode() : 0);
        result = 31 * result + (whatDidGood != null ? whatDidGood.hashCode() : 0);
        result = 31 * result + (whatCouldBetter != null ? whatCouldBetter.hashCode() : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "Report{" +
                "dateReport=" + dateReport +
                ", titleReport='" + titleReport + '\'' +
                ", descriptionReport='" + descriptionReport + '\'' +
                ", whatDidGood='" + whatDidGood + '\'' +
                ", whatCouldBetter='" + whatCouldBetter + '\'' +
                '}';
    }
}
