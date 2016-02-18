package ro.sci.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ro.sci.domain.Schedule;
import ro.sci.service.ScheduleService;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {
 @Autowired
 ScheduleService scheduleService;

 @RequestMapping("")
 public ModelAndView list() {
  ModelAndView view = new ModelAndView("schedule_list");
  view.addObject("schedule", scheduleService.listAll());
  return view;
 }

 @RequestMapping(method = RequestMethod.POST)
 public ModelAndView saveSchedule(Schedule schedule,BindingResult bindingResult) {
  ModelAndView result = list();
  try {
   scheduleService.save(schedule);
  } catch (Exception e) {   
   result = renderEditPage(schedule.getUserId());
   bindingResult.addError(new ObjectError("schedule",e.getMessage()));
  }
  return result;
 }

 @RequestMapping("/schedule_edit")
 public ModelAndView renderEditPage(Long id) {
  ModelAndView result = new ModelAndView("schedule_edit");
  Schedule schedule = new Schedule();
  if (id != null) {
   schedule = scheduleService.findById(id);   
  }
  result.addObject("schedule", schedule);
  return result;
 }

 @RequestMapping("/schedule_delete")
 public ModelAndView onDelete(long id) {
  ModelAndView result = list();
  if (!scheduleService.delete(id)) {
   result.addObject("error", "ERROR DELETING INEXISTENT SCHEDULE!");
  }
  return result;
 }
}