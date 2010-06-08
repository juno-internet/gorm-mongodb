package grails.plugins.mongodb.test

import com.mongodb.BasicDBObjectBuilder
import com.mongodb.BasicDBObject

/**
 * tests the mongo bean and access to the wired mongodb driver
 *
 * @author: Juri Kuehn
 */
class LowLevelTests extends GroovyTestCase {

  def mongo

  void testLowLevelAccess() {
    String testedCollection = "unlikelyToHaveThatBrrrr"

    println mongo.db.collectionNames

    // put one object into a new collection
    BasicDBObjectBuilder dbb = new BasicDBObjectBuilder();
    dbb.add("field1", 1343)
       .add("field2", "hoho")
       .push("submap").add("sub1", 1).add("sub2", 2);
    mongo.db.getCollection(testedCollection).insert(dbb.get());

    // test this collection
    assertEquals "tested collection should have 1 element", 1, mongo.db.getCollection(testedCollection).count
    assertNotNull "inserted element should be in db", mongo.db.getCollection(testedCollection).findOne(new BasicDBObject("field2", "hoho"))

    // remove it
    mongo.db.getCollection(testedCollection).drop()
    assertFalse "tested collection should have been removed", mongo.db.collectionNames.contains(testedCollection)
  }

}