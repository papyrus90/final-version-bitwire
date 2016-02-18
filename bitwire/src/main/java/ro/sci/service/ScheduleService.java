package ro.sci.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.sci.dao.ScheduleDAO;
import ro.sci.domain.Schedule;

@Service
public class ScheduleService {

 @Autowired
 private ScheduleDAO dao;

 public Schedule save(Schedule schedule) {
//  if((!validateTitle(schedule)) || ((!validateContent(schedule)) || (!validateDate(schedule)))){
//   
//  }else{
//   dao.update(schedule);
//  }
  if((validateTitle(schedule)) && ((validateContent(schedule)) && (validateDate(schedule)))){
   dao.update(schedule);
  }
  return schedule;
 }
 
 
 
 //from here
 public boolean validateTitle(Schedule schedule){
  if (schedule.getTitle().equals("")) {
   System.out.println("Empty title");
   return false;
  }
  return true;
 }
 public boolean validateDate(Schedule schedule){
  StringBuffer scheduleDate = new StringBuffer(schedule.getPostingDate());
  scheduleDate.append(" ");
  scheduleDate.append(schedule.getPostingTime());
  DateFormat format = new SimpleDateFormat( "yyyy-MM-dd hh:mm");
  Date date = null;
  try{
   date = format.parse(scheduleDate.toString());
   schedule.setDate();
  }catch(ParseException e){
   System.out.println("Please insert valid date and time!");
   return false;
  }
  Date now = new Date();
  if(!date.after(now)){
   System.out.println("Please insert valid date and time!");
   return false;
   //throw new IllegalArgumentException("Please insert a time in the future!");
  }
  return true;
 }
 public boolean validateContent(Schedule schedule){
  if(schedule.getContent().equals("")){
   System.out.println("Please insert some content!");
   return false;
   //throw new IllegalArgumentException("Please insert some content!");
  }
  return true;
 }
 
 public Collection<Schedule> listAll() {
  return dao.getAll();
 }
 public boolean delete(long id) {
  Schedule schedule= dao.findById(id);
  if (schedule == null) {
   return false;
   //throw new IllegalArgumentException("Schedule not exists");
  } else {
   return dao.delete(schedule);
  }
 }
 public Schedule findById(long id) {
  Schedule schedule = dao.findById(id);
  return schedule;
 }

}