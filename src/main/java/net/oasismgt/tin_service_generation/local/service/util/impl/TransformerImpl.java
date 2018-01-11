
package net.oasismgt.tin_service_generation.local.service.util.impl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.stereotype.Service;

import net.oasismgt.tin_service_generation.local.service.util.Transformer;



@Service
public class TransformerImpl implements Transformer{
		
	private static final long serialVersionUID = -1422154351983641381L;

	@Override
	public String getHash(String bata) {
		String newHash = null;
		    try {
		    	 MessageDigest md = MessageDigest.getInstance("SHA-512");
		         byte[] bytes = md.digest(bata.getBytes("UTF-8"));
		         StringBuilder sb = new StringBuilder();
		         for(int i=0; i< bytes.length ;i++){
		            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		         }		         
		         newHash = sb.toString();
		        } 
		       catch (NoSuchAlgorithmException |UnsupportedEncodingException e){
		        e.printStackTrace();
		       }
		    return newHash;
	}
}
