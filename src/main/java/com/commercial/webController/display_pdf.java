package com.commercial.webController;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
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

public class display_pdf {

	public display_pdf() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	@RequestMapping(value="/display_pdf", produces="application/pdf")
	public void display_ticket(HttpServletRequest request, HttpServletResponse response) {
		
		String file = request.getParameter("file");
		/*
		System.out.println("file == "+file);
		System.out.println("file_rep == "+file);
		*/
		response.setContentType("application/pdf");
	    InputStream inputStream = null;
	    ServletOutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    File fichier = new File(file);
	    
	    try{
	    	BufferedInputStream in = new BufferedInputStream(new FileInputStream(fichier));
			byte[] buffer = new byte[1024];
			int read = in.read(buffer, 0, buffer.length);
			while (read != -1) {
				outputStream.write(buffer, 0, read);
				read = in.read(buffer, 0, buffer.length);
			}
			in.close();
			outputStream.flush();
			outputStream.close();
	    }catch(IOException ioException){
	        //Do something or propagate up..
	    }finally{
	        IOUtils.closeQuietly(inputStream);
	        IOUtils.closeQuietly(outputStream);
	    }
		
	}
	
}
