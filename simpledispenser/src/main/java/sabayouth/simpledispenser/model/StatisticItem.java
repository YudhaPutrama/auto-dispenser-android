package sabayouth.simpledispenser.model;


public class StatisticItem {
    private Integer id;
    private String date;
    private Integer volume;

    public StatisticItem(){
        setId(0);
        setDate("00:00");
        setVolume(0);
    }
    public StatisticItem(String date, Integer volume){
        setId(0);
        setDate(date);
        setVolume(volume);
    }

    public Integer getId(){return id;}
    public String getDate(){return date;}
    public Integer getVolume(){return volume;}

    public void setId(Integer id){this.id = id;}
    public void setDate(String date){this.date = date;}
    public void setVolume(Integer volume){this.volume = volume;}
}
