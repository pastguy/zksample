package curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class LeaderSelectorDemo {
	RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 5);
	CuratorFramework curator = CuratorFrameworkFactory.newClient("192.168.179.129:2182",retryPolicy);
	
//	LeaderSelector selector = new LeaderSelector(curator, "leaderSelector", listener)
}
