package demo;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: MMM.
 * @Description: ZK练习
 * @Date:Created in 2017/12/25 11:03.
 * @Modified By:  MMM
 */
public class ZookeeperDemo {
	private static final String CONNECTION_STRING = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
	private static final int SEESION_TIMEOUT = 5000;

	private static final CountDownLatch latch = new CountDownLatch(1);

	public static void main(String[] args) throws Exception {
//				连接zookeeper
		ZooKeeper zk = new ZooKeeper(CONNECTION_STRING, SEESION_TIMEOUT, new Watcher() {
			@Override
			public void process(WatchedEvent watchedEvent) {
				if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
					latch.countDown();
				}
			}
		});
		latch.await();
//		获取ZooKeeper客户端对象
		System.out.println("zookeeper对象");
		System.out.println(zk);

////		以同步方式列出子节点
//		System.out.println("以同步方式列出子节点");
		List<String> children = zk.getChildren("/", null);
		for (String node : children) {
			System.out.println(node);
		}
//		以异步方式列出节点
//		System.out.println("以异步方式列出节点");
		zk.getChildren("/", null, new AsyncCallback.ChildrenCallback() {
			@Override
			public void processResult(int rc, String path, Object ctx, List<String> children) {
				for (String node : children) {
					System.out.println(node);
				}
			}
		}, null);

		//	    以异步方式列出节点
//		System.out.println("以异步方式列出节点2");
		zk.getChildren("/", null, new AsyncCallback.Children2Callback() {
			@Override
			public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
				for (String node : children) {
					System.out.println(node);
				}
			}
		}, null);

//		以同步方式判断节点是否已经存在
		Stat stat = zk.exists("/", null);
		if (stat != null) {
			System.out.println("Node exists");
		} else {
			System.out.println("Node does not exist");
		}
//		以异步方式判断节点是否已经存在
		zk.exists("/", null, new AsyncCallback.StatCallback() {
			@Override
			public void processResult(int i, String s, Object o, Stat stat) {
				if (stat != null) {
					System.out.println("Node exists");
				} else {
					System.out.println("Node does not exist");
				}
			}
		}, null);
////		以同步方式创建节点
//		String name = zk.create("/foo", "hello".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//		System.out.println(name);
////		以异步方式创建节点
//		zk.create("/foo1", "hello World".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, new AsyncCallback.StringCallback() {
//			@Override
//			public void processResult(int i, String s, Object o, String s1) {
//				System.out.println(s1);
//			}
//		}, null);
//	    以同步方式获取节点数据
		byte[] data = zk.getData("/foo", null, null);
		String dataStr = new String(data, "UTF-8");
		System.out.println(dataStr);
//	    以异步方式获取节点数据
		zk.getData("/foo1", null, new AsyncCallback.DataCallback() {
			@Override
			public void processResult(int i, String s, Object o, byte[] bytes, Stat stat) {
				try {
					String bytesStr = new String(bytes, "UTF-8");
					System.out.println(bytesStr);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}, null);
//		以同步方式更新节点数据
		Stat stat1 = zk.setData("/foo", "Hi".getBytes(), -1);
		System.out.println(stat1 != null);
//		以异步方式更新节点数据
		zk.setData("/foo1", "Hello World!".getBytes(), -1, new AsyncCallback.StatCallback() {
			@Override
			public void processResult(int i, String s, Object o, Stat stat) {
				System.out.println(stat != null);
			}
		}, null);
//		以同步方式删除节点
		zk.delete("/foo", -1);
//		以异步方式删除节点
		zk.delete("/foo1", -1, new AsyncCallback.VoidCallback() {
			@Override
			public void processResult(int i, String s, Object o) {
				System.out.println(i==0);
			}
		}, null);
	}

}
