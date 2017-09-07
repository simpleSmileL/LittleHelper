package bean;

public class NewsItem {

	public String title;
	private String desc;
	private String image;
	private String type;
	private int comment;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getComment() {
		return comment;
	}
	public void setComment(int comment) {
		this.comment = comment;
	}
	@Override
	public String toString() {
		return "NewsItem [title=" + title + ", desc=" + desc + ", image="
				+ image + ", type=" + type + ", comment=" + comment + "]";
	}
	
	
}
