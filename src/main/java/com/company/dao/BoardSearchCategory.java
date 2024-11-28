/**
 * 
 */
package com.company.dao;

/**
 * @author 04-14
 *
 */
public enum BoardSearchCategory {
	// View-field("DB-field")
	Title("title"),Nickname("nickname"),Content("content");
	
	private String category;
	private BoardSearchCategory(String category) {
		this.category = category;
	}
	
	@Override
	public String toString() {
		return category;
	}
	public static BoardSearchCategory fromString(String string) {
		if(string == null) return null;
		switch(string.toLowerCase()) {
		case "title": return Title;
		case "nickname": return Nickname;
		case "content": return Content;
		default: return null;
		}
	}
}
