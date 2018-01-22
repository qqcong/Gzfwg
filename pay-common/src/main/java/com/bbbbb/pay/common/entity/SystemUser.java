package com.bbbbb.pay.common.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;


@Data
@Entity(name="system_user")
public class SystemUser {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="create_date")
	private Timestamp createDate;

	@Column(name="last_login")
	private Timestamp lastLogin;

	private String password;

	private String status;
	
	private String salt;

	@Column(name="user_name")
	private String userName;

}