package ro.sci.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import ro.sci.dao.BaseDAO;
import ro.sci.domain.Schedule;

/**
 * This class is used for creating a connection to the database, and for data manipulation.
 * 
 * @author Sandor Naghi
 *
 */
public class JDBCScheduleDAO implements BaseDAO<Schedule>{

	private String host;
	private String port;
	private String dbName;
	private String userName;
	private String pass;

	/**
	 * Constructor for the JDBCScheduleDAO class.
	 * @param host			The host name.
	 * @param port			The port number.
	 * @param dbName		The database name.
	 * @param userName		The username.
	 * @param pass			The password for the username.
	 */
	public JDBCScheduleDAO(String host, String port, String dbName, String userName, String pass) {
		this.host = host;
		this.userName = userName;
		this.pass = pass;
		this.port = port;
		this.dbName = dbName;
	}
	
	/**
	 * This method returns all the schedules from the database.
	 * @return		A LinkedList with all the schedules.
	 */
	@Override
	public Collection<Schedule> getAll() {
		Connection connection = newConnection();

		Collection<Schedule> result = new LinkedList<>();

		try (ResultSet rs = connection.createStatement().executeQuery("select * from table_schedule ")) {

			while (rs.next()) {
				result.add(extractSchedule(rs));
			}
			connection.commit();
		} catch (SQLException ex) {
			throw new RuntimeException("Error getting schedules.", ex);
		} finally {
			try {
				connection.close();
			} catch (Exception ex) {

			}
		}

		return result;
	}
	/**
	 * This method find a schedule by the title, if it's exists.
	 * @return		The schedule if it's exists, null if not exists.
	 */
	@Override
	public Schedule findByTitle(String title) {
		Connection connection = newConnection();

		List<Schedule> result = new LinkedList<>();

		try (ResultSet rs = connection.createStatement().executeQuery("select * from table_schedule where title = '" + title + "'")) {
			while (rs.next()) {
				result.add(extractSchedule(rs));
			}
			connection.commit();
		} catch (SQLException ex) {
			throw new RuntimeException("Error getting schedule.", ex);
		} finally {
			try {
				connection.close();
			} catch (Exception ex) {

			}
		}

		if (result.size() > 1) {
			throw new IllegalStateException("Multiple Schedule for title: " + title);
		}
		return result.isEmpty() ? null : result.get(0);
	}

	/**
	 * Insert a schedule in the database.
	 * @return		The schedule that is inserted to the database.
	 */
	@Override
	public Schedule create(Schedule schedule) {
		Connection connection = newConnection();
		try {
			PreparedStatement ps = null;
				ps = connection.prepareStatement(
						"insert into table_schedule (title, content, media, visibility, date, user_id) values (?, ?, ?, ?, ?, ?)");
			ps.setString(1, schedule.getTitle());
			ps.setString(2, schedule.getContent());
			ps.setString(3, schedule.getMedia());
			ps.setString(4, schedule.getVisibility());
			ps.setTimestamp(5, new Timestamp(schedule.getTimestamp().getTime()));
			
			ps.setInt(6, 1);		//this is where i need the userId, but cold not get it yet

			ps.executeUpdate();
			connection.commit();

		} catch (SQLException ex) {

			throw new RuntimeException("Error getting schedule.", ex);
		} finally {
			try {
				connection.close();
			} catch (Exception ex) {

			}
		}

		return schedule;

	}

	/**
	 * This method delete a schedule from the database.
	 * @return		True if the schedule was deleted, false if it's not.
	 */
	@Override
	public boolean delete(Schedule schedule) {
		boolean result = false;
		Connection connection = newConnection();
		try {
			Statement statement = connection.createStatement();
			result = statement.execute("delete from table_schedule where title = '" + schedule.getTitle() + "'");
			connection.commit();
		} catch (SQLException ex) {
			throw new RuntimeException("Error deletig schedule.", ex);
		} finally {
			try {
				connection.close();
			} catch (Exception ex) {
			}
		}
		return result;
	}
	/**
	 * It creates a connection to the database.
	 * @return	A connection to the database.
	 */
	protected Connection newConnection() {
		try {
			Class.forName("org.postgresql.Driver").newInstance();

			String url = new StringBuilder().append("jdbc:").append("postgresql").append("://").append(host).append(":")
					.append(port).append("/").append(dbName).append("?user=").append(userName).append("&password=")
					.append(pass).toString();
			Connection result = DriverManager.getConnection(url);
			result.setAutoCommit(false);
			
			return result;
		} catch (Exception ex) {
			throw new RuntimeException("Error getting DB connection", ex);
		}

	}
	/**
	 * Extracting one schedule from the database.
	 * @param rs	The ResultSet that is needed to be processed.
	 * @return		The schedule that is extracted from the database.
	 * @throws SQLException
	 */
	private Schedule extractSchedule(ResultSet rs) throws SQLException {
		Schedule schedule = new Schedule();
		
		schedule.setUserId(rs.getLong("user_id"));
		schedule.setTitle(rs.getString("title"));
		schedule.setContent(rs.getString("content"));
		schedule.setMedia(rs.getString("media"));
		schedule.setVisibility(rs.getString("visibility"));
		Date date = new Date(rs.getTimestamp("date").getTime());		//getting the date and time
		schedule.setTimestamp(date);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
		schedule.setPostingDate(simpleDateFormat.format(date));			//extracting the date
		simpleDateFormat = new SimpleDateFormat("hh:mm");
		schedule.setPostingTime(simpleDateFormat.format(date));			//extracting the time
		return schedule;
	}
}
