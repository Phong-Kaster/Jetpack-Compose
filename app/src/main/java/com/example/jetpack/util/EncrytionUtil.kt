package com.example.jetpack.util

import android.util.Log
import java.security.SecureRandom
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

/**
 * # [AES (Advanced Encryption Standard)](https://medium.com/@harmanpreet.khera/building-a-secure-android-application-encryption-techniques-dd11a2208242)
 */
object EncryptionUtil {

    private val TAG = this.javaClass.simpleName
    private const val ALGORITHM = "AES" // Specifies the encryption algorithm (AES).
    private const val TRANSFORMATION = "AES/CBC/PKCS5Padding" // Defines the algorithm, mode (CBC), and padding (PKCS5Padding)
    private const val KEY_SIZE = 256 // Specifies the key size (256 bits).

    fun generateKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(ALGORITHM)
        keyGenerator.init(KEY_SIZE, SecureRandom())
        return keyGenerator.generateKey()
    }

    /**
     * Cipher Initialization: Uses Cipher.getInstance(TRANSFORMATION) to create a Cipher object.
     *
     * IV Generation: Generates an initialization vector (IV) for CBC mode.
     *
     * Encryption: Initializes the Cipher in ENCRYPT_MODE and encrypts the data.
     *
     * Encoding: Combines the IV and encrypted data, then encodes them in Base64 for easy storage/transmission.
     */
    fun encrypt(data: String, secretKey: SecretKey): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val iv = ByteArray(cipher.blockSize)
        SecureRandom().nextBytes(iv)
        val ivParameterSpec = IvParameterSpec(iv)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec)
        val encryptedBytes = cipher.doFinal(data.toByteArray())
        val ivAndEncryptedData = iv + encryptedBytes
        return Base64.getEncoder().encodeToString(ivAndEncryptedData)
    }

    /**
     * Decoding: Decodes the Base64-encoded data to retrieve the IV and encrypted bytes.
     *
     * Cipher Initialization: Uses the same transformation settings to initialize the Cipher in DECRYPT_MODE.
     *
     * Decryption: Decrypts the data and converts it back to a String.
     */
    fun decrypt(encryptedData: String, secretKey: SecretKey): String {
        val ivAndEncryptedData = Base64.getDecoder().decode(encryptedData)
        val iv = ivAndEncryptedData.copyOfRange(0, 16)
        val encryptedBytes = ivAndEncryptedData.copyOfRange(16, ivAndEncryptedData.size)
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val ivParameterSpec = IvParameterSpec(iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec)
        val decryptedBytes = cipher.doFinal(encryptedBytes)
        return String(decryptedBytes)
    }

    fun runAesExample() {
        val secretKey = generateKey()

        val message = "Phong Kaster"

        val encryptedData = encrypt(message, secretKey)
        val decryptedData = decrypt(encryptedData, secretKey)

        Log.d(TAG, "-----------------------")
        Log.d(TAG, "Original Data: $message")
        Log.d(TAG, "Encrypted Data: $encryptedData")
        Log.d(TAG, "Decrypted Data: $decryptedData")
    }
}