package pl.mcdev.leagueoflegends.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.TimeoutException;

import javax.net.ssl.HttpsURLConnection;

public class IOUtils {

	public static void copy(InputStream source, File file) {
		try {
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len = 0;
			while ((len = source.read(buf)) != -1) {
				out.write(buf, 0, len);
			}
			out.close();
			source.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static File initialize(File file, boolean b) {
		if (!file.exists()) {
			try {
				file.getParentFile().mkdirs();
				if (b)
					file.createNewFile();
				else
					file.mkdir();
			} catch (IOException e) {
				if (Logger.exception(e.getCause()))
					e.printStackTrace();
			}
		}
		return file;
	}

	public static String getContent(String s) {
		String body = null;
		try {
			URL url = new URL(s);
			URLConnection con = url.openConnection();
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			InputStream in = con.getInputStream();
			String encoding = con.getContentEncoding();
			encoding = encoding == null ? "UTF-8" : encoding;
			body = IOUtils.toString(in, encoding);
		} catch (TimeoutException e) {
			Logger.info(e.getMessage());
		} catch (Exception e) {
			Logger.warning(e.getMessage());
		}
		return body;
	}

	public static void httpPost(String url, Map<String, String> values) {
		URLConnection con;
		try {
			con = new URL(url).openConnection();
			HttpURLConnection http = (HttpURLConnection) con;
			http.setRequestMethod("POST"); // PUT is another valid option
			http.setDoOutput(true);

			StringJoiner sj = new StringJoiner("&");
			for (Map.Entry<String, String> entry : values.entrySet())
				sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" + URLEncoder.encode(entry.getValue(), "UTF-8"));
			byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);

			http.setFixedLengthStreamingMode(out.length);
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			http.connect();
			OutputStream os = http.getOutputStream();
			os.write(out);

			os.close();
			http.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void httpsPost(String url, Map<String, String> values) {
		URLConnection con;
		try {
			con = new URL(url).openConnection();
			HttpsURLConnection http = (HttpsURLConnection) con;
			http.setRequestMethod("POST"); // PUT is another valid option
			http.setDoOutput(true);

			StringJoiner sj = new StringJoiner("&");
			for (Map.Entry<String, String> entry : values.entrySet())
				sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" + URLEncoder.encode(entry.getValue(), "UTF-8"));
			byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);

			http.setFixedLengthStreamingMode(out.length);
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			http.connect();
			OutputStream os = http.getOutputStream();
			os.write(out);

			os.close();
			http.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void jsonPost(String url, String json) {
		URLConnection con;
		try {
			con = new URL(url).openConnection();
			HttpURLConnection http = (HttpURLConnection) con;
			http.setRequestMethod("POST"); // PUT is another valid option
			http.setDoOutput(true);

			byte[] out = json.getBytes(StandardCharsets.UTF_8);
			int length = out.length;

			http.setFixedLengthStreamingMode(length);
			http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			http.connect();
			OutputStream os = http.getOutputStream();
			os.write(out);

			os.close();
			http.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static File getFile(String s, boolean folder) {
		File file = new File(s);
		try {
			if (!file.exists()) {
				if (folder)
					file.mkdirs();
				else {
					file.getParentFile().mkdirs();
					file.createNewFile();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	public static void delete(File f) {
		if (!f.exists())
			return;
		if (f.isDirectory())
			for (File c : f.listFiles())
				delete(c);
		if (!f.delete())
			try {
				throw new FileNotFoundException("Failed to delete file: " + f);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	}

	public static String toString(InputStream in, String encoding) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buf = new byte[8192];
		int len = 0;
		while ((len = in.read(buf)) != -1)
			baos.write(buf, 0, len);
		return new String(baos.toByteArray(), encoding);
	}
}
