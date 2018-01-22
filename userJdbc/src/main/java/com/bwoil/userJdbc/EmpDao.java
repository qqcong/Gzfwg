package com.bbbbb.userJdbc;

import java.sql.*;   
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @date 2017年12月15日 下午2:24:39
 */
public class EmpDao {
//	public static final String url = "jdbc:mysql://127.0.0.1:3306/student";  
//    public static final String name = "com.mysql.jdbc.Driver";  
//    public static final String user = "root";  
//    public static final String password = "root";
	public List findPageMysql(int start,int limit){
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		List list=null;
		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			con = DriverManager.getConnection(url,user,password);
			con = ConnectionSource.getConnection();
			stmt = con.prepareStatement("select * from xxx limit ? ,?");
			stmt.setInt(1, start);
			stmt.setInt(2, limit);
			rs=stmt.executeQuery();
			list=new ArrayList();
			StringBuffer sb = new StringBuffer();
			 while(rs.next()){
	                // 通过字段检索
	                int id  = rs.getInt("xx");
	                String name = rs.getString("xxx");
	                String url = rs.getString("xxxx");
					sb.append("insert into xxx (xx, xx, xx, xx) values (").append(");\n");
	                list.add("xxx");
	            }
			 sb.append("\ncommit;");
			 FileUtil.writeFile("/path/","文件名sql",sb.toString(),"UTF-8");
		}catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(rs!=null) rs.close();
                if(stmt!=null) stmt.close();
                if(con!=null) con.close();
            }catch(SQLException se2){
            	System.out.println("关闭数据库异常");
            }
        }
		return list;
			
	}
	public static void main(String[] args) {
		int time=0;
		boolean flag=true;
		while(flag){
			int limit=1000;
			int start=time*limit;
			EmpDao dao=new EmpDao();
			List list=dao.findPageMysql(start,limit);
			if(list==null||list.size()==0){
				flag=false;
			}
		}
		
	}

}
