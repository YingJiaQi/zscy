/**
 * Copyright 2014 the original author or authors. All rights reserved.
 */
package com.syard.common.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

/**
 * @author Administrator
 * @since 0.0.1
 *
 */
public class ByteDataSource implements DataSource{

	private byte[] filebyte=null;
    private String filetype="application/octet-stream";
    private String filename="";
    private OutputStream outputstream=null;
    private InputStream  inputstream=null;
    public ByteDataSource() {
    }
    public ByteDataSource(String FileName) {
     File f=new File(FileName);
      filename=f.getName();
      try {
	      inputstream = new FileInputStream(f);
	      inputstream.read(filebyte);
	  } catch (Exception e) {
		  
	  }
    }

    public ByteDataSource(byte[] filebyte,String displayfilename) {
        this.filebyte=filebyte;
        this.filename=displayfilename;
    }
    public String getContentType() {
        return filetype;
    }

    public InputStream getInputStream() throws IOException {
     InputStream input=new ByteArrayInputStream(filebyte); 
        return input;
    }

    public String getName() {
        return filename;
    }

    public OutputStream getOutputStream() throws IOException {
     
     outputstream.write(filebyte);
        return outputstream;
    }

}
