package org.apache.gobblin.password;

public interface IPasswordManager {

	/**
	   * Encrypt a password. A master password must have been provided in the constructor.
	   * @param plain A plain password to be encrypted.
	   * @return The encrypted password.
	   */
	String encryptPassword(String plain);

	/**
	   * Decrypt an encrypted password. A master password file must have been provided in the constructor.
	   * @param encrypted An encrypted password.
	   * @return The decrypted password.
	   */
	String decryptPassword(String encrypted);

	/**
	   * Decrypt a password if it is an encrypted password (in the form of ENC(.*))
	   * and a master password file has been provided in the constructor.
	   * Otherwise, return the password as is.
	   */
	String readPassword(String password);

}