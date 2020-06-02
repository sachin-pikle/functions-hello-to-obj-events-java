# Function that retrieves an object from a bucket in Object Storage using the OCI Java SDK

In the previous step, we used a modified Cloud Event JSON to pass in the 
actual namespace, bucket name and file name to our function.
```
fn invoke myapp oci-objectstorage-get-object-java < test-event.json
```

In this step, let us test the entire scenario end-to-end with a real Cloud 
Event. Let us define an Events Rule to trigger the function when a text file 
is uploaded to the bucket.

## Event Rule

* Go to the OCI Console > Events Service > Create Rule
* Rule Conditions: 
    * Select `Event Type`
    * Service Name = `Object Storage`
    * Event Type = `Object - Create`
    * Add Condition
    * Condition Type = `Attribute`
    * bucketName = `Enter the name of your bucket`
* Actions:
    * Action Type = `Functions`
    * Compartment = `Enter/select the compartment that contains your function`
    * Application = `myapp`
    * Function = `oci-objectstorage-get-object-java`

## Code

Just cleaned up commented/dead code.

## Deploy

```
fn -v deploy --app myapp
```

## Test

Upload a small text file in the bucket and check the function logs. You should
 see no errors. Also, you should see the real Cloud Event that was generated
 by the file upload event.

