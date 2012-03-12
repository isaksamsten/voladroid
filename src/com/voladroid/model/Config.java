package com.voladroid.model;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;
import org.apache.commons.io.FileUtils;

public class Config {

	public static final String DEFAULT_FILE = "default.properties";
	private File configFile;
	private PropertiesConfiguration properties;

	public Config(File file) throws ConfigurationException {
		configFile = file;

		try {
			FileUtils.touch(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		properties = new PropertiesConfiguration(configFile);
		properties.setAutoSave(true);
	}

	public void addConfigurationListener(ConfigurationListener l) {
		properties.addConfigurationListener(l);
	}

	public boolean removeConfigurationListener(ConfigurationListener l) {
		return properties.removeConfigurationListener(l);
	}

	public void setProperty(String key, Object value) {
		properties.setProperty(key, value);
	}

	public boolean containsKey(String key) {
		return properties.containsKey(key);
	}

	public void addProperty(String key, Object value) {
		properties.addProperty(key, value);
	}

	public Object getProperty(String key) {
		return properties.getProperty(key);
	}

	public BigDecimal getBigDecimal(String arg0, BigDecimal arg1) {
		return properties.getBigDecimal(arg0, arg1);
	}

	public BigDecimal getBigDecimal(String key) {
		return properties.getBigDecimal(key);
	}

	public BigInteger getBigInteger(String arg0, BigInteger arg1) {
		return properties.getBigInteger(arg0, arg1);
	}

	public BigInteger getBigInteger(String key) {
		return properties.getBigInteger(key);
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		return properties.getBoolean(key, defaultValue);
	}

	public Boolean getBoolean(String arg0, Boolean arg1) {
		return properties.getBoolean(arg0, arg1);
	}

	public boolean getBoolean(String key) {
		return properties.getBoolean(key);
	}

	public byte getByte(String key, byte defaultValue) {
		return properties.getByte(key, defaultValue);
	}

	public Byte getByte(String arg0, Byte arg1) {
		return properties.getByte(arg0, arg1);
	}

	public byte getByte(String key) {
		return properties.getByte(key);
	}

	public double getDouble(String key, double defaultValue) {
		return properties.getDouble(key, defaultValue);
	}

	public Double getDouble(String arg0, Double arg1) {
		return properties.getDouble(arg0, arg1);
	}

	public double getDouble(String key) {
		return properties.getDouble(key);
	}

	public float getFloat(String key, float defaultValue) {
		return properties.getFloat(key, defaultValue);
	}

	public Float getFloat(String arg0, Float arg1) {
		return properties.getFloat(arg0, arg1);
	}

	public float getFloat(String key) {
		return properties.getFloat(key);
	}

	public int getInt(String key, int defaultValue) {
		return properties.getInt(key, defaultValue);
	}

	public int getInt(String key) {
		return properties.getInt(key);
	}

	public Integer getInteger(String arg0, Integer arg1) {
		return properties.getInteger(arg0, arg1);
	}

	@SuppressWarnings("unchecked")
	public Iterator<Object> getKeys() {
		return properties.getKeys();
	}

	@SuppressWarnings("unchecked")
	public List<Object> getList(String arg0, List<?> arg1) {
		return properties.getList(arg0, arg1);
	}

	@SuppressWarnings("unchecked")
	public List<Object> getList(String key) {
		return properties.getList(key);
	}

	public char getListDelimiter() {
		return properties.getListDelimiter();
	}

	public long getLong(String key, long defaultValue) {
		return properties.getLong(key, defaultValue);
	}

	public Long getLong(String arg0, Long arg1) {
		return properties.getLong(arg0, arg1);
	}

	public long getLong(String key) {
		return properties.getLong(key);
	}

	public short getShort(String key, short defaultValue) {
		return properties.getShort(key, defaultValue);
	}

	public Short getShort(String arg0, Short arg1) {
		return properties.getShort(arg0, arg1);
	}

	public short getShort(String key) {
		return properties.getShort(key);
	}

	public String getString(String key, String defaultValue) {
		return properties.getString(key, defaultValue);
	}

	public String getString(String key) {
		return properties.getString(key);
	}

	public String[] getStringArray(String arg0) {
		return properties.getStringArray(arg0);
	}

	public void setListDelimiter(char listDelimiter) {
		properties.setListDelimiter(listDelimiter);
	}

	public void save() throws ConfigurationException {
		properties.save();
	}
}
