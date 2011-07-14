package com.nlogneg.model.objects;

import java.io.File;

public class ConnectionConfiguration {
	private String commonName;
	
	private File keystoreFile;
	private String keystorePassword;
	
	private File trustStoreFile;
	private String trustStorePassword;
	
	public ConnectionConfiguration(String commonName, File keystoreFile,
			String keystorePassword, File trustStoreFile,
			String trustStorePassword) {
		super();
		this.commonName = commonName;
		this.keystoreFile = keystoreFile;
		this.keystorePassword = keystorePassword;
		this.trustStoreFile = trustStoreFile;
		this.trustStorePassword = trustStorePassword;
	}

	public String getCommonName() {
		return commonName;
	}

	public File getKeystoreFile() {
		return keystoreFile;
	}

	public String getKeystorePassword() {
		return keystorePassword;
	}

	public File getTrustStoreFile() {
		return trustStoreFile;
	}

	public String getTrustStorePassword() {
		return trustStorePassword;
	}
	
	
}
