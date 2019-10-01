package com.lzc.model;

import java.io.Serializable;
import java.sql.Timestamp;

//@Table(name="sys_user")
public class SysUser implements Serializable {

	private static final long serialVersionUID = 1L;
	
    //@Id
	private String user_id;
    
	private String org_id;
	
	private String user_type;
	
	private String login_name;
	
	private String user_email;
	
	private String user_name;
	
	private String nick_name;
	
	private String user_logo;
	
	private String user_tel;
	
	private Timestamp login_datetime;
	
	private Timestamp create_datetime;
	
	private String is_valid;
	
    
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getOrg_id() {
		return org_id;
	}

	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public String getLogin_name() {
		return login_name;
	}

	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public String getUser_logo() {
		return user_logo;
	}

	public void setUser_logo(String user_logo) {
		this.user_logo = user_logo;
	}

	public String getUser_tel() {
		return user_tel;
	}

	public void setUser_tel(String user_tel) {
		this.user_tel = user_tel;
	}

	public Timestamp getLogin_datetime() {
		return login_datetime;
	}

	public void setLogin_datetime(Timestamp login_datetime) {
		this.login_datetime = login_datetime;
	}

	public Timestamp getCreate_datetime() {
		return create_datetime;
	}

	public void setCreate_datetime(Timestamp create_datetime) {
		this.create_datetime = create_datetime;
	}

	public String getIs_valid() {
		return is_valid;
	}

	public void setIs_valid(String is_valid) {
		this.is_valid = is_valid;
	}

}
