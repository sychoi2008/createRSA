import java.math.BigInteger;

public class RSA {
    public static int p=13;
    public static int q=231;
    public static int n=p*q;
    public static int pieN=(p-1)*(q-1);


    public static int gcd(int a, int b) {
        if(b==0) return a;
        else return gcd(b, a%b);
    }

    public static int publicKey() {
        //public key는 반드시 pieN과 서로소여야 한다. 서로소 : 최대공약수가 1인 관계
        int e = 2;
        while(e<pieN) {
            if(gcd(pieN, e) != 1) {
                e++;
                continue;
            }
            else {
                return e;
            }
        }
        return e;
    }

    public static int privateKey(int e) {
        //확장 유클리드 알고리즘 사용
        int r1 = pieN;
        int r2 = e;

        int t1 = 0;
        int t2 = 1;
        int r,t,q;

        while(r2 != 0) {
            q = r1 / r2;
            r = r1%r2;
            r1 = r2;
            r2 = r;

            t = t1 - q * t2;
            t1 = t2;
            t2 = t;

        }
        r = r1;
        t = t1;

        //드디어! 최대공약수가 1일 때. inverse가 존재
        if(r == 1) {
            if(t<0) { //음수가 나오는 경우도 존재함. 그럴때는 mod n의 n을 더해준다. https://kimtaehyun98.tistory.com/110
                t += pieN;
            }
            return t;
        } else { //inverse가 존재하지 않을 때
            return 0;
        }
    }

    public static String encrypt(String plainText, int e) {
        //plaintext를 char배열에 넣어준다.
        char[] charsPlain = plainText.toCharArray();
        //아스키코드 번호에 RSA연산을 해준 값들을 넣어줄 int 배열
        long [] encryptAscii = new long[charsPlain.length];
        char[] encryptString = new char[charsPlain.length];
        for(int i=0; i<charsPlain.length; i++) {
            long num = charsPlain[i];
            //System.out.println("plaintext일 때 : "+ num);
            encryptAscii[i] = ((long) Math.pow(num, e)) % n;
            //System.out.println("연산 후 : " + encryptAscii[i]);
            encryptString[i] = (char) encryptAscii[i];
        }
        return new String(encryptString);
    }

    public static String decrypt(String cipherText, int d) {
        char[] cipherChar = cipherText.toCharArray();
        long [] asciiNum = new long[cipherChar.length];
        char[] decryptString = new char[cipherChar.length];

        for(int i=0; i<cipherChar.length; i++) {
            long num = cipherChar[i];
            //System.out.println("CipherText일 때 : "+num);
            BigInteger bigNumber1 = BigInteger.valueOf(num);
            BigInteger bigNumber2 = BigInteger.valueOf(n);
            BigInteger bigPow = bigNumber1.pow(d);

            //asciiNum[i] = bigPow.remainder(bigNumber2);
            BigInteger originChar = bigPow.remainder(bigNumber2);
            long chOriginChar = originChar.longValue();
            asciiNum[i] = chOriginChar;
            //System.out.println("연산 후 : "+asciiNum[i]);

            decryptString[i] = (char) asciiNum[i];
        }
        return new String(decryptString);


    }


}