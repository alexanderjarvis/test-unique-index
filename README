Test ReactiveMongo Unique Index
=====================================

To test, either run 'play test' (which times out..) or 'play run' and then do the following curl **twice** (the second time should not return OK):

	curl -i -X POST http://localhost:9000/users

You will need the [play 2.1 snapshot](https://bitbucket.org/sgodbillon/repository/src/9f0c4e40cca1/play-2.1-SNAPSHOT.zip)

Monitor the mongodb collection 'users' in the database 'test'.

	use test
	db.users.find()
	
Also check that the unique index for 'email' is created ok.

	db.users.getIndexes()