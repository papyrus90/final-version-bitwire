package ro.sci.service;

import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ro.sci.BitwireApplication;
import ro.sci.domain.Schedule;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BitwireApplication.class)

/**
 * This is a JUnit class for the Schedule part of the project.
 * @author Sandor Naghi
 *
 */
public class ScheduleServiceTest {

	@Autowired
	private ScheduleService service;
		
	@Test
	public void additionSuccessfull(){
		Schedule sch = new Schedule();
		sch.setTitle("bubu1");
		sch.setPostingDate("2017-01-01");
		sch.setPostingTime("01:01");
		sch.setContent("bibi");
		service.save(sch);
		Assert.assertFalse(service.listAll().isEmpty());
		service.delete(sch.getTitle());		// to delete from db what inserted in JUnit test
	}
	@Test
	public void testSaveNewSchedule() {
		Schedule schedule = new Schedule();
		schedule.setTitle("bubu2");
		schedule.setPostingDate("2017-01-01");
		schedule.setPostingTime("01:01");
		schedule.setContent("bibi");
		Schedule savedSchedule = service.save(schedule);
		Assert.assertEquals("bubu2", savedSchedule.getTitle());
		service.delete(schedule.getTitle());
	}
	@Test(expected = IllegalStateException.class)
	public void addingEmptyTitle(){
		Schedule schedule = new Schedule();
		schedule.setTitle("");
		schedule.setPostingDate("2017-01-01");
		schedule.setPostingTime("01:01");
		schedule.setContent("bibi");
		service.save(schedule);
	}
	@Test
	public void doubleAddition(){
		Schedule sch1 = new Schedule();
		sch1.setTitle("bubu3");
		sch1.setPostingDate("2017-01-01");
		sch1.setPostingTime("01:01");
		sch1.setContent("bibi");
		service.save(sch1);
		Collection<Schedule> list1 = service.listAll();
		Schedule sch2 = new Schedule();
		sch2.setTitle("bubu3");
		sch2.setPostingDate("2017-01-01");
		sch2.setPostingTime("01:01");
		sch2.setContent("bibi");
		service.save(sch2);
		Collection<Schedule> list2 = service.listAll();
		Assert.assertEquals(list1.size(), list2.size());
		service.delete(sch1.getTitle());
	}	
	@Test
	public void deleteSchedule(){
		Schedule sch = new Schedule();
		sch.setTitle("bubu4");
		sch.setPostingDate("2017-01-01");
		sch.setPostingTime("01:01");
		sch.setContent("bibi");
		service.save(sch);
		Schedule help1 = service.findByTitle(sch.getTitle());
		service.delete(sch.getTitle());
		Schedule help2 = service.findByTitle(sch.getTitle());
		Assert.assertNotEquals(help1, help2);
	}
	@Test(expected = IllegalStateException.class)
	public void doubleDeletion(){
		Schedule sch = new Schedule();
		sch.setTitle("bubu5");
		sch.setPostingDate("2017-01-01");
		sch.setPostingTime("01:01");
		sch.setContent("bibi");
		service.save(sch);
		service.delete(sch.getTitle());
		service.delete(sch.getTitle());
	}
	@Test(expected = IllegalStateException.class)
	public void emptyDate(){
		Schedule sch = new Schedule();
		sch.setTitle("bubu6");
		sch.setPostingDate("");
		sch.setPostingTime("01:01");
		sch.setContent("bibi");
		service.save(sch);
	}
	@Test(expected = IllegalStateException.class)
	public void emptyTime(){
		Schedule sch = new Schedule();
		sch.setTitle("bubu7");
		sch.setPostingDate("2017-01-01");
		sch.setPostingTime("");
		sch.setContent("bibi");
		service.save(sch);
	}
	@Test(expected = IllegalStateException.class)
	public void dateInThePast(){
		Schedule sch = new Schedule();
		sch.setTitle("bubu8");
		sch.setPostingDate("2014-01-01");
		sch.setPostingTime("01:01");
		sch.setContent("bibi");
		service.save(sch);
	}
	@Test(expected = IllegalStateException.class)
	public void illegalHour(){
		Schedule sch = new Schedule();
		sch.setTitle("bubu9");
		sch.setPostingDate("2017-01-01");
		sch.setPostingTime("70:60");
		sch.setContent("bibi");
		service.save(sch);
	}
	@Test(expected = IllegalStateException.class)
	public void emptyContent(){
		Schedule sch = new Schedule();
		sch.setTitle("bubu10");
		sch.setPostingDate("2017-01-01");
		sch.setPostingTime("20:30");
		sch.setContent("");
		service.save(sch);
	}
}
