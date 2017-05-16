package curator;

import java.security.ProtectionDomain;
import java.util.concurrent.TimeUnit;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.PathUtils;

public class LeaderLatchDemo {

	public static void main(String[] args) {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 5);
		CuratorFramework curator = CuratorFrameworkFactory.newClient("192.168.179.129:2182",retryPolicy);
		
		curator.getConnectionStateListenable().addListener(new ConnectionStateListener() {
			
			public void stateChanged(CuratorFramework client, ConnectionState newState) {
				// TODO Auto-generated method stub
				
			}
		});
		LeaderLatch leaderLatch = new LeaderLatch(curator, "/leaderlatch","node2");
		leaderLatch.addListener(new LeaderLatchListener() {
			
			public void notLeader() {
				System.out.println("notLeader");
				
			}
			
			public void isLeader() {
				System.out.println("isLeader"); 
				
			}
		});
		try {
			curator.start();
			leaderLatch.start();
			
			leaderLatch.await(2, TimeUnit.SECONDS);
			
			System.out.println("Is leader:" + leaderLatch.hasLeadership());
			System.out.println("Leader is:" + leaderLatch.getLeader());
			System.in.read();
			System.out.println("After delete node ,Is leader :" + leaderLatch.hasLeadership());
			System.in.read();
			System.out.println("Another node created ,Is leader :" + leaderLatch.hasLeadership()+"Leader is:" + leaderLatch.getLeader());
			System.in.read();
			leaderLatch.close();
			System.out.println("After leaderLatch close ,Is leader :" + leaderLatch.hasLeadership());
			curator.close();
			System.in.read();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
