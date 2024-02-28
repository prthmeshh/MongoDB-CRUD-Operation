package org.example;
import com.mongodb.*;

import org.example.model.User;
import java.net.UnknownHostException;

public class MongoDBCrudExample {
    public static void main(String[] args) throws UnknownHostException {

        User user=new User(101,"Rohan","MGR",true);

        DBObject doc = createDBObject(user);

        MongoClient mongo = new MongoClient("localhost", 27017);

        DB db=mongo.getDB("testdb");
        // DBCollection coll = db.createCollection("users",doc);
        DBCollection coll = db.getCollection("posts");

        //Create User
        WriteResult result = coll.insert(doc);
        System.out.println(result.getUpsertedId());
        System.out.println(result.getN());

        // Read User
        DBObject obj = BasicDBObjectBuilder.start().add("id",user.getId()).get();
        DBCursor cursor = coll.find(obj);
        while(cursor.hasNext()){
            System.out.println(cursor.next());
        }

        //Update
        user.setName("Rohan Sharma");
        doc = createDBObject(user);
        coll.update(obj, doc);

    }

    private static DBObject createDBObject(User user){

        BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();
        docBuilder.append("_id",user.getId());
        docBuilder.append("name",user.getName());
        docBuilder.append("role",user.getRole());
        docBuilder.append("isEmployee",user.isEmployee());
        return docBuilder.get();

    }
}
