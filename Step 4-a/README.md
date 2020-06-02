# Function that retrieves an object from a bucket in Object Storage using the OCI Java SDK

In the previous step you passed in two parameters - file name and bucket name.
```
echo -n '{"name": "file1.txt", "bucketName":"mybucket"}' | fn invoke myapp oci-objectstorage-get-object-java
```

In this step, let us use the file name and bucket name from the Cloud Event
 JSON - Object Storage Create Object event. 

You can copy the Cloud Event JSON from the OCI Console > Events Service > 
Validate Rule.

Here's an example [test-event.json](test-event.json).

## Deploy

```
fn -v deploy --app myapp
```

## Test

```
fn invoke myapp oci-objectstorage-get-object-java < test-event.json
```

## Logs

```
Tue, Jun 2, 2020, 11:23:08 UTC Inside ObjectStorageGetObject::handle(Map) method
Tue, Jun 2, 2020, 11:23:08 UTC CloudEvent Map: {cloudEventsVersion=0.1, eventId=unique_ID, eventType=com.oraclecloud.objectstorage.createobject, source=objectstorage, eventTypeVersion=2.0, eventTime=2019-01-10T21:19:24.000Z, contentType=application/json, extensions={compartmentId=ocid1.compartment.oc1..unique_ID}, data={compartmentId=ocid1.compartment.oc1..unique_ID, compartmentName=example_name, resourceName=my_object, resourceId=/n/example_namespace/b/my_bucket/o/my_object, availabilityDomain=all, additionalDetails={eTag=f8ffb6e9-f602-460f-a6c0-00b5abfa24c7, namespace=example_namespace, bucketName=my_bucket, bucketId=ocid1.bucket.oc1.phx.unique_id, archivalState=Available}}}
Tue, Jun 2, 2020, 11:23:08 UTC CloudEvent Namespace: example_namespace
Tue, Jun 2, 2020, 11:23:08 UTC CloudEvent Bucket name: my_bucket
Tue, Jun 2, 2020, 11:23:09 UTC CloudEvent Object name: my_object
```