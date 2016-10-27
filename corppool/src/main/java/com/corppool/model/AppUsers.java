package com.corppool.model;

import java.sql.Date;

public class AppUsers {

	private String idappusers;
	private String uid;
	private String userid;
	private String password;
	
	private String usertype;
	private Date lastaccesseddate;
	private String corpemail;
	private String iscorpemailverified;
	
	private String isactive;
	private String islocked;
	private String lockedreason;
	private String lastaccessdevice;
	
	private Date datecreated;
	private Date datemodified;
	private String createdby;
	private String modifiedby;
	public String getIdappusers() {
		return idappusers;
	}
	public void setIdappusers(String idappusers) {
		this.idappusers = idappusers;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public Date getLastaccesseddate() {
		return lastaccesseddate;
	}
	public void setLastaccesseddate(Date lastaccesseddate) {
		this.lastaccesseddate = lastaccesseddate;
	}
	public String getCorpemail() {
		return corpemail;
	}
	public void setCorpemail(String corpemail) {
		this.corpemail = corpemail;
	}
	public String getIscorpemailverified() {
		return iscorpemailverified;
	}
	public void setIscorpemailverified(String iscorpemailverified) {
		this.iscorpemailverified = iscorpemailverified;
	}
	public String getIsactive() {
		return isactive;
	}
	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}
	public String getIslocked() {
		return islocked;
	}
	public void setIslocked(String islocked) {
		this.islocked = islocked;
	}
	public String getLockedreason() {
		return lockedreason;
	}
	public void setLockedreason(String lockedreason) {
		this.lockedreason = lockedreason;
	}
	public String getLastaccessdevice() {
		return lastaccessdevice;
	}
	public void setLastaccessdevice(String lastaccessdevice) {
		this.lastaccessdevice = lastaccessdevice;
	}
	public Date getDatecreated() {
		return datecreated;
	}
	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}
	public Date getDatemodified() {
		return datemodified;
	}
	public void setDatemodified(Date datemodified) {
		this.datemodified = datemodified;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public String getModifiedby() {
		return modifiedby;
	}
	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}
	
	
	
}
