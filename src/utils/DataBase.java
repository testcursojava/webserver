package utils;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class DataBase {
	
	public final static int INTEGER = 0;
	public final static int STRING = 1;
	public final static int FLOAT = 2;
	
	private final static String URL = "jdbc:mysql://localhost:3306/portal";
	private final static String USER = "root";
	private final static String PASS = "test1234";
	
	private static Connection conn;
	
	private void init() {
		if(conn==null) {
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				conn = (Connection) DriverManager.getConnection(URL, USER, PASS);
			} catch (SQLException e) {
			    throw new IllegalStateException("Cannot connect the database!", e);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void close() {
		if(conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
			}
			conn = null;
		}
	}
	
	public RowsResultSql get(String sql,List<BindValue> bind) {
		RowsResultSql result = null;
		PreparedStatement prep;
		try {
			prep = preparaquery(sql,bind);
			if(prep!=null) {
				result = new RowsResultSql(prep.executeQuery());
				prep.close();
			}
		} catch (SQLException e) {
			throw new IllegalStateException("Sql error! "+sql, e);
		}finally {
			close();
		}
		return result;
	}
	
	public int update(String sql,List<BindValue> bind) {
		int result = -1;
		PreparedStatement prep;
		try {
			prep = preparaquery(sql,bind);
			if(prep!=null) {
				result = prep.executeUpdate();
				prep.close();
			}
			
		} catch (SQLException e) {
			throw new IllegalStateException("Sql error!", e);
		}finally{
			close();
		}
		
		return result;
	}
	
	private PreparedStatement preparaquery(String sql,List<BindValue> params) throws SQLException {
		init();
		if(conn!=null) {
			PreparedStatement prep = (PreparedStatement) conn.prepareStatement(sql);
			if(params!=null)
				bindparams(prep,params);
			return prep;
		}else
			return null;
	}
	
	private void bindparams(PreparedStatement prep,List<BindValue> params) throws NumberFormatException, SQLException {
		for(int i=0;i<params.size();i++) {
			BindValue param = params.get(i);
			int p = i+1;
			switch(param.getType()) {
			case INTEGER:
				prep.setInt(p,Integer.parseInt(param.getValue()));
				break;
			case STRING:
				prep.setString(p,param.getValue());
				break;
			case FLOAT:
				prep.setFloat(p,Float.parseFloat(param.getValue()));
				break;
			}
		}
	}
	
}
