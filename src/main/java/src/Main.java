package src;

import com.mongodb.DocumentToDBRefTransformer;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;


import java.util.Arrays;
import java.util.logging.Logger;

import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class Main {
    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("vehicles"); //db
        MongoCollection mongoCollection = mongoDatabase.getCollection("cars"); //collections-table

//        save(mongoCollection);
//        read(mongoCollection);
//        readByParam(mongoCollection, "Mark", "BMW");
//        delete(mongoCollection, "Mark", "BMW");
        update(mongoCollection);
    }

    private static void update(MongoCollection mongoCollection) {
//        Document document = new Document();
//        document.put("Mark","Audi");
//        Document documentExist = (Document) mongoCollection.find(document).first();
//
//        Document documentUpdated = new Document();
//        documentUpdated.put("Model","A2");
//        documentUpdated.put("Color", "Black");

//        tu przy update kloci sie o niewlasciwy model danych bson jesli zrobimy jak wyzej
//        trzeba zrobić tak:

        Bson eq = Filters.eq("Mark", "Audi");
        Bson query = combine(set("Model","A2"), set("Color","Black"));

        mongoCollection.updateOne(eq,query);
    }

    private static void delete(MongoCollection mongoCollection, String param, String value) {
        Document document = new Document();
        document.put(param,value);
        mongoCollection.deleteMany(document);
    }

    private static void readByParam(MongoCollection mongoCollection, String param, Object value) {
        Document document = new Document();
        document.put(param,value);
        MongoCursor iterator = mongoCollection.find(document).iterator();
        while (iterator.hasNext()){
            Document next = (Document) iterator.next();
            Logger logger = Logger.getLogger(Main.class.getName());
            logger.info((next.toJson()));
        }
    }

    private static void read(MongoCollection mongoCollection) {

       Document first = (Document) mongoCollection.find().first();
        System.out.println(first.toJson());
    }

    private static void save(MongoCollection mongoCollection) {
//        Document documentCars = new Document();
//        documentCars.put("Mark", "Fiat");
//        documentCars.put("Model", "126p");
//
//        Document documentCars2 = new Document();
//        documentCars2.put("Mark", "BMW");
//        documentCars2.put("Model", "One");
//        documentCars2.put("Color", "Red");

        Document documentCars3 = new Document();
        documentCars3.put("Mark", "Audi");
        documentCars3.put("Model", "A1");
        documentCars3.put("Color", Arrays.asList("red", "gray", "black"));

        Document documentCars4 = new Document();
        documentCars4.put("Mark", "BMW");
        documentCars4.put("Model", "2");

        mongoCollection.insertMany(Arrays.asList(documentCars3, documentCars4));
    }
}
