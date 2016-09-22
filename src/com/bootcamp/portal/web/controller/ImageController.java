package com.bootcamp.portal.web.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bootcamp.portal.mgr.annotation.PermissionRequired;

@Controller
@PermissionRequired
public class ImageController {

	protected static final Logger LOGGER = Logger
			.getLogger(LotController.class);
	
	@RequestMapping(value = "image/{name:.+}", method = RequestMethod.GET, produces = "image/jpg")
	public @ResponseBody byte[] getImageByLotId(@PathVariable("name") String name)
			{

	    try {
	       
	        // Prepare buffered image.
	        BufferedImage img = ImageIO.read(new FileInputStream("C:/SpeedSell/git/SpeedSell/bootcamp/web/r/images/lots/"+name));
	        // Create a byte array output stream.
	        ByteArrayOutputStream bao = new ByteArrayOutputStream();

	        // Write to output stream
	        ImageIO.write(img, "jpg", bao);

	        return bao.toByteArray();
	    } catch (FileNotFoundException e) {
	    	//e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
	    } 
	    return null;
	}
	
	@RequestMapping(value = "lotdata/images", method = RequestMethod.POST, consumes = {"multipart/form-data"})
	public ResponseEntity<String> uploadImg(@RequestParam("file") MultipartFile file){
		
		if (!file.isEmpty()) {
			try {
				String fileName = file.getOriginalFilename();;
								
				String dirPath = "C:/SpeedSell/git/SpeedSell/bootcamp/web/r/images/lots/";
								
                byte[] bytes = file.getBytes();
                BufferedOutputStream buffStream = 
                        new BufferedOutputStream(new FileOutputStream(new File(dirPath, fileName)));
                buffStream.write(bytes);
                buffStream.close();
                //path is file name
                return new ResponseEntity<String>(("{\"path\":\""+fileName+"\"}"), HttpStatus.OK);
            } catch (Exception e) {
            	e.printStackTrace();
            	return null;
            }
        } 
		return null;
	}
	
}
