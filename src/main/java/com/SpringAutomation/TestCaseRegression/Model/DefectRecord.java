package com.SpringAutomation.TestCaseRegression.Model;




public class DefectRecord {

    private String testCaseId;
    private String defectDescription;

    public DefectRecord(String testCaseId, String defectDescription) {
        this.testCaseId = testCaseId;
        this.defectDescription = defectDescription;
    }

    public String getTestCaseId() {
        return testCaseId;
    }

    public void setTestCaseId(String testCaseId) {
        this.testCaseId = testCaseId;
    }

    public String getDefectDescription() {
        return defectDescription;
    }

    public void setDefectDescription(String defectDescription) {
        this.defectDescription = defectDescription;
    }

    public DefectRecord() {
    }
//private String defectId;

}
