package com.example.epamfinalproject.Utility;

import com.lambdaworks.crypto.SCryptUtil;

/** Utility class to encrypt password. Uses the SCryptUtil algorithm to hash passwords */
public class Encryptor {

  private Encryptor() {}

  /**
   * @param password string value from User's Registration and Login Forms
   * @return hashed password
   */
  public static String encrypt(String password) {
    return SCryptUtil.scrypt(password, 16, 16, 16);
  }

  /**
   * @param hash the hashed password against which the new one will be compared
   * @param password unhashed password to be compared
   * @return identity check result (true or false)
   */
  public static boolean check(String hash, String password) {
    return SCryptUtil.check(password, hash);
  }
}
