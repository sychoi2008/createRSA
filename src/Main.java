import java.util.Base64;

public class Main {
    public static void main(String[] args) {
        int publicKey = RSA.publicKey();
        System.out.println("public key : "+ publicKey);
        int privateKey = RSA.privateKey(publicKey);
        System.out.println("private key : "+ privateKey);
        String plainText = "Hello World";
        System.out.println("Plain Text : "+plainText);
        String cipherText = RSA.encrypt(plainText, publicKey);
        System.out.println("Cipher Text : "+ cipherText);
        System.out.println("----------------------");
        String originalText = RSA.decrypt(cipherText, privateKey);
        System.out.println("복호화 : "+originalText);

    }
}