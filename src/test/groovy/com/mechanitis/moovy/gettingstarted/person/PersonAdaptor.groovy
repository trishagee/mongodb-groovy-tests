package com.mechanitis.moovy.gettingstarted.person

import org.mongodb.Document;

/**
 * This Adaptor allows us to convert our domain object, Person, into the MongoDB-specific Document.
 */
final class PersonAdaptor {
    static final Map toDocument(Person person) {
        [_id    : person.id,
         name   : person.name,
         address: [street: person.address.street,
                   city  : person.address.town,
                   phone : person.address.phone] as Document,
         books  : person.bookIds] as Document;
    }
}
