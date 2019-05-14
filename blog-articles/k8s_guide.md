# Host your application using persistent storage on Google Kubernetes Engine
Google Kubernetes Engine is a Google Cloud Platform service that serves as a Kubernetes-based environment for managing, scaling and deploying containerized applications.
In this tutorial, I'd like to show how to create your own Kubernetes cluster using GKE and deploy your docker image on it.
![k8s](https://en.wikipedia.org/wiki/Kubernetes#/media/File:Kubernetes_(container_engine).png)
Prerequisites:
- Your personal Google Cloud Platform account
- (Optional) Docker image with your web application. 
Don't worry if you don't have one, you're free to use the example provided below.  

Let's begin with creating your first Kubernetes cluster.
From the available Google Cloud Platform services choose Kubernetes Engine and open the Clusters tab. It's the main panel for managing your Kubernetes clusters. Choose the Create a Cluster option and follow the steps:
- Choose Standard cluster template.
- Choose your preferred cluster location. [Here](https://cloud.google.com/about/locations/) you can find out more about CGP zones and where the zones are located. 
- Let's continue with the default values for Node pools (Machine type and Number of nodes) and continue with Create.

It might take up to a couple minutes for GCP to spin up and prepare the cluster. Enter the "Cluster" tab and wait until for the green tick appears next to the cluster name meaning the cluster is ready to use. 

From now on we'll be using the built-in Cloud Shell tool to manage the cluster. Cloud Shell enables a CLI access to your Google Cloud through a web linux console. Click the Connect button next to the cluster name and continue with Run in Cloud Shell. Now, we're connected to the created cluster.

In order deploy our application we need to specify the all the necessary


```
apiVersion: apps/v1
kind: Deployment
metadata:
   name: hello-world-app
spec:
   replicas: 2
   strategy:
    type: RollingUpdate
    rollingUpdate:
     maxSurge: 2
     maxUnavailable: 0
   template:
     spec:
       containers:
       - name: hello-world-container
         imagePullPolicy: "Always"
         image: dockercloud/hello-world:latest
         ports:
         - containerPort: 80
         readinessProbe:
            httpGet:
              path: /
              port: 80
            initialDelaySeconds: 10
            periodSeconds: 15
            successThreshold: 1
            failureThreshold: 25
```




