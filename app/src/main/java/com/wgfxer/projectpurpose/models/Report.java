package com.wgfxer.projectpurpose.models;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

/**
 * Модель отчета по цели
 */
@Entity(tableName = "reports",foreignKeys = @ForeignKey(entity = Purpose.class,parentColumns = "id", childColumns = "purposeId", onDelete = CASCADE))
public class Report {

    @PrimaryKey(autoGenerate = true)
    private int reportId;

    private long reportDate;

    private String titleReport;

    private String descriptionReport;

    private String whatDidGood;

    private String whatCouldBetter;

    private int purposeId;

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public long getReportDate() {
        return reportDate;
    }

    public void setReportDate(long reportDate) {
        this.reportDate = reportDate;
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

    public int getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(int purposeId) {
        this.purposeId = purposeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Report report = (Report) o;

        if (reportId != report.reportId) return false;
        if (reportDate != report.reportDate) return false;
        if (purposeId != report.purposeId) return false;
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
        int result = reportId;
        result = 31 * result + (int) (reportDate ^ (reportDate >>> 32));
        result = 31 * result + (titleReport != null ? titleReport.hashCode() : 0);
        result = 31 * result + (descriptionReport != null ? descriptionReport.hashCode() : 0);
        result = 31 * result + (whatDidGood != null ? whatDidGood.hashCode() : 0);
        result = 31 * result + (whatCouldBetter != null ? whatCouldBetter.hashCode() : 0);
        result = 31 * result + purposeId;
        return result;
    }

    @Override
    public String toString() {
        return "Report{" +
                "reportId=" + reportId +
                ", reportDate=" + reportDate +
                ", titleReport='" + titleReport + '\'' +
                ", descriptionReport='" + descriptionReport + '\'' +
                ", whatDidGood='" + whatDidGood + '\'' +
                ", whatCouldBetter='" + whatCouldBetter + '\'' +
                ", purposeId=" + purposeId +
                '}';
    }
}

