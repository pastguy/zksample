package zksample;


public class ZkClient {

	public static void main(String[] args) throws Exception {

		for (int i = 0; i < 5; i++) {
			
			Participant pc = new Participant("node"+2);
			new Thread(pc).start();
		}

	}

}
