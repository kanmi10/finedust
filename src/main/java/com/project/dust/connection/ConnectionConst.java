package com.project.dust.connection;

public abstract class ConnectionConst {

    /**
     * H2 Database 사용
     */
  /*
    public static final String URL = "jdbc:h2:tcp://localhost/~/test";
    public static final String USERNAME = "sa";
    public static final String PASSWORD = "";

  */
    /**
     * MySQL Database 사용
     * 데이터베이스에 접속하는데 필요한 기본 정보를 편리하게 사용할 수 있도록하는 상수
     */
    public static final String URL = "jdbc:mysql://localhost:3306/today_dust";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "java1234";

}
