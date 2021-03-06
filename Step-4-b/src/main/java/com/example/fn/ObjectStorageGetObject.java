/*
** ObjectStorageGetObject version 1.0.
**
** Copyright (c) 2020 Oracle, Inc.
** Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl.
*/

package com.example.fn;

import com.oracle.bmc.auth.ResourcePrincipalAuthenticationDetailsProvider;
import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.requests.GetObjectRequest;
import com.oracle.bmc.objectstorage.responses.GetObjectResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import java.util.Map;

public class ObjectStorageGetObject {

    private ObjectStorage objStoreClient = null;
    final ResourcePrincipalAuthenticationDetailsProvider provider
            = ResourcePrincipalAuthenticationDetailsProvider.builder().build();

    public ObjectStorageGetObject() {

        try {
            //print env vars in Functions container
            System.err.println("OCI_RESOURCE_PRINCIPAL_VERSION " + System.getenv("OCI_RESOURCE_PRINCIPAL_VERSION"));
            System.err.println("OCI_RESOURCE_PRINCIPAL_REGION " + System.getenv("OCI_RESOURCE_PRINCIPAL_REGION"));
            System.err.println("OCI_RESOURCE_PRINCIPAL_RPST " + System.getenv("OCI_RESOURCE_PRINCIPAL_RPST"));
            System.err.println("OCI_RESOURCE_PRINCIPAL_PRIVATE_PEM " + System.getenv("OCI_RESOURCE_PRINCIPAL_PRIVATE_PEM"));

            objStoreClient = new ObjectStorageClient(provider);

        } catch (Throwable ex) {
            System.err.println("Failed to instantiate ObjectStorage client - " + ex.getMessage());
        }
    }

    public String handle(Map cloudEvent) {
        System.out.println("Inside ObjectStorageGetObject::handle(Map) method");
        System.out.println("CloudEvent Map: " + cloudEvent);

        Map data = (Map) cloudEvent.get("data");
        String objectName = (String) data.get("resourceName");
        Map additionalDetails = (Map) data.get("additionalDetails");
        String namespace = (String) additionalDetails.get("namespace");
        String bucketName = (String) additionalDetails.get("bucketName");

        System.out.println("CloudEvent Namespace: " + namespace);
        System.out.println("CloudEvent Bucket name: " + bucketName);
        System.out.println("CloudEvent Object name: " + objectName);

        String result = "FAILED";

        if (objStoreClient == null) {
            System.err.println("There was a problem creating the ObjectStorage Client object. Please check logs");
            return result;
        }
//        try {

            GetObjectRequest gor = GetObjectRequest.builder()
//                    .namespaceName(nameSpace)
                    .namespaceName(namespace)
//                    .bucketName(objectInfo.getBucketName())
                    .bucketName(bucketName)
//                    .objectName(objectInfo.getName())
                    .objectName(objectName)
                    .build();
            System.out.println("Getting content for object " + objectName + " from bucket " + bucketName);

            GetObjectResponse response = objStoreClient.getObject(gor);
            result = new BufferedReader(new InputStreamReader(response.getInputStream()))
                    .lines().collect(Collectors.joining("\n"));

            System.out.println("Finished reading content for object " + result);

//        } catch (Throwable e) {
//            System.err.println("Error fetching object " + e.getMessage());
//            result = "Error fetching object " + e.getMessage();
//        }

        return result;
    }
}
