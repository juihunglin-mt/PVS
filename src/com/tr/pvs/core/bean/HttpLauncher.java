
package com.tr.pvs.core.bean;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;

public class HttpLauncher {

	public void connect(String url) throws Exception {
		URL conn = new URL(url);
		
		URLConnection c = conn.openConnection();
		BufferedInputStream is = new BufferedInputStream(c.getInputStream());
		byte[] buf = new byte[1024]; 
		int read;
		while ((read = is.read(buf)) != -1) {
			System.out.write(buf, 0, read);
		}
		is.close();
	}
	
	public static void main(String argv[]) {
		try {
			HttpLauncher hc = new HttpLauncher();
			hc.connect(argv[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
