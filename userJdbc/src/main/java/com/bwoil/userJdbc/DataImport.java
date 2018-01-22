package com.bbbbb.userJdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public  abstract class DataImport {
	
	
	public abstract void post(List<CardBean> list);

	private Connection con;
	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://192.168.101.61:3306/b2c_v3.4?characterEncoding=utf8";
	private String user = "root";
	private String password = "bbbbb@20141030";

	public  void execute(long lastlastmodify) throws Exception {
		initConnection();
		int per=10;


		for(int i=0;i<1000000;i++) {
			List<CardBean> list=new ArrayList<CardBean>();
			findByPaye(i*per,per,lastlastmodify,list);
			if(list==null||list.isEmpty()){
				break;
			}
			System.out.println("list size:"+list.size());
			this.post(list);
		}
		closeConnection();
	}


	



	private  void closeConnection() throws Exception {
		con.close();
	}


	public void setUrl(String url) {
		this.url = url;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private  void initConnection() throws Exception {
		Class.forName(driver);
		con = DriverManager.getConnection(url, user, password);
	}
	
	private  void findByPaye(int start,int pageSize,long lastlastmodify,List<CardBean> result) throws Exception {
		String sql="SELECT\n" +
				"\th.member_id AS userId,\n" +
				"\th.mobile_no AS mobile,\n" +
				"\th.holder_name AS user_name,\n" +
				"\th.standard_bank_no AS bank_code,\n" +
				"\th.bank_card_no as bank_no,\n" +
				"  h.identity_code as id_number,\n" +
				"  h.lastmodify as lastmodify\n" +
				"FROM\n" +
				"\tsdb_b2c_member_hnapay h  WHERE h.verified = 'true' and lastmodify>"+lastlastmodify+"  limit "+start+","+pageSize;
		ResultSet rs = execute(sql);
		while (rs.next()) {
			CardBean	cardBean = new CardBean();
			cardBean.setUserId(rs.getString("userId"));
			cardBean.setMobile(rs.getString("mobile"));
			cardBean.setBankCode(rs.getString("bank_code"));
			cardBean.setCardNum(rs.getString("bank_no"));
			cardBean.setIdNumber(rs.getString("id_number"));
			cardBean.setName(rs.getString("user_name"));
			cardBean.setLastUpdate(rs.getLong("lastmodify"));
			result.add(cardBean);
		}
	}

	
	
	
	private  ResultSet execute(String sql) throws SQLException {
		Statement statement = con.createStatement();
		System.out.println("sql:"+sql);
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}

}
