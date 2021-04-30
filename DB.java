package com.cn.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据库操作工具类
 */
public class DB
{
	//数据库连接
	private Connection con;
	
	private PreparedStatement pstm;

	//数据库用户名
	private String user = "root";
	//数据库密码
	private String password = "root";
	//数据库驱动
	private String className = "com.mysql.jdbc.Driver";
	//数据库jdbc url
	private String url = "jdbc:mysql://localhost:3306/shopp?useUnicode=true&characterEncoding=UTF-8";

	
	public DB()
	{
		try
		{
			Class.forName(className);
		} catch (ClassNotFoundException e)
		{
			System.out.println("加载数据库驱动失败！");
			e.printStackTrace();
		}
	}

	/**
	 * 获得数据库连接
	 * @return 数据库连接
	 */
	public Connection getCon()
	{
		try
		{
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e)
		{
			System.out.println("创建数据库连接失败！");
			con = null;
			e.printStackTrace();
		}
		return con;
	}

	/**
	 * 执行sql语句
	 * @param sql
	 * @param params
	 */
	public void doPstm(String sql, Object[] params)
	{
		if (sql != null && !sql.equals(""))
		{
			if (params == null)
				params = new Object[0];

			getCon();
			if (con != null)
			{
				try
				{
					System.out.println("执行SQL:"+sql);
					pstm = con.prepareStatement(sql,
							ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
					for (int i = 0; i < params.length; i++)
					{
						pstm.setObject(i + 1, params[i]);
					}
					pstm.execute();
				} catch (SQLException e)
				{
					System.out.println("doPstm()方法出错！");
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 获得ResultSet
	 * @return
	 * @throws SQLException
	 */
	public ResultSet getRs() throws SQLException
	{
		return pstm.getResultSet();
	}

 

	/**
	 * 关闭数据库
	 */
	public void closed()
	{
		try
		{
			if (pstm != null)
				pstm.close();
		} catch (SQLException e)
		{
			System.out.println("关闭pstm对象失败！");
			e.printStackTrace();
		}
		try
		{
			if (con != null)
			{
				con.close();
			}
		} catch (SQLException e)
		{
			System.out.println("关闭con对象失败！");
			e.printStackTrace();
		}
	}
	
	
	
}
