package com.mechanitis.moovy.gettingstarted

import com.mechanitis.moovy.gettingstarted.person.Address
import com.mechanitis.moovy.gettingstarted.person.Person
import com.mechanitis.moovy.gettingstarted.person.PersonAdaptor
import org.mongodb.Document
import org.mongodb.MongoClient
import org.mongodb.MongoClients
import org.mongodb.MongoCollection
import org.mongodb.MongoDatabase
import org.mongodb.connection.ServerAddress
import spock.lang.Specification

import static com.mechanitis.moovy.test.util.JsonMatcher.jsonEqual
import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat

class Exercise3InsertSpecification extends Specification {
    def shouldTurnAPersonIntoADocument() {
        given:
        Person bob = new Person(id: 'bob',
                                name: 'Bob The Amazing',
                                address: new Address(street: '123 Fake St',
                                                     town: 'LondonTown',
                                                     phone: 1234567890),
                                bookIds: [27464, 747854]);

        when:
        Document bobAsDocument = new Document(PersonAdaptor.toDocument(bob));

        then:
        String expectedDocument = "{ _id : '${bob.id}'," +
                                  "  name : '${bob.name}' ," +
                                  '  address : ' +
                                  "   {  street : ${bob.address.street} ," +
                                  "      city : ${bob.address.town} ," +
                                  "      phone : ${bob.address.phone}" +
                                  '   } ,' +
                                  "  books : ${bob.bookIds}" +
                                  '}';
        (bobAsDocument.toString()) jsonEqual(expectedDocument)
    }

    def shouldBeAbleToSaveAPerson() throws UnknownHostException {
        given:
        MongoClient mongoClient = MongoClients.create(new ServerAddress())
        MongoDatabase database = mongoClient.getDatabase('Examples');
        MongoCollection collection = database.getCollection('people');

        Person charlie = new Person(id: 'charlie',
                                    name: 'Charles',
                                    address: new Address(street: '74 That Place',
                                                         town: 'London',
                                                         phone: 1234567890),
                                    bookIds: [1, 74]);

        when:
        collection.insert(new Document(PersonAdaptor.toDocument(charlie)));

        then:
        assertThat(collection.find().count(), is(1L));

        cleanup:
        database.tools().drop();
    }
}
