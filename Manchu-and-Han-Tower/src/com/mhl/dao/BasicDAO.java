package com.mhl.dao;

import com.mhl.utils.JDBCCUtilsByDruid;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BasicDAO<T> {

    private QueryRunner qr = new QueryRunner();

    //通用dml方法
    public int update(String sql, Object... parameters) {
        Connection connection = null;

        try {
            connection = JDBCCUtilsByDruid.getConnection();
            int update = qr.update(connection, sql, parameters);
            return update;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCCUtilsByDruid.close(null, null, connection);
        }

    }

    /**
     * 返回多个对象
     *
     * @param sql        sql语句 可以有 ？
     * @param clazz      传入一个类的Class对象 如 Actor.class
     * @param parameters 传入 ？ 的具体值
     * @return 根据 Actor.class 返回对应的 ArrayList 集合
     */
    public List<T> queryMulti(String sql, Class<T> clazz, Object... parameters) {

        Connection connection = null;
        try {
            connection = JDBCCUtilsByDruid.getConnection();
            return qr.query(connection, sql, new BeanListHandler<T>(clazz), parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCCUtilsByDruid.close(null, null, connection);
        }

    }

    //查询单行结果
    public T querySingle(String sql, Class<T> clazz, Object... parameters) {

        Connection connection = null;
        try {
            connection = JDBCCUtilsByDruid.getConnection();
            return qr.query(connection, sql, new BeanHandler<T>(clazz), parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCCUtilsByDruid.close(null, null, connection);
        }

    }

    //查询单行单列结果
    public Object queryScalar(String sql,Object... parameters) {

        Connection connection = null;
        try {
            connection = JDBCCUtilsByDruid.getConnection();
            return qr.query(connection, sql, new ScalarHandler(), parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCCUtilsByDruid.close(null, null, connection);
        }

    }

}
