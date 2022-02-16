package br.com.seg.maxipago.gateway;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

public class SaveRequest {

	public static void log(String request, String response, String postUrl) {

		try {
			OutputStream os = new FileOutputStream("/opt/maxipago/requestAndResponse.txt");
			OutputStreamWriter osw= new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);

			Date date = new Date();
			
			
			bw.write(date.toString() + " " + postUrl + "\n\n");
			bw.write("== REQUEST == \n\n");
			bw.write(request + "\n\n");
			bw.write("== RESPONSE == \n\n");
			bw.write(response);

			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();

		}
	}
}