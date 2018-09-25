package cn.com.wasec;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import cn.com.wasec.impl.ISymmetricEncryption;
import ltd.snowland.security.SM3;
import ltd.snowland.security.SM4;
import ltd.snowland.utils.NumberTool;
import it.unisa.dia.gas.jpbc.Element;
import java.util.Base64;
public class SM4Engine implements ISymmetricEncryption{
	private SM4 engine = null;
	public SM4Engine() {
//		if (engine == null) {
//			synchronized (SM4.class) {
//				if (engine == null)
					engine = new SM4();
//			}			
//		}
	}
	@Override
	public void crypto(int mode, InputStream is, OutputStream os, Element e) {
		generateSecretKeyFromElement(e);
		if (mode == Cipher.ENCRYPT_MODE) {
			byte[] b = null;
			try {
				b = is.readAllBytes();
				System.out.println("b:"+NumberTool.byteToHex(b));
				byte[] newb = engine.encryptData_ECB(b);
				System.out.println("key:"+engine.getSecretKey());
				System.out.println("newb:"+NumberTool.byteToHex(newb));
//				
//				for(int i = 0; i < newb.length; i++) {
//					if(newb[i] < 0)
//						newb[i] += 256;
//				}
				os.write(newb);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if(mode == Cipher.DECRYPT_MODE){
			byte[] b = null;
			try {
				b = is.readAllBytes();
				System.out.println("b:"+NumberTool.byteToHex(b));
//				for(int i = 0; i < b.length; i++) {
//					if(b[i] >= 128)
//						b[i] -= 256;
//				}
				System.out.println("key:"+engine.getSecretKey());
				byte[] newb = engine.decryptData_ECB(b);
				System.out.println("newb:"+NumberTool.byteToHex(newb));
				os.write(newb);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	private void generateSecretKeyFromElement(Element e) {
		System.out.println("e:" + e);
		byte[] b = e.toBytes();
		b = Arrays.copyOf(b, 16);
		try {
		String hash = SM3.hashHex(b);
		System.out.println("hash:"+hash);
		engine.setSecretKey(hash.substring(0, 16));
		} catch (Exception e2) {
			System.err.println("error in IO");
		}
//		return new SecretKey(b, "SM4");
	}
}
