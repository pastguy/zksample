package zksample;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ZkClient {

	public static void main(String[] args) throws Exception {

//		try {
//			Participant pc1 = new Participant("node1");
//			pc1.elect();
//			Participant pc2 = new Participant("node2");
//			pc2.elect();
//			Participant pc3 = new Participant("node3");
//			pc3.elect();
//		} catch (KeeperException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		Watcher wc = new Watcher() {

			public void process(WatchedEvent event) {
				System.out.println(event);;
			}

		};
		ZooKeeper zookeeper = new ZooKeeper("192.168.179.128:2182", 9999999,wc);
		zookeeper.exists("/election/node1", new Watcher() {

			public void process(WatchedEvent event) {
				System.out.println(event);;
			}

		});
		System.in.read();
//		String mypath = zookeeper.create("/election/node1", null,
//				Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//		zookeeper.getData("/election/node1", true,null);
		
	}

}
