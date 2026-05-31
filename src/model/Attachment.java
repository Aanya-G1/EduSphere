package model;

public class Attachment {
	private int attachmentId;
    private int postId;
    private String fileName;
    private String filePath;
    public Attachment() {}
	public Attachment(int attachmentId, int postId, String fileName, String filePath) {
		this.attachmentId = attachmentId;
		this.postId = postId;
		this.fileName = fileName;
		this.filePath = filePath;
	}
	public int getAttachmentId() {
		return attachmentId;
	}
	public void setAttachmentId(int attachmentId) {
		this.attachmentId = attachmentId;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	@Override
	public String toString() {
	    return this.fileName; 
	}
    
}
