package it.vo;

public class Condition {
	private String pname;
	private String cid;
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getIs_hot() {
		return is_hot;
	}
	public void setIs_hot(String is_hot) {
		this.is_hot = is_hot;
	}
	private String is_hot;
}
