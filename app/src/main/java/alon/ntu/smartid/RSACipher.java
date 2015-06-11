package alon.ntu.smartid;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSACipher {

	private PublicKey pubKey;
	private PrivateKey privKey;
	
	public RSACipher() throws NoSuchAlgorithmException {
		KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
		
		gen.initialize(128);
		
		KeyPair pair = gen.generateKeyPair();
		
		pubKey = (RSAPublicKey) pair.getPublic();
		privKey = (RSAPrivateKey) pair.getPrivate();
		
	}
	
	public RSACipher(String pubSting, String privString, String modulusString) throws NoSuchAlgorithmException {
		
		BigInteger publicExponent = new BigInteger(pubSting, 16);
		BigInteger privateExponent = new BigInteger(privString, 16);
		BigInteger modulus = new BigInteger(modulusString, 16);
		
		try {
			pubKey = KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(modulus, privateExponent));
			privKey = KeyFactory.getInstance("RSA").generatePrivate(new RSAPrivateKeySpec(modulus, publicExponent));
			
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
	}
	
	public byte[] getPublicKey() {
		return pubKey.getEncoded();
	}
	
	public byte[] getPrivateKey() {
		return privKey.getEncoded();
	}
	
	public String getKeyString(RSAKey key) {
		return new String(key.getModulus().toString(16));
	}
	
	public String getPublicKeyString() {
		return ((RSAPublicKey) pubKey).getPublicExponent().toString(16);
	}
	
	public String getPrivateKeyString() {
		return ((RSAPrivateKey) privKey).getPrivateExponent().toString(16);
	}
	
	public String getModulusString() {
		return ((RSAPublicKey) pubKey).getModulus().toString(16);
	}
	
	public byte[] encrypt(byte[] b) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		
		return cipher.doFinal(b);
	}
	
	public byte[] decrypt(byte[] b) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privKey);
		
		return cipher.doFinal(b);
	}
}
