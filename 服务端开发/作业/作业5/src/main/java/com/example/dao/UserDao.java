package com.example.dao;

import com.example.domain.LoginLog;
import com.example.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;

//@Repository
//public class UserDao {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    public int getMatchCount(String userName, String password) {
//        String sqlStr = " SELECT count(*) FROM t_user "
//                + " WHERE user_name =? and password=? ";
//        return jdbcTemplate.queryForObject(sqlStr, new Object[]{userName, password}, Integer.class);
//    }
//
//    public User findUserByUserName(final String userName) {
//        String sqlStr = " SELECT user_id,user_name "
//                + " FROM t_user WHERE user_name =? ";
//
//        return jdbcTemplate.queryForObject(sqlStr, (ResultSet rs, int rowNum) -> {
//            User user = new User(rs.getInt("user_id"), rs.getString("user_name"));
//            return user;
//        }, userName);
//    }
//
//    public void updateLoginInfo(User user) {
//        String sqlStr = " UPDATE t_user SET last_visit=?,last_ip=?"
//                + " WHERE user_id =?";
//        jdbcTemplate.update(sqlStr, new Object[]{user.getLastVisit(),
//                user.getLastIp(), user.getUserId()});
//    }
//}
public interface UserDao extends JpaRepository<LoginLog, Integer> {
    @Query("SELECT count(u) FROM User u WHERE u.userName = ?1 and u.password = ?2")
    Long getMatchCount(String userName, String password);

    @Query("SELECT u FROM User u WHERE u.userName =?1")
    User findUserByUserName(final String userName);


}