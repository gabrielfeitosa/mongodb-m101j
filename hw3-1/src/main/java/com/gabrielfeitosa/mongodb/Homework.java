package com.gabrielfeitosa.mongodb;

import java.net.UnknownHostException;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class Homework {

	public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB db = client.getDB("school");
        DBCollection collection = db.getCollection("students");

        DBCursor cur = collection.find();
        while (cur.hasNext()) {
        	BasicDBObject student = (BasicDBObject) cur.next();
        	BasicDBList scores = (BasicDBList) student.get("scores");
        	BasicDBList newScores = new BasicDBList();
        	double lowest = Double.MAX_VALUE;
        	BasicDBObject typeHomework = null;
        	for (int i = 0; i < scores.size(); i++) {
				BasicDBObject item = (BasicDBObject) scores.get(i);
				double studentScore = item.getDouble("score");
				if(!item.get("type").equals("homework")){
					newScores.add(item);
				}else{
					if(typeHomework == null || lowest < studentScore){
						typeHomework = item;
						lowest = studentScore;
					}
				}
        	}
        	newScores.add(typeHomework);
        	collection.update(new BasicDBObject("_id",student.get("_id")), new BasicDBObject("scores", newScores));
		}
    }

}
