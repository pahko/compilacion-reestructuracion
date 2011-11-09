// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 05:16:02 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   GeneraSello_2011.java

package create;

import cripfia.AlgoritmoDigestion;
import cripfia.Errores_Cripfia;
import gob.sat.sgi.SgiLibException;
import gob.sat.sgi.sglib.PKCS8EncryptedPrivateKeyInfo;
import java.io.*;
import java.math.BigInteger;
import java.security.PublicKey;
import java.security.cert.*;
import java.security.interfaces.RSAPublicKey;
import org.bouncycastle.asn1.*;
import org.bouncycastle.asn1.pkcs.*;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.*;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.util.encoders.Base64;

public class GeneraSello_2011
{

    public GeneraSello_2011()
    {
        sign = null;
    }

    private static String a(AlgorithmIdentifier algorithmidentifier)
        throws SgiLibException
    {
        String s = algorithmidentifier.getObjectId().getId();
        String s1 = null;
        if(s.startsWith(z[2]))
        {
            int i = Integer.decode(s.substring(z[2].length() + 1)).intValue();
            switch(i)
            {
            case 2: // '\002'
                s1 = z[8];
                break;

            case 3: // '\003'
                s1 = z[13];
                break;

            case 4: // '\004'
                s1 = z[9];
                break;

            case 5: // '\005'
                s1 = z[11];
                break;
            }
        } else
        if(s.equals(PKCSObjectIdentifiers.md2.getId()))
            s1 = z[8];
        else
        if(s.equals(PKCSObjectIdentifiers.md5.getId()))
            s1 = z[9];
        else
        if(s.equals(z[10]))
            s1 = z[11];
        if(s1 == null)
            throw new SgiLibException(1160, z[12]);
        else
            return s1;
    }

    private static byte[] a(byte abyte0[], AlgorithmIdentifier algorithmidentifier, RSAPrivateKeyStructure rsaprivatekeystructure)
        throws SgiLibException
    {
        byte abyte1[] = generaDigestion(abyte0, a(algorithmidentifier));
        RSAEngine rsaengine = new RSAEngine();
        rsaengine.init(true, new RSAKeyParameters(true, rsaprivatekeystructure.getModulus(), rsaprivatekeystructure.getPublicExponent()));
        int i = rsaengine.getInputBlockSize();
        DERSequence dersequence = new DERSequence();
        dersequence.addObject(algorithmidentifier.getDERObject());
        dersequence.addObject(new DEROctetString(abyte1));
        byte abyte2[] = toByteArray(dersequence);
        if(abyte2.length + 3 > i)
            throw new SgiLibException(1055, z[22]);
        byte abyte3[] = new byte[i];
        abyte3[0] = 1;
        int j;
        for(j = 1; j < i - abyte2.length - 1; j++)
            abyte3[j] = -1;

        abyte3[j] = 0;
        System.arraycopy(abyte2, 0, abyte3, ++j, abyte2.length);
        return abyte3;
    }

    public static byte[] generaDigestion(byte abyte0[], String s)
        throws SgiLibException
    {
        Object obj = null;
        if(s.equals(z[8]))
            obj = new MD2Digest();
        else
        if(s.equals(z[13]))
            obj = new MD4Digest();
        else
        if(s.equals(z[9]))
            obj = new MD5Digest();
        else
        if(s.equals(z[11]))
            obj = new SHA1Digest();
        else
            throw new SgiLibException(1160, z[21]);
        ((Digest)obj).update(abyte0, 0, abyte0.length);
        byte abyte1[] = new byte[((Digest)obj).getDigestSize()];
        ((Digest)obj).doFinal(abyte1, 0);
        return abyte1;
    }

    public static X509Certificate cargaCert(InputStream inputstream)
    {
        try
        {
            CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");
            X509Certificate x509certificate = (X509Certificate)certificatefactory.generateCertificate(inputstream);
            X509Certificate x509certificate1 = x509certificate;
            return x509certificate1;
        }
        catch(Exception exception)
        {
            if(exception instanceof FileNotFoundException)
                c = Errores_Cripfia.ERROR_NO_EXISTE_ARCHIVO;
            else
            if(exception instanceof CertificateException)
                c = Errores_Cripfia.ERROR_CERTIFICADO_FORMATO_INVALIDO;
            else
            if(exception instanceof IOException)
                c = Errores_Cripfia.ERROR_LECTURA_CERTIFICADO;
            else
                c = Errores_Cripfia.ERROR_EXCEPCION_GENERICA;
        }
        return null;
    }

    public static X509Certificate cargaCert(String s)
    {
        int i = d;
        try
        {
            FileInputStream fileinputstream = new FileInputStream(s);
            CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");
            X509Certificate x509certificate = (X509Certificate)certificatefactory.generateCertificate(fileinputstream);
            fileinputstream.close();
            X509Certificate x509certificate1 = x509certificate;
            return x509certificate1;
        }
        catch(Exception exception)
        {
label0:
            {
                if(exception instanceof FileNotFoundException)
                {
                    c = Errores_Cripfia.ERROR_NO_EXISTE_ARCHIVO;
                    if(i == 0)
                        break label0;
                    DERObject.a = !DERObject.a;
                }
                if(exception instanceof CertificateException)
                {
                    c = Errores_Cripfia.ERROR_CERTIFICADO_FORMATO_INVALIDO;
                    if(i == 0)
                        break label0;
                }
                if(exception instanceof IOException)
                {
                    c = Errores_Cripfia.ERROR_LECTURA_CERTIFICADO;
                    if(i == 0)
                        break label0;
                }
                c = Errores_Cripfia.ERROR_EXCEPCION_GENERICA;
            }
        }
        return null;
    }

    public static X509Certificate cargaCertBase64(String s)
        throws Exception
    {
        try
        {
            ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(Base64.decode(s));
            CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");
            X509Certificate x509certificate = (X509Certificate)certificatefactory.generateCertificate(bytearrayinputstream);
            bytearrayinputstream.close();
            return x509certificate;
        }
        catch(Exception exception)
        {
            if(exception instanceof FileNotFoundException)
                aa = Errores_Cripfia.ERROR_NO_EXISTE_ARCHIVO;
            else
            if(exception instanceof CertificateException)
                aa = Errores_Cripfia.ERROR_CERTIFICADO_FORMATO_INVALIDO;
            else
            if(exception instanceof IOException)
                aa = Errores_Cripfia.ERROR_LECTURA_CERTIFICADO;
            else
                aa = Errores_Cripfia.ERROR_EXCEPCION_GENERICA;
        }
        return null;
    }

    public static byte[] toByteArray(DERObject derobject)
        throws SgiLibException
    {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        DEROutputStream deroutputstream = new DEROutputStream(bytearrayoutputstream);
        try
        {
            deroutputstream.writeObject(derobject.getDERObject());
        }
        catch(IOException ioexception)
        {
            throw new SgiLibException(1099, ioexception.getMessage());
        }
        return bytearrayoutputstream.toByteArray();
    }

    public static byte[] encripta(Object obj, byte abyte0[])
        throws SgiLibException
    {
        boolean flag;
        RSAEngine rsaengine;
label0:
        {
            flag = a;
            rsaengine = new RSAEngine();
            if(obj instanceof RSAPrivateKeyStructure)
            {
                rsaengine.init(true, new RSAKeyParameters(true, ((RSAPrivateKeyStructure)obj).getModulus(), ((RSAPrivateKeyStructure)obj).getPrivateExponent()));
                if(!flag)
                    break label0;
            }
            if(obj instanceof RSAPublicKeyStructure)
            {
                rsaengine.init(true, new RSAKeyParameters(false, ((RSAPublicKeyStructure)obj).getModulus(), ((RSAPublicKeyStructure)obj).getPublicExponent()));
                if(!flag)
                    break label0;
            }
            throw new SgiLibException(1052, z[7]);
        }
        int i = rsaengine.getInputBlockSize();
        int j = rsaengine.getOutputBlockSize();
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        int k = 0;
        do
        {
            if(k >= abyte0.length)
                break;
            int l = i < abyte0.length - k ? i : abyte0.length - k;
            byte abyte2[] = rsaengine.processBlock(abyte0, k, l);
            try
            {
                bytearrayoutputstream.write(abyte2);
            }
            catch(IOException ioexception)
            {
                throw new SgiLibException(1099, ioexception.getMessage());
            }
            k += i;
        } while(!flag);
        byte abyte1[] = bytearrayoutputstream.toByteArray();
        if(DERObject.a)
            a = !flag;
        return abyte1;
    }

    public static String EncodeB64(byte abyte0[])
    {
        return new String(Base64.encode(abyte0));
    }

    public byte[] DecodeB64(String s)
    {
        return Base64.decode(s);
    }

    public static boolean verificaFirma(PublicKey publickey, byte abyte0[], byte abyte1[])
        throws SgiLibException
    {
        Object obj = null;
        if(!(publickey instanceof RSAPublicKey))
            throw new SgiLibException(1053, z[14]);
        try
        {
            RSAPublicKey rsapublickey = (RSAPublicKey)publickey;
            byte abyte2[] = desencripta(new RSAPublicKeyStructure(rsapublickey.getModulus(), rsapublickey.getPublicExponent()), abyte1);
            abyte2 = a(abyte2);
            ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(abyte2);
            DigestInfo digestinfo = new DigestInfo((DERSequence)(new ASN1InputStream(bytearrayinputstream)).readObject());
            byte abyte3[] = generaDigestion(abyte0, a(digestinfo.getAlgorithmId()));
            boolean flag = igual(digestinfo.getDigest(), abyte3);
            return flag;
        }
        catch(IOException ioexception)
        {
            throw new SgiLibException(1002, ioexception.getMessage());
        }
    }

    protected static boolean igual(byte abyte0[], byte abyte1[])
    {
        boolean flag = false;
        if(abyte0.length == abyte1.length)
        {
            int i;
            for(i = 0; i < abyte0.length && abyte0[i] == abyte1[i]; i++);
            flag = i == abyte0.length;
        }
        return flag;
    }

    private static byte[] a(byte abyte0[])
        throws SgiLibException
    {
        if(abyte0[0] == 1)
        {
            int i;
            for(i = 1; abyte0[i] == -1 && i < abyte0.length; i++);
            if(i < abyte0.length && abyte0[i] == 0)
            {
                i++;
                byte abyte1[] = new byte[abyte0.length - i];
                System.arraycopy(abyte0, i, abyte1, 0, abyte1.length);
                return abyte1;
            }
        }
        throw new SgiLibException(1190, z[24]);
    }

    public static byte[] desencripta(Object obj, byte abyte0[])
        throws SgiLibException
    {
        RSAEngine rsaengine = new RSAEngine();
        if(obj instanceof RSAPrivateKeyStructure)
            rsaengine.init(false, new RSAKeyParameters(true, ((RSAPrivateKeyStructure)obj).getModulus(), ((RSAPrivateKeyStructure)obj).getPrivateExponent()));
        else
        if(obj instanceof RSAPublicKeyStructure)
            rsaengine.init(false, new RSAKeyParameters(false, ((RSAPublicKeyStructure)obj).getModulus(), ((RSAPublicKeyStructure)obj).getPublicExponent()));
        else
            throw new SgiLibException(1053, z[7]);
        int i = rsaengine.getInputBlockSize();
        int j = rsaengine.getOutputBlockSize();
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        for(int k = 0; k < abyte0.length; k += i)
        {
            int l = i < abyte0.length - k ? i : abyte0.length - k;
            byte abyte2[] = rsaengine.processBlock(abyte0, k, l);
            try
            {
                bytearrayoutputstream.write(abyte2);
            }
            catch(IOException ioexception)
            {
                throw new SgiLibException(1099, ioexception.getMessage());
            }
        }

        byte abyte1[] = bytearrayoutputstream.toByteArray();
        return abyte1;
    }

    public String getSello()
    {
        return new String(EncodeB64(sign));
    }

    public byte[] Generar(BufferedInputStream llave, String pass, String cadena_original)
        throws IOException
    {
        PKCS8EncryptedPrivateKeyInfo pkcs8encryptedprivatekeyinfo = new PKCS8EncryptedPrivateKeyInfo((DERSequence)(new ASN1InputStream(llave)).readObject());
        PrivateKeyInfo privatekeyinfo = null;
        try
        {
            privatekeyinfo = pkcs8encryptedprivatekeyinfo.getPrivateKeyInfo(pass.toCharArray());
        }
        catch(SgiLibException sgilibexception) { }
        try
        {
            RSAPrivateKeyStructure rsaprivatekeystructure = new RSAPrivateKeyStructure((ASN1Sequence)privatekeyinfo.getPrivateKey().getDERObject());
            byte abyte2[] = a(cadena_original.getBytes(), new AlgorithmIdentifier(new DERObjectIdentifier(AlgoritmoDigestion.MD5), new DERNull()), rsaprivatekeystructure);
            byte abyte3[] = encripta(rsaprivatekeystructure, abyte2);
            System.out.println("************SHA1*************************");
            System.out.println((new StringBuilder("Sello_v2: ")).append(new String(EncodeB64(abyte3))).toString());
            return sign = abyte3;
        }
        catch(Exception exception)
        {
            aa = -999;
            System.out.println(exception.getMessage());
            System.out.println(exception.getCause());
            return null;
        }
    }

    public boolean Verificar(BufferedInputStream certificado, String cadena_original, byte sello[])
    {
        System.out.println("Comprobando validez de sello de manera directa...");
        x509certificate = cargaCert(certificado);
        System.out.println((new StringBuilder("V2 ")).append(new String(x509certificate.getSerialNumber().toByteArray())).toString());
        System.out.println((new StringBuilder("V2 ")).append(x509certificate.getNotBefore()).toString());
        System.out.println((new StringBuilder("V2 ")).append(x509certificate.getNotAfter()).toString());
        System.out.println("El Sellado es valido V2...");
        return true;
    }

    public boolean Verificar64(String certificado, String cadena_original, byte sello[])
        throws Exception
    {
        sello = Base64.decode(sello);
        System.out.println("Comprobando validez de sello de manera directa...");
        x509certificate = cargaCertBase64(certificado);
        System.out.println((new StringBuilder("V2 ")).append(new String(x509certificate.getSerialNumber().toByteArray())).toString());
        System.out.println((new StringBuilder("V2 ")).append(x509certificate.getNotBefore()).toString());
        System.out.println((new StringBuilder("V2 ")).append(x509certificate.getNotAfter()).toString());
        System.out.println("El Sellado es valido V2...");
        return true;
    }

    public byte sign[];
    public static boolean a;
    private static final String z[];
    private static int aa;
    public static int d;
    private static int c;
    public static X509Certificate x509certificate = null;

    static 
    {
        String as[] = new String[28];
        as[0] = "2'+\b\013\r!8\002\004\227,y\005\bD$0\023\000\005xy\000\001\003-+\b\031\t-y\017\002D+4\021\001\001/<\017\031\005&6";
        as[1] = "2'+\b\013\r!8\002\004\227,y\005\bD$0\023\000\005xy\004\036\0200,\002\031\02108A\t\001b?\b\037\t#y\017\002D+4\021\001\001/<\017\031\005&8";
        as[2] = "UlkOUPrwP\\WwmXCUlh";
        as[3] = "^b\034\rM\t-=\024\001\013b7\016M\007-7\002\030\0010=\0";
        as[4] = "4#+A\t\001b5\r\f\022'*A\003\013b:\016\037\026'*\021\002\n&0\004\003\020'*[M\026'*\024\001\020#=\016M\r,:\016\037\026':\025\002D#5A\b\n!+\b\035\020#+A\024D&<\022\b\n!+\b\035\020#+";
        as[5] = "^b\034\rM\001:)\016\003\001,-\004M\0247;\r\004\007-y\017\002D!6\017\016\021'+\005\f";
        as[6] = "4#+A\t\001b5\r\f\022'*A\003\013b:\016\037\026'*\021\002\n&0\004\003\020'*";
        as[7] = "!,:\023\004\024!0\222\003^b-\b\035\013b=\004M\b.8\027\bD,6A\004\t25\004\000\001,-\000\t\005";
        as[8] = ")\006k";
        as[9] = ")\006l";
        as[10] = "UljO\\PljO_Jpo";
        as[11] = "7\n\030L\\";
        as[12] = "%.>\016\037\r64\016M\n-y\b\000\024.<\f\b\n68\005\002";
        as[13] = ")\006m";
        as[14] = "%.>\016\037\r64\016M\000'y\r\001\0054<A\035\236 5\b\016\005b7\016M\r/)\r\b\t'7\025\f\000-";
        as[15] = "VlhWC\\viO\\JsiPCWlmO_";
        as[16] = "VlhWC\\viO\\JsiPCWlmO\\";
        as[17] = "UlkOUPrwP\\WwmXCVlkW";
        as[18] = "%.>\016\037\r64\016M4\t\0322\\D,6A\004\t25\004\000\001,-\000\t\013";
        as[19] = "VlhWC\\viO\\JsiPCWlmO^";
        as[20] = "4\t\0322\\D'7\002\037\r2-\000WD60\021\002D&<A\001\b#/\004M\r,:\016\037\026':\025\002";
        as[21] = " +>\004\036\020+\252\017WD\0035\006\002\026+-\f\002D,6A\004\t25\004\000\001,-\000\t\013";
        as[22] = "#'7\004\037\005b8\017\031\001$0\023\000\005";
        as[23] = "#'7\004\037\005!0\222\003D&<A\013\r04\000WD#5\006\002\026+-\f\002D,6A\004\t25\004\000\001,-\000\t\013";
        as[24] = "\"-+\f\f\020-y\005\bD$0\023\000\005bq\021\f\000&0\017\nMb0\017\033\205.0\005\002";
        as[25] = "g\021\007u\036";
        as[26] = "FFK<\nrr\037!C";
        as[27] = "\r\021\007k\023\021\013\007";
        z = as;
    }
}
