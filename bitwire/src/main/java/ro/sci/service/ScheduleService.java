package ro.sci.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.springframework.stereotype.Service;

import ro.sci.db.JDBCScheduleDAO;
import ro.sci.domain.Schedule;

@Service
/**
 * This class is for saving (inserting into database), and validating the input from schedule_create.html.
 * @author Sandor Naghi
 *
 */
public class ScheduleService {

	private JDBCScheduleDAO dao = new JDBCScheduleDAO("localhost", "5432", "userdb", "postgres", "bubu");

	/**
	 * Create the schedule in the database, after vaidating the title, content, and date.
	 * @param schedule		The schedule that's need to be inserted, and validated.
	 * @return				The schedule that is inserted.
	 */
	public Schedule save(Schedule schedule) {		
		if ((validateTitle(schedule)) && ((validateContent(schedule)) && (validateDate(schedule)))) {
			if(findByTitle(schedule.getTitle()) != null){		//if the title exists, then not insert
				System.out.println("Schedule title allready exists.");
			}else{
				dao.create(schedule);
			}
		}
		return schedule;
	}
	
	/**
	 * Validating the title of the schedule. The title of the schedule is unique.
	 * @param schedule		Schedules title needed to be validated.
	 * @return				True if the title is valid.
	 */
	public boolean validateTitle(Schedule schedule){
		if (schedule.getTitle().isEmpty()) {
			System.out.println("Empty title.");
			throw new IllegalStateException();
		}
		return true;
	}
	/**
	 * Validating the date of the Schedule. It has to be a valid date and time, and in the future.
	 * @param schedule		Schedules date and time needed to be validated.
	 * @return				True if the date and time is valid.
	 */
	public boolean validateDate(Schedule schedule){
		String time = schedule.getPostingTime();
		String [] array = time.split(":");
		int a = -1, b = -1;
		try{
			a = Integer.parseInt(array[0]);
			b = Integer.parseInt(array[1]);
		}catch(NumberFormatException e){
			
		}
		if(!((a >= 0) && (a <= 23) && (b >= 0) && (b <= 59))){
			System.out.println("Please insert valid date and time!");
			throw new IllegalStateException();
		}
		
		if(schedule.getPostingDate().isEmpty() || schedule.getPostingTime().isEmpty()){
			System.out.println("Please insert valid date and time!");
			throw new IllegalStateException();
		}
		
		StringBuffer scheduleDate = new StringBuffer(schedule.getPostingDate());
		scheduleDate.append(" ");
		scheduleDate.append(schedule.getPostingTime());
		DateFormat format = new SimpleDateFormat( "yyyy-MM-dd hh:mm");
		Date date = null;
		try{
			date = format.parse(scheduleDate.toString());
			schedule.setTimestamp(date);
		}catch(ParseException e){
			System.out.println("Please insert valid date and time!");
			throw new IllegalStateException();
		}
		Date now = new Date();
		if(!date.after(now)){
			System.out.println("Please insert date and time in the future!");
			throw new IllegalStateException();
		}
		return true;
	}
	
	/**
	 * Validating the content of the schedule. It's should not be empty.
	 * @param schedule	Schedules content needed to be valid.
	 * @return	True if the content is valid.
	 */
	public boolean validateContent(Schedule schedule){
		if(schedule.getContent().isEmpty()){
			System.out.println("Please insert some content!");
			throw new IllegalStateException();
		}
		return true;
	}
	
	public Collection<Schedule> listAll() {
		return dao.getAll();
	}
	
	public boolean delete(String title) {
		Schedule schedule= dao.findByTitle(title);
		if (schedule == null) {
			throw new IllegalStateException();
		} else {
			return dao.delete(schedule);
		}
	}
	public Schedule findByTitle(String title) {
		Schedule schedule = dao.findByTitle(title);
		return schedule;
	}
	
}
