package com.lzc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class SysOrg implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String org_id;

	private String parent_id;

	private String org_manage_type;
	
	private String org_code;

	private String org_name;

	private String short_name;

	private String eng_name;
	
	private String licence_code;
	
	private String licence_issuer;
		
	private String official_man_name;
	
	private String official_man_id;
	
	private String contact_man;
	
	private String contact_tel;
	
	private String contact_email;
	
	private String post_code;
	
	private String org_sort;

	private String img_logo_file;
	
	private String img_cover_file;

	private String area_id;

	private String address;

	private BigDecimal gps_jd;

	private BigDecimal gps_wd;

	private String reg_way;

	private Date reg_date;

	private Timestamp create_datetime;

	private String is_verify;
	
	private String is_valid;
	
	private String level_code;
	
	private String org_intro;
	
	private String org_desc;
	
	private String video_url;
	
	private Integer hot;
	
	private String class_id;
	
	private int aaa;
	
	

	public int getAaa() {
		return aaa;
	}

	public void setAaa(int aaa) {
		this.aaa = aaa;
	}

	public String getOrg_id() {
		return org_id;
	}

	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getOrg_manage_type() {
		return org_manage_type;
	}

	public void setOrg_manage_type(String org_manage_type) {
		this.org_manage_type = org_manage_type;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	public String getShort_name() {
		return short_name;
	}

	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}

	public String getEng_name() {
		return eng_name;
	}

	public void setEng_name(String eng_name) {
		this.eng_name = eng_name;
	}

	public String getOrg_code() {
		return org_code;
	}

	public void setOrg_code(String org_code) {
		this.org_code = org_code;
	}

	public String getLicence_code() {
		return licence_code;
	}

	public void setLicence_code(String licence_code) {
		this.licence_code = licence_code;
	}

	public String getLicence_issuer() {
		return licence_issuer;
	}

	public void setLicence_issuer(String licence_issuer) {
		this.licence_issuer = licence_issuer;
	}

	public String getOfficial_man_name() {
		return official_man_name;
	}

	public void setOfficial_man_name(String official_man_name) {
		this.official_man_name = official_man_name;
	}

	public String getOfficial_man_id() {
		return official_man_id;
	}

	public void setOfficial_man_id(String official_man_id) {
		this.official_man_id = official_man_id;
	}

	public String getContact_man() {
		return contact_man;
	}

	public void setContact_man(String contact_man) {
		this.contact_man = contact_man;
	}

	public String getContact_tel() {
		return contact_tel;
	}

	public void setContact_tel(String contact_tel) {
		this.contact_tel = contact_tel;
	}

	public String getContact_email() {
		return contact_email;
	}

	public void setContact_email(String contact_email) {
		this.contact_email = contact_email;
	}

	public String getPost_code() {
		return post_code;
	}

	public void setPost_code(String post_code) {
		this.post_code = post_code;
	}

	public String getOrg_sort() {
		return org_sort;
	}

	public void setOrg_sort(String org_sort) {
		this.org_sort = org_sort;
	}

	public String getImg_logo_file() {
		return img_logo_file;
	}

	public void setImg_logo_file(String img_logo_file) {
		this.img_logo_file = img_logo_file;
	}

	public String getImg_cover_file() {
		return img_cover_file;
	}

	public void setImg_cover_file(String img_cover_file) {
		this.img_cover_file = img_cover_file;
	}

	public String getArea_id() {
		return area_id;
	}

	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getGps_jd() {
		return gps_jd;
	}

	public void setGps_jd(BigDecimal gps_jd) {
		this.gps_jd = gps_jd;
	}

	public BigDecimal getGps_wd() {
		return gps_wd;
	}

	public void setGps_wd(BigDecimal gps_wd) {
		this.gps_wd = gps_wd;
	}

	public String getReg_way() {
		return reg_way;
	}

	public void setReg_way(String reg_way) {
		this.reg_way = reg_way;
	}

	public Date getReg_date() {
		return reg_date;
	}

	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}

	public Timestamp getCreate_datetime() {
		return create_datetime;
	}

	public void setCreate_datetime(Timestamp create_datetime) {
		this.create_datetime = create_datetime;
	}

	public String getIs_verify() {
		return is_verify;
	}

	public void setIs_verify(String is_verify) {
		this.is_verify = is_verify;
	}

	public String getIs_valid() {
		return is_valid;
	}

	public void setIs_valid(String is_valid) {
		this.is_valid = is_valid;
	}

	public String getLevel_code() {
		return level_code;
	}

	public void setLevel_code(String level_code) {
		this.level_code = level_code;
	}

	public String getOrg_intro() {
		return org_intro;
	}

	public void setOrg_intro(String org_intro) {
		this.org_intro = org_intro;
	}

	public String getOrg_desc() {
		return org_desc;
	}

	public void setOrg_desc(String org_desc) {
		this.org_desc = org_desc;
	}

	public String getVideo_url() {
		return video_url;
	}

	public void setVideo_url(String video_url) {
		this.video_url = video_url;
	}

	public Integer getHot() {
		return hot;
	}

	public void setHot(Integer hot) {
		this.hot = hot;
	}

	public String getClass_id() {
		return class_id;
	}

	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}

}
