package shilpakarita.indobytes.com.excelsheetproject;

/**
 * Created by indobytes21 on 8/17/2017.
 */

public class Taskmodel {

    private String stg_name,stg_task,stg_date,stg_comment;

    public Taskmodel(String stg_name, String stg_task, String stg_date,String stg_comment) {
        this.stg_name = stg_name;
        this.stg_task = stg_task;
        this.stg_date = stg_date;
        this.stg_comment = stg_comment;
    }

    public String getStg_name() {
        return stg_name;
    }

    public void setStg_name(String stg_name) {
        this.stg_name = stg_name;
    }

    public String getStg_task() {
        return stg_task;
    }

    public void setStg_task(String stg_task) {
        this.stg_task = stg_task;
    }

    public String getStg_date() {
        return stg_date;
    }

    public void setStg_date(String stg_date) {
        this.stg_date = stg_date;
    }

    public String getStg_comment() {
        return stg_comment;
    }

    public void setStg_comment(String stg_comment) {
        this.stg_comment = stg_comment;
    }
}
