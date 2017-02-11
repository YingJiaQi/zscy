package com.syard.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * 链接SFTP服务器工具类
 * @author huwk
 *
 */
public class SFTPUtils {
	private static final Logger log = LoggerFactory.getLogger(SFTPUtils.class);
	public static SFTPUtils getInstance(){
		return new SFTPUtils();
	}
	
	private SFTPUtils(){
		
	}
	
	public ChannelSftp operateSftp(){
		String host = PropsUtil.get("sftp.host");
		String port = PropsUtil.get("sftp.port");
	    String username = PropsUtil.get("sftp.username");
	    String password = PropsUtil.get("sftp.password");
		ChannelSftp sftp = connect(host, Integer.parseInt(port), username, password);
		return sftp;
	}
	

	private static ChannelSftp connect(String host,int port,String username,String password){
	
		ChannelSftp sftp = null;
		
		try {
			JSch jsch = new JSch();
			jsch.getSession(username, host, port);
			Session sshSession = jsch.getSession(username, host, port);
			
			System.out.println("session ceated");
			sshSession.setPassword(password);
		    Properties sshConfig = new Properties();
		    sshConfig.put("StrictHostKeyChecking", "no");
		    sshSession.setTimeout(6000);
		    sshSession.setConfig(sshConfig);
		    sshSession.connect();
		    System.out.println("session connected");
		    System.out.println("Opening Channel");
		    Channel channel = sshSession.openChannel("sftp");
		    channel.connect();
		    
		    
		    sftp = (ChannelSftp)channel;
		    System.out.println("Connected to "+host+" .");
			System.out.println(new Date());
		} catch (JSchException e) {
			e.printStackTrace();
		}
		return sftp;
	}
	
	/**
	 * 上传文件
	 * @param directory 上传的目录
	 * @param uploadFile 要上传的文件
	 * @param sftp
	 */
	public void upload(String directory,ChannelSftp sftp,File... files){
		
		try {
			sftp.cd(directory);
		    if(files != null && files.length>0){
		    	for(File uploadFile:files){
		    		System.out.println(uploadFile.getName());
					sftp.put(new FileInputStream(uploadFile),uploadFile.getName());
		    	}
	    	}
		} catch (SftpException e) {
			log.error("sftp上传异常："+e.getMessage());
		} catch (FileNotFoundException e) {
			log.error("sftp上传文件没有发现"+e.getMessage());
		}
		
	}
	
	/**
	 * 下载文件
	 * @param directory 下载目录
	 * @param downloadFile 下载的文件
	 * @param saveFile 存在本地的路径
	 * @param sftp
	 */
	public static void download(String directory,String saveFile,ChannelSftp sftp,String... downloadFiles){
		try {
			sftp.cd(directory);
			
			if(downloadFiles != null && downloadFiles.length>0){
				for(String downloadFile:downloadFiles){
					/*File file = new File(saveFile+ downloadFile);
					if(!file.exists()){
						file.createNewFile();
					}*/
					File file = new File(saveFile);
					if(!file.exists()   && !file.isDirectory()){
						file.mkdirs();
					}
					File file1 = new File(saveFile+ downloadFile);
					if(!file1.exists()){
						file1.createNewFile();
					}
					sftp.get(downloadFile,new FileOutputStream(file1));
				}
			}
		} catch (SftpException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	}
	
	/*public static void main(String[] args) {
		ChannelSftp sftp = connect("27.115.119.188", 22, "healthcare_ftp", "Danone12345");
		File file = new File("D:/export/");
		File[] files = file.listFiles();
		
		
	}*/
}
