package com.lhl.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * MongoDB保存读取数据的简单demo
 * 参照<a>http://www.mongodb.org.cn/tutorial/24.html</a>
 */
public class MongoDBTest {

    @Test
    public void connectWithoutUserAndPass() {
        // 创建连接
        try (MongoClient mongoClient = new MongoClient("192.168.96.89", 27017)) {
            // 连接到数据库
            MongoDatabase database = mongoClient.getDatabase("test");
            System.out.println("成功连接到MongoDB数据库");
            // 创建集合
            database.createCollection("testx");
            System.out.println("集合创建成功");
            // 获取集合
            MongoCollection<Document> collection = database.getCollection("test");
            System.out.println("集合获取成功");
            // 写入文档
            Document document = new Document("title", "mongodb").append("desc", "nosql")
                    .append("likes", 100).append("by", "lhl");
            List<Document> documentAddList = new ArrayList<>();
            documentAddList.add(document);
            collection.insertMany(documentAddList);
            System.out.println("插入文档成功");
            // 查询所有文档
            FindIterable<Document> documents = collection.find();
            MongoCursor<Document> iterator = documents.iterator();
            System.out.println("--------------新增后检验内容");
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
            // 更新文档
            collection.updateMany(Filters.eq("likes", 100), new Document("$set", new Document("likes", 200)));
            documents = collection.find();
            iterator = documents.iterator();
            System.out.println("--------------修改后检验内容");
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
            // 删除第一个文档
            collection.deleteOne(Filters.eq("likes", 200));
            documents = collection.find();
            iterator = documents.iterator();
            System.out.println("--------------删除后检验内容");
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 需要用账号密码登录
     */
    public void connectWithUserAndPass() {
        ServerAddress serverAddress = new ServerAddress("192.168.96.89", 27017);
        List<ServerAddress> addressList = new ArrayList<>();
        addressList.add(serverAddress);
        // 参数依次为 用户名 数据库名称 密码
        MongoCredential credential = MongoCredential.createCredential("root", "test", "password".toCharArray());
        List<MongoCredential> credentialList = new ArrayList<>();
        credentialList.add(credential);
        // 通过连接认证获取MongoDB连接
        try (MongoClient mongoClient = new MongoClient(addressList, credentialList)) {
            MongoDatabase database = mongoClient.getDatabase("test");
            System.out.println("成功连接到MongoDB数据库");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
