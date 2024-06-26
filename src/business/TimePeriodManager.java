package business;

import dao.TimePeriodDao;
import entity.TimePeriod;

public class TimePeriodManager {

    private final TimePeriodDao timePeriodDao;

    public TimePeriodManager(){
        this.timePeriodDao = new TimePeriodDao();
    }

    public TimePeriod getById(int id){
        return this.timePeriodDao.getById(id);
    }

}
