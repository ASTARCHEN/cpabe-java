package cn.edu.pku.ss.crypto.abe.apiV2;

import java.io.File;

public class Example {
	public static void main(String[] args) {
		Server server = new Server();
		Client PKUClient = new Client(new String[] { "部门A", "职位A" });
		Client THUClient = new Client(new String[] { "部门A", "职位B" });
		Client TeacherClient = new Client(new String[] { "部门B", "职位A" });
		// client��server����ȡ��Կ�ַ���
		String PKJSONString = server.getPublicKeyInString();
		PKUClient.setPK(PKJSONString);
		THUClient.setPK(PKJSONString);
		TeacherClient.setPK(PKJSONString);

		// client���Լ���������Ϣ���͸�server,����ȡ˽Կ�ַ���
		String SKJSONString = server.generateSecretKey(PKUClient.getAttrs());
		PKUClient.setSK(SKJSONString);

		SKJSONString = server.generateSecretKey(THUClient.getAttrs());
		THUClient.setSK(SKJSONString);

		SKJSONString = server.generateSecretKey(TeacherClient.getAttrs());
		TeacherClient.setSK(SKJSONString);

		String outputFileName = "test.nst";
		File in = new File("README.md");
		String policy = "部门A"; // Student OR Teacher
		PKUClient.enc(in, policy, outputFileName);

		in = new File(outputFileName);

		try {
			THUClient.dec(in);
		} catch (Exception e) {
			System.out.println("ERROR");
		}
	}
}
