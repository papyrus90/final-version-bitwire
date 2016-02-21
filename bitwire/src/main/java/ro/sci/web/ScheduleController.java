package ro.sci.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ro.sci.domain.Schedule;
import ro.sci.service.ScheduleService;

/**
 * This is a Controller class, with the mapping to "/schedule", and it's creating views for the list and the creation of the schedule.
 * @author Sandor Naghi
 *
 */
@Controller
@RequestMapping("/schedule")
public class ScheduleController {
	@Autowired
	ScheduleService scheduleService;

	/**
	 * Creating a list, of all schedules in the database.
	 * @return		A ModelAndView object, with the list of all schedules.
	 */
	@RequestMapping("")
	public ModelAndView list() {
		ModelAndView view = new ModelAndView("schedule_list");
		view.addObject("schedule", scheduleService.listAll());
		return view;
	}

	/**
	 * Save the Schedule.
	 * @param schedule		The Schedule that it's need to be saved.
	 * @return				A ModelAndView object.
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView saveSchedule(Schedule schedule) {
		ModelAndView result = list();
		try {
			scheduleService.save(schedule);
		} catch (Exception e) {			
			result = renderEditPage(schedule.getTitle());
		}
		return result;
	}

	/**
	 * 
	 * @param title	
	 * @return
	 */
	@RequestMapping("/schedule_create")
	public ModelAndView renderEditPage(String title) {
		ModelAndView result = new ModelAndView("schedule_create");
		Schedule schedule = new Schedule();
		if (title != null) {
			schedule = scheduleService.findByTitle(title);
		}
		result.addObject("schedule", schedule);
		return result;
	}

	/**
	 * Trying to delete a Schedule from the list.
	 * @param title		Title of the Schedule after we make the delete.
	 * @return			The list of all Schedules not deleted from database.
	 */
	@RequestMapping("/schedule_delete")
	public ModelAndView onDelete(String title) {
		ModelAndView result = list();
		if (!scheduleService.delete(title)) {
			result.addObject("error", "ERROR DELETING INEXISTENT SCHEDULE!");
		}
		return result;
	}
}