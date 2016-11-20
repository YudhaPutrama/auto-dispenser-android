package sabayouth.simpledispenser.model;

public class AlarmItem {
    Integer id;
    String time;
    String status;

    public static final String ACTIVE = "ACTIVE";
    public static final String NONACTIVE = "NONACTIVE";

    public AlarmItem(){
    }

    public AlarmItem(String time){
        this.time = time;
        this.status = ACTIVE;
    }
    public AlarmItem(String time, String status){
        this.time = time;
        this.status = status;
    }

    //getters
    public Integer getId(){return this.id;}
    public String getTime(){return this.time;}
    public String getStatus(){return this.status;}

    //setter
    public void setId(Integer id){this.id = id;}
    public void setTime(String time){this.time = time;}
    public void setStatus(String status){this.status = status;}

}
