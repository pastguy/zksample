package zksample;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class Participant implements Watcher,Runnable{

	private String status;
	private String name;

	private static String zkServer = "192.168.179.128:2181,192.168.179.128:2182,192.168.179.128:2183";
	private static int sessionTimeout = 999999;
	private static String ELECTION_PATH = "/election";

	private ZooKeeper zookeeper;

	public Participant(String name) {
		this.name = name;
		try {
			this.zookeeper = new ZooKeeper(zkServer, sessionTimeout,
					new Watcher() {

						public void process(WatchedEvent event) {
							System.out.println(event);

						}
					});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void run() {
		// TODO Auto-generated method stub
		
	}

	public void process(WatchedEvent event) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 竞选leader
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public boolean elect() throws KeeperException, InterruptedException {

			String mypath = zookeeper.create(ELECTION_PATH + "/" + "node", null,
					Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
			System.out.println( name + "path:"  + mypath);
			return repeatElect(mypath);
	}

	public boolean repeatElect(final String mypath) {

		List<String> childNodes;
		try {
			childNodes = zookeeper.getChildren(ELECTION_PATH, null);

			Collections.sort(childNodes);
			String preNode = "";
			for (int i = 0; i < childNodes.size(); i++) {
				if (i == 0 && mypath.endsWith(childNodes.get(i))) {
					this.setStatus("Leader");
					System.out.println(name + "is leader");
					return true;
				}
				preNode = childNodes.get(i);
				if (mypath.endsWith(childNodes.get(i))) {
					this.setStatus("Follower");
					System.out.println(name + "is follower");
					break;
				}
			}
			zookeeper.exists(ELECTION_PATH + "/" + preNode, new Watcher() {

				public void process(WatchedEvent event) {
					repeatElect(mypath);
				}

			});
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


}
