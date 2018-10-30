package com.megvii.insight.service.finance;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EncryptTest {

  @Autowired
  StringEncryptor stringEncryptor;

  @Test
  public void encryptTest() {
    String result = stringEncryptor.encrypt("1a2s3d.qwe");
    System.out.println(result);
  }

  @Test
  public void decryptTest() {
    String result = "AyMqfrdCVVTxW04J+8xsbejxj8sME/Lg";
    result = stringEncryptor.decrypt(result);
    System.out.println(result);
  }

}
