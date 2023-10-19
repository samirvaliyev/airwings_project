package com.example.epamfinalproject.Utility;

import com.example.epamfinalproject.Entities.Enums.UserRole;
import com.example.epamfinalproject.Entities.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Utility class for dynamic creation SQL Query
 */
public class QueryBuilder {

    /**
     * Utility function to dynamically create an SQL query based on filter values
     *
     * @param page number of new page
     * @param user logged or empty user
     * @return query string
     */
    public String airplaneQueryBuilder(HttpServletRequest request, int page, User user) {
        StringBuilder query =
                new StringBuilder(
                        "select * from airplanes "
                                + "inner join routes r on r.id = airplanes.route_id"
                                + " inner join flights s on s.id = airplanes.flight_id "
                                + " where deleted = false");

        Object leavingDate = request.getSession().getAttribute(FieldKey.AIRPLANE_LEAVING);
        if (leavingDate != null && leavingDate != ""
                && Validation.isDateValid(String.valueOf(leavingDate))) {
            query.append(" and start_date >= ").append("'").append(leavingDate).append("'");
        } else if (user == null || user.getRole() == UserRole.CLIENT) {
            query.append(" and start_date >= now()");
        }
        if(user != null && user.getRole().equals(UserRole.ADMINISTRATOR)){
            query.append(" and confirmed = false");
        }
        Object arrivingDate = request.getSession().getAttribute(FieldKey.AIRPLANE_ARRIVING);
        if (arrivingDate != null && !"".equals(arrivingDate)
                && Validation.isDateValid(String.valueOf(arrivingDate))) {
            query.append(" and end_date <= ").append("'").append(arrivingDate).append("'");
        }

        Object transitTime = request.getSession().getAttribute(FieldKey.TRANSIT_TIME);
        if (transitTime != null && !"".equals(transitTime) && Integer.parseInt((String) transitTime) > 0) {
            query.append(" and r.transit_time >= ").append(transitTime);
        }
        query
                .append(" limit ")
                .append(Constants.PAGE_SIZE)
                .append(" offset ")
                .append((page - 1) * Constants.PAGE_SIZE);
        return query.toString();
    }

    /**
     * Utility function to dynamically create an SQL query for counting number of all records based on
     * filter values
     *
     * @param user logged or empty user
     * @return query string
     */
    public String airplaneCountQueryBuilder(HttpServletRequest request, User user) {
        StringBuilder query =
                new StringBuilder(
                        "select count(airplanes.id) from airplanes inner join routes r on r.id = airplanes.route_id"
                                + " where deleted = false");

        Object leavingDate = request.getSession().getAttribute(FieldKey.AIRPLANE_LEAVING);
        if (leavingDate != null
                && leavingDate != ""
                && Validation.isDateValid(String.valueOf(leavingDate))) {
            query.append(" and start_date >= ").append("'").append(leavingDate).append("'");
        } else if (user == null || user.getRole() == UserRole.CLIENT) {
            query.append(" and start_date >= now()");
        }
        if(user != null && user.getRole().equals(UserRole.ADMINISTRATOR)){
            query.append(" and confirmed = false");
        }
        Object arrivingDate = request.getSession().getAttribute(FieldKey.AIRPLANE_ARRIVING);
        if (arrivingDate != null
                && !"".equals(arrivingDate)
                && Validation.isDateValid(String.valueOf(arrivingDate))) {
            query.append(" and end_date <= ").append("'").append(arrivingDate).append("'");
        }

        Object transitTime = request.getSession().getAttribute(FieldKey.TRANSIT_TIME);
        if (transitTime != null
                && !"".equals(transitTime)
                && Integer.parseInt(String.valueOf(transitTime)) > 0) {
            query.append(" and r.transit_time >= ").append(transitTime);
        }
        return query.toString();
    }

}
