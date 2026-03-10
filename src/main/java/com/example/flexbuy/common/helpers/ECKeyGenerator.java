package com.example.flexbuy.common.helpers;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.util.Base64;


public class ECKeyGenerator {
    public static void main(String[] args) throws Exception{
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
        keyGen.initialize(256);
        KeyPair keyPair = keyGen.generateKeyPair();

        ECPrivateKey privateKey =(ECPrivateKey) keyPair.getPrivate();
        ECPublicKey publicKey = (ECPublicKey) keyPair.getPublic();

        System.out.println("Private Keys: (Base64):"); //in production dont use base64
        System.out.println(Base64.getEncoder().encodeToString(privateKey.getEncoded()));

        System.out.println("public Keys: (Base64):");
        System.out.println(Base64.getEncoder().encodeToString(publicKey.getEncoded()));

    }
}
