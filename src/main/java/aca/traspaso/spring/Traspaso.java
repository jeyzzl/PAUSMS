package  aca.traspaso.spring;

public class Traspaso{
    private String idNumber;
    private String courseGrade;
    private String courseGradeDate;
    private String subjectCode;
    private String subjectName;
    private String semester;
    private String semesterName;
    private String planId;

    public Traspaso(){
        idNumber        = "S";
        courseGrade     = "-";
        courseGradeDate = "-";
        subjectCode     = "-";
        subjectName     = "-";
        semester    = "-";
        semesterName    = "-";
        planId          = "-";
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }


    public String getCourseGrade() {
        return courseGrade;
    }

    public void setCourseGrade(String courseGrade) {
        this.courseGrade = courseGrade;
    }

    public String getCourseGradeDate() {
        return courseGradeDate;
    }

    public void setCourseGradeDate(String courseGradeDate) {
        this.courseGradeDate = courseGradeDate;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

   

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }
    
    

}