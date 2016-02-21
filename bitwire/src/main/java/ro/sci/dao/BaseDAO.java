package ro.sci.dao;

import java.util.Collection;

import ro.sci.domain.Schedule;

/**
 * This Interface is for declaring the methods used for Database manipulation.
 * 
 * @author Naghi Sandor
 *
 * @param <T>	Everything that extends from Schedule class.
 */
public interface BaseDAO<T extends Schedule> {
	
	/**
	 * This method returns all the schedules from the database.
	 * @return		A LinkedList with all the schedules.
	 */
	Collection<T> getAll();

	/**
	 * This method find a schedule by the title, if it's exists.
	 * @return		Schedule if it's exists, null if not exists.
	 * @param title	The title of the Schedule.
	 */
	T findByTitle(String title);
	
	/**
	 * Insert a schedule in the database.
	 * @return			Schedule that is inserted to the database.
	 * @param model		The Schedule needed to create.
	 */
	T create(T model);
	
	/**
	 * This method delete a schedule from the database.
	 * @return			True if the schedule was deleted, false if it's not.
	 * @param model		The Schedule needed to delete.
	 */
	boolean delete(T model);
}
