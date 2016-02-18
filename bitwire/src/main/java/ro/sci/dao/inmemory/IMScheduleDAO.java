package ro.sci.dao.inmemory;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import ro.sci.dao.ScheduleDAO;
import ro.sci.domain.Schedule;

@Repository
public class IMScheduleDAO extends IMBaseDAO<Schedule> implements ScheduleDAO{

 @Override
 public Collection<Schedule> searchByName(String query) {
  if (StringUtils.isEmpty(query)) {
   return getAll();
  }
  
  Collection<Schedule> all = new LinkedList<Schedule>(getAll());
  for (Iterator<Schedule> it = all.iterator(); it.hasNext();) {
   Schedule emp = it.next();
   String ss = emp.getTitle();
   if (!ss.toLowerCase().contains(query.toLowerCase())) {
    it.remove();
   }
  }
  return all;
 }
}