package com.coursera.capstone.data;


public class Token {

	
	private String issued_to;
	private String audience;
	private String user_id;
	private String scope;
	private int expires_in;
	private String email;
	private boolean verified_email;
	private String access_type;
	
	
	
	public Token(){
	
	}
	
	
	public Token(String issued_to, String audience, String user_id,
			String scope, int expires_in, String email, boolean verified_email,
			String access_type) {
		super();
		this.issued_to = issued_to;
		this.audience = audience;
		this.user_id = user_id;
		this.scope = scope;
		this.expires_in = expires_in;
		this.email = email;
		this.verified_email = verified_email;
		this.access_type = access_type;
	}


	public String getIssued_to() {
		return issued_to;
	}


	public String getAudience() {
		return audience;
	}


	public String getUser_id() {
		return user_id;
	}


	public String getScope() {
		return scope;
	}


	public int getExpires_in() {
		return expires_in;
	}


	public String getEmail() {
		return email;
	}


	public boolean isVerified_email() {
		return verified_email;
	}


	public String getAccess_type() {
		return access_type;
	}


	public void setIssued_to(String issued_to) {
		this.issued_to = issued_to;
	}


	public void setAudience(String audience) {
		this.audience = audience;
	}


	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}


	public void setScope(String scope) {
		this.scope = scope;
	}


	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public void setVerified_email(boolean verified_email) {
		this.verified_email = verified_email;
	}


	public void setAccess_type(String access_type) {
		this.access_type = access_type;
	}
	
	
	
	
	
	
	
	
	
	
	
}
