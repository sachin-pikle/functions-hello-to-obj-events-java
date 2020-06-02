# Function that retrieves an object from a bucket in Object Storage using the OCI Java SDK

In the previous step, we used a dummy Cloud Event JSON to pass in three 
parameters - namespace, bucket name and file name to our function.
```
fn invoke myapp oci-objectstorage-get-object-java < test-event.json
```

In this step, let us use actual values for namespace, bucket name and file 
name and bucket name in the Cloud Event JSON. 

Replace the values with actual values from your tenancy/compartment.
* file name ==> "data"."resourceName": "small-text-file.txt"
* namespace ==> "data"."additionalDetails"."namespace": "your-namespace"
* bucket name ==> "data"."additionalDetails"."bucketName": "test-bucket"

Here's an example [test-event.json](test-event.json).

## Code

Add the code to use GetObjectRequest, objStoreClient.getObject() and 
GetObjectResponse from the OCI SDK to get the object from the bucket.

## Deploy

```
fn -v deploy --app myapp
```

## Test

```
fn invoke myapp oci-objectstorage-get-object-java < test-event.json
```
This will return the actual contents of the file.
