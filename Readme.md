Prepare
=

1. Download/Unpackage Solr `rm -R solr-5.5.2 && wget http://archive.apache.org/dist/lucene/solr/5.5.2/solr-5.5.2.zip && unzip solr-5.5.2.zip && rm solr-5.5.2.zip`
2. Start Solr in default cloud example `solr-5.5.2/bin/solr -e cloud -noprompt -a "-Denable.runtime.lib=true"`
3. Upload configset `solr-5.5.2/bin/solr zk -upconfig -n config -d src/main/resources/conf -z localhost:9983`
4. Create Blob Store `curl 'http://localhost:8983/solr/admin/collections?action=CREATE&name=.system'`
5. Create collection `solr-5.5.2/bin/solr create -c collection -n config`

Test
=

1. Build jar `mvn clean package`
2. Upload handler `curl -X POST -H 'Content-Type: application/octet-stream' --data-binary @target/solr-cloud-lib-test-1.0.jar http://localhost:8983/solr/.system/blob/solr-cloud-lib-test`
3. Check available handler versions `curl http://localhost:8983/solr/.system/blob/solr-cloud-lib-test`
3. Make collection aware of handler (version 1) `curl http://localhost:8983/solr/collection/config -H 'Content-type:application/json' -d '{"add-runtimelib": { "name":"solr-cloud-lib-test", "version":1 }}'`
4. Load the handler by using it `curl http://localhost:8983/solr/collection/handler`
5. Get info about the current handler `curl 'http://localhost:8983/solr/collection/admin/mbeans?wt=json&key=/handler&cat=QUERYHANDLER'`

Update
=

1. Change component version to 2.0 (in both, the java class and maven project; could be done by plugin)
2. Build jar `mvn clean package`
3. Upload handler `curl -X POST -H 'Content-Type: application/octet-stream' --data-binary @target/solr-cloud-lib-test-2.0.jar http://localhost:8983/solr/.system/blob/solr-cloud-lib-test`
4. Make collection aware of handler `curl http://localhost:8983/solr/collection/config -H 'Content-type:application/json' -d '{"update-runtimelib": { "name":"solr-cloud-lib-test", "version":2 }}'`
5. Load the handler by using it `curl http://localhost:8983/solr/collection/handler`
6. Get info about the current handler `curl 'http://localhost:8983/solr/collection/admin/mbeans?wt=json&key=/handler&cat=QUERYHANDLER'`

Close
=

1. Stop solr `solr-5.5.2/bin/solr stop -all`