package actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.opensymphony.xwork2.ActionSupport;

public class ImageUpload extends ActionSupport{
	private File upload = null;
	private String uploadContentType = "";
	private String uploadFileName = "";
	private String savePath = "";
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public String getUploadContentType() {
		return uploadContentType;
	}
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public String execute() throws Exception{
		String fileName = this.savePath + "/" + getUploadFileName();
		FileOutputStream fos = new FileOutputStream(fileName);
		FileInputStream fis = new FileInputStream(getUpload());
		byte[] b = new byte[1024];
		int len = 0;
		while((len=fis.read(b)) > 0) {
			fos.write(b, 0, len);
		}
		return "success";
	}
}
