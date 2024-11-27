/**
 * 
 */
package com.company.vo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BoardVO {
	private int idx;
	private String nickname;
	private String password;
	private String title;
	private String content;
	private String originalImagePath;
	private String realImagePath;
	
	private Timestamp postdate;
	private int pageview;
	private int download;
	
	private String formattedPostdate;
	
	public void setPostdate(Timestamp postdate) {
		this.postdate = postdate;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.formattedPostdate = dateFormat.format(postdate);
	}
	
	@SuppressWarnings("unused") // for invalidating direct setter
	private void setFormattedPostdate(String ignored) {
		// to prevent direct setting of formatted postdate
	}
}
