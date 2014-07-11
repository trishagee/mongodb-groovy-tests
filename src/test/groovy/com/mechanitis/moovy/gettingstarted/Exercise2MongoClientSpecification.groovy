package com.mechanitis.moovy.gettingstarted

import org.mongodb.Document
import org.mongodb.MongoClient
import org.mongodb.MongoClients
import org.mongodb.MongoCollection
import org.mongodb.MongoDatabase
import org.mongodb.connection.ServerAddress
import spock.lang.Specification

class Exercise2MongoClientSpecification extends Specification {
    def shouldGetADatabaseFromTheMongoClient() throws Exception {
        given:
        MongoClient mongoClient = MongoClients.create(new ServerAddress())

        when:
        MongoDatabase database = mongoClient.getDatabase('TheDatabaseName');

        then:
        database != null;
    }

    def shouldGetACollectionFromTheDatabase() throws Exception {
        given:
        MongoClient mongoClient = MongoClients.create(new ServerAddress())
        MongoDatabase database = mongoClient.getDatabase('TheDatabaseName');

        when:
        MongoCollection collection = database.getCollection('TheCollectionName');

        then:
        collection != null
    }

    def shouldNotBeAbleToUseMongoClientAfterItHasBeenClosed() throws UnknownHostException {
        given:
        MongoClient mongoClient = MongoClients.create(new ServerAddress())

        when:
        mongoClient.close();
        mongoClient.getDatabase('SomeDatabase').getCollection('coll').insert([field: 'value'] as Document);

        then:
        thrown IllegalStateException
    }

}
