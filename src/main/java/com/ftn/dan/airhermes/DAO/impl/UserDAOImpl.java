package com.ftn.dan.airhermes.DAO.impl;

import com.ftn.dan.airhermes.DAO.UserDAO;
import com.ftn.dan.airhermes.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            int index = 1;
            Long id = rs.getLong(index++);
            String name = rs.getString(index++);
            String surname = rs.getString(index++);
            String username = rs.getString(index++);
            String password = rs.getString(index++);
            String email = rs.getString(index++);
            Timestamp dateOfBirth = rs.getTimestamp(index++);
            Timestamp registrationTimestamp = rs.getTimestamp(index++);
            Boolean admin = rs.getBoolean(index++);
            Boolean blocked = rs.getBoolean(index++);

            User user = new User(id, name, surname, username, password, email, dateOfBirth, registrationTimestamp, admin, blocked);
            return user;
        }

    }

    @Override
    public List<User> find(String name, String surname, String username, String email, Boolean admin, Boolean blocked) {
        ArrayList<Object> listaArgumenata = new ArrayList<Object>();

        String sql = "SELECT id, name, surname, username, password, email, date_of_birth, registration_timestamp, admin, blocked FROM users ";

        StringBuffer whereSql = new StringBuffer(" WHERE ");
        boolean imaArgumenata = false;

        if(name!=null) {
            name = "%" + name + "%";
            if(imaArgumenata)
                whereSql.append(" AND ");
            whereSql.append("name LIKE ?");
            imaArgumenata = true;
            listaArgumenata.add(name);
        }

        if(surname!=null) {
            surname = "%" + surname + "%";
            if(imaArgumenata)
                whereSql.append(" AND ");
            whereSql.append("surname LIKE ?");
            imaArgumenata = true;
            listaArgumenata.add(surname);
        }

        if(username!=null) {
            username = "%" + username + "%";
            if(imaArgumenata)
                whereSql.append(" AND ");
            whereSql.append("username LIKE ?");
            imaArgumenata = true;
            listaArgumenata.add(username);
        }

        if(email!=null) {
            email = "%" + email + "%";
            if(imaArgumenata)
                whereSql.append(" AND ");
            whereSql.append("email LIKE ?");
            imaArgumenata = true;
            listaArgumenata.add(email);
        }

        if(admin!=null) {
            //vraća samo administratore ili sve korisnike sistema
            String administratorSql = (admin)? "admin = 1": "admin >= 0";
            if(imaArgumenata)
                whereSql.append(" AND ");
            whereSql.append(administratorSql);
            imaArgumenata = true;
        }

        if(blocked!=null) {
            String blockedSql = (blocked)? "blocked = 1": "blocked >= 0";
            if(imaArgumenata)
                whereSql.append(" AND ");
            whereSql.append(blockedSql);
            imaArgumenata = true;
        }


        if(imaArgumenata)
            sql=sql + whereSql.toString()+" ORDER BY username";
        else
            sql=sql + " ORDER BY username";
        System.out.println(sql);

        return jdbcTemplate.query(sql, listaArgumenata.toArray(), new UserRowMapper());
    }

}
