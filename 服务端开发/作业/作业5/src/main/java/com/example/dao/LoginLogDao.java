package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.LoginLog;
import org.springframework.data.jpa.repository.Query;

//@Repository
//public class LoginLogDao {
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    public void insertLoginLog(LoginLog loginLog) {
//        String sqlStr = "INSERT INTO t_login_log(user_id,ip,login_datetime) "
//                + "VALUES(?,?,?)";
//        Object[] args = {loginLog.getUserId(), loginLog.getIp(),
//                loginLog.getLoginDate()};
//        jdbcTemplate.update(sqlStr, args);
//    }
//}

public interface LoginLogDao extends JpaRepository<LoginLog, Long> {
}