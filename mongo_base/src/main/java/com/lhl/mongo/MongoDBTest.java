package com.lhl.mongo;

import java.util.Iterator;

import com.mongodb.client.FindIterable;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * 以前写的MongoDB保存读取数据的简单demo
 */
public class MongoDBTest {
	public static void main(String args[]) {
		MongoClient mongoClient =null;
		try {
			// 连接到 mongodb 服务
			 mongoClient = new MongoClient("192.168.96.89", 27017);
			 
			// 连接到数据库
			MongoDatabase mongoDatabase = mongoClient.getDatabase("test");
			System.out.println("Connect to database successfully");

			MongoCollection<Document> collection = mongoDatabase.getCollection("test");
			
			Document doc = new Document("name", "haha").append("id", "12345678");
			
			collection.insertOne(doc);
			
			FindIterable<Document> findIterable = collection.find();
			
			Iterator<Document> iterator=null;
			try {
				iterator = findIterable.iterator();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			while(iterator.hasNext()){
				Document document = iterator.next();
				System.out.println(document.get("name"));
			}

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}finally{
			mongoClient.close();
		}
	}
}
