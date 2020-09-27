package com.commercial.webController;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.lowagie.text.pdf.codec.Base64.InputStream;

@Controller
@SessionAttributes("user")

public class display_img {

	public display_img() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/display_img", produces="image/jpeg")
	public void display_img(HttpServletRequest request, HttpServletResponse response) {
		
String link = request.getParameter("link");
		
		//System.out.println(link);
		
	      File file = new File(link);
	      

	      if(!file.exists())
	    	  file = new File("D:\\Vehicule\\no_img.png");
	    	  
	    	  
	    	  
	    	  response.setContentLength((int)file.length());

		      FileInputStream in = null;
			try {
				in = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      ServletOutputStream out = null;
			try {
				out = response.getOutputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      
		      response.setContentType("image/jpeg");
		    
		      // Copy the contents of the file to the output stream
		       byte[] buf = new byte[1024];
		       int count = 0;
		       try {
				while ((count = in.read(buf)) >= 0) {
				     out.write(buf, 0, count);
				  }
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		       	       
		       try {
				response.getOutputStream().write(buf);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   
			    try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	}
	
}
