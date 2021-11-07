package ru.javawebinar.topjava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.ClassUtils;

public class Profiles {
    public static final String
            JDBC = "jdbc",
            JPA = "jpa",
            DATAJPA = "datajpa";

    public static final String REPOSITORY_IMPLEMENTATION = DATAJPA;

    public static final String
            POSTGRES_DB = "postgres",
            HSQL_DB = "hsqldb";

    @Autowired
    private static Environment environment;

    //  Get DB profile depending on DB driver in classpath
    public static String getActiveDbProfile() {
        if (ClassUtils.isPresent("org.postgresql.Driver", null)) {
            return POSTGRES_DB;
        } else if (ClassUtils.isPresent("org.hsqldb.jdbcDriver", null)) {
            return HSQL_DB;
        } else {
            throw new IllegalStateException("Could not find DB driver");
        }
    }

    //  Get repo profile depending
    public static String getActiveRepoProfile() {
        System.out.println(environment.getActiveProfiles());
        System.out.println(System.getProperty("spring.profiles.active"));
        if (System.getProperty("spring.profiles.active") == null) {
            return DATAJPA;
        } else if (ClassUtils.isPresent("ru.javawebinar.**.repository.jpa.JpaMealRepository", null)) {
            return JPA;
        } else if (ClassUtils.isPresent("ru.javawebinar.**.repository.jdbc.JdbcMealRepository", null)) {
            return JDBC;
        } else {
            throw new IllegalStateException("Could not find repository class");
        }
    }
}
