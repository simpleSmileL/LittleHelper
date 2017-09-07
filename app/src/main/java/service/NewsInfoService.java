package service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import bean.NewsItem;

import android.util.Xml;


public class NewsInfoService {
	
	public static List<NewsItem> getAllNews(String path){
		List<NewsItem>list = new ArrayList<NewsItem>();
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(3000);
			conn.setRequestMethod("GET");
			int code = conn.getResponseCode();
			if(code == 200){
				InputStream is = conn.getInputStream();
				XmlPullParser parser = Xml.newPullParser();
				parser.setInput(is, "utf-8");
				int eventtype = parser.getEventType();
				NewsItem item = null;
				while(eventtype != XmlPullParser.END_DOCUMENT){
					switch (eventtype) {
					case XmlPullParser.START_TAG:
						if("item".equals(parser.getName())){
							item = new NewsItem();
						}else if("title".equals(parser.getName())){
							item.setTitle(parser.nextText());
						}else if("description".equals(parser.getName())){
							item.setDesc(parser.nextText());
						}else if("image".equals(parser.getName())){
							item.setImage(parser.nextText());
						}else if("type".equals(parser.getName())){
							item.setType(parser.nextText());
						}else if("comment".equals(parser.getName())){
							item.setComment(Integer.parseInt(parser.nextText()));
						}
						
						
						break;

					case XmlPullParser.END_TAG:
						if("item".equals(parser.getName())){
							list.add(item);
						}
						
						break;
					}
					
					eventtype=parser.next();
				}
				is.close();
				
				
			}else{
				return null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(list.get(0).title);
		return list;
		
	}
	
	
}
