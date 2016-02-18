package ro.sci.dao;

import java.util.Collection;
import ro.sci.domain.Schedule;

/**
 * Created 31/Jan/2016
 * @author Sandor Naghi
 */
public interface ScheduleDAO extends BaseDAO<Schedule>{

 Collection<Schedule> searchByName(String query);
}