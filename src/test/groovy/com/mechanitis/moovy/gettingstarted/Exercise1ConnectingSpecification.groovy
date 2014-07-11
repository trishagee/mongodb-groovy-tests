package com.mechanitis.moovy.gettingstarted

import org.mongodb.MongoClient
import org.mongodb.MongoClientURI
import org.mongodb.MongoClients
import org.mongodb.connection.ServerAddress
import spock.lang.Specification

class Exercise1ConnectingSpecification extends Specification {
    def 'should create a new mongo client connected to localhost'() throws Exception {
        when:
        MongoClient mongoClient = MongoClients.create(new ServerAddress())

        then:
        mongoClient != null
    }

    def 'should be able to connected via URI'() {
        when:
        MongoClient mongoClient = MongoClients.create(new MongoClientURI('mongodb://localhost:27017'));

        then:
        mongoClient != null
    }

}
