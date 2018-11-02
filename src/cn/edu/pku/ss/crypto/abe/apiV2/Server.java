package cn.edu.pku.ss.crypto.abe.apiV2;

import java.io.File;
import java.io.IOException;

import cn.edu.pku.ss.crypto.abe.CPABEImplWithoutSerialize;
import cn.edu.pku.ss.crypto.abe.CPABEImplWithoutSerialize.KeyPair;
import cn.edu.pku.ss.crypto.abe.MasterKey;
import cn.edu.pku.ss.crypto.abe.PublicKey;
import cn.edu.pku.ss.crypto.abe.serialize.SerializeUtils;

public class Server {
	private KeyPair pair;

	public Server() {
		File mkfile = new File("MK");
		File pkfile = new File("PK");
		if (mkfile.exists() && pkfile.exists()) {
			pair = new KeyPair();
			MasterKey mk = SerializeUtils.unserialize(MasterKey.class, mkfile);
			PublicKey pk = SerializeUtils.unserialize(PublicKey.class, pkfile);
			pair.setMK(mk);
			pair.setPK(pk);
		} else {
			pair = CPABEImplWithoutSerialize.setup();
			SerializeUtils.serialize(pair.getMK(), mkfile);
			SerializeUtils.serialize(pair.getPK(), pkfile);
		}
	}

	public String getPublicKeyInString() {
		return pair.getPKJSONString();
	}

	public String generateSecretKey(String[] attrs) {
		return CPABEImplWithoutSerialize.keygen(attrs, pair.getPK(), pair.getMK());
	}
}
