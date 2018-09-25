package cn.com.wasec.impl;

import java.io.InputStream;
import java.io.OutputStream;

import it.unisa.dia.gas.jpbc.Element;

public interface ISymmetricEncryption {
	public void crypto(int mode, InputStream is, OutputStream os, Element e);
}
