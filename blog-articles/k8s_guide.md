# Host your application on Google Kubernetes Engine   ![k8s](https://s3.eu-west-3.amazonaws.com/elasticbeanstalk-eu-west-3-430227218185/article/Kubernetes_(container_engine).png)

Google Kubernetes Engine is a Google Cloud Platform service that serves as a Kubernetes-based environment for managing, scaling and deploying containerized applications.
In this tutorial, I'd like to show how to create your own Kubernetes cluster using GKE and deploy your docker image on it.

##Prerequisites:
- Your personal Google Cloud Platform account
- (Optional) Docker image with your web application. 
Don't worry if you don't have one, you're free to use the example provided below.  

##Create a cluster!
Let's begin with creating your first Kubernetes cluster.
![Cluster-tab](https://s3.eu-west-3.amazonaws.com/elasticbeanstalk-eu-west-3-430227218185/article/Screenshot+2019-05-14+at+12.51.22.png)
From the available Google Cloud Platform services choose Kubernetes Engine and open the Clusters tab. It's the main panel for managing your Kubernetes clusters. Choose the Create a Cluster option and follow the steps:
- Choose Standard cluster template.
- Choose your preferred cluster location. [Here](https://cloud.google.com/about/locations/) you can find out more about CGP zones and where the zones are located. 
- Let's continue with the default values for Node pools (Machine type and Number of nodes) and continue with Create.

It might take up to a couple minutes for GCP to spin up and prepare the cluster. Enter the "Cluster" tab and wait until for the green tick appears next to the cluster name meaning the cluster is ready to use:
![ready-cluster](https://s3.eu-west-3.amazonaws.com/elasticbeanstalk-eu-west-3-430227218185/article/Screenshot+2019-05-14+at+12.53.35.png)

From now on we'll be using the built-in Cloud Shell tool to manage the cluster. Cloud Shell enables a CLI access to your Google Cloud through a web linux console. Click the Connect button next to the cluster name and continue with Run in Cloud Shell. Now, we're connected to the created cluster.

##Prepare a configuration file
In order to deploy our application we need to prepare a config file in YAML format - _deployment.yaml_. Let's analyze our configuration.


```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
   name: hello-world
   labels:
     app: hello-world
spec:
   replicas: 2
   strategy:
    type: RollingUpdate
    rollingUpdate:
     maxSurge: 2
     maxUnavailable: 0
   selector:
     matchLabels:
       app: hello-world
   template:
     metadata:
       labels:
         app: hello-world
     spec:
       containers:
       - name: hello-world
         imagePullPolicy: "Always"
         image: karthequian/helloworld:latest
         ports:
         - containerPort: 80
         readinessProbe:
            httpGet:
              path: /
              port: 80
            initialDelaySeconds: 10
            periodSeconds: 3
            successThreshold: 1
            failureThreshold: 25
```

**.metadata.name** - Specifies name of the deployment\
**.spec.replicas** - Number of [Pods](https://kubernetes.io/docs/concepts/workloads/pods/pod/) that will be created. In this case K8s will spin up 2 independent instances of the application.\
**.spec.strategy** - Here we are choosing deployment strategy for our app. Rolling update allows us to update the application with no downtime by creating a set of freshly updated Pods, before terminating the old ones. Furthermore, we can manage our roll out by specifying 2 fields:
 - **.maxSurge** - Specifies the number of new Pods that can be created before shutting down any old ones.
 - **.maxUnavailable** - States the maximum acceptable number of Pods that might be unavailable during the rollout process.  
 
**.selector.matchLabels** - Identifies Pods to manage in the deployment. In this simple case, we simply choose the `app: hello-world` tag that is defined in template below.  
**.spec.containers** - Describes the containers that will run inside pods. In this example, we are going to create one container per Pod.  
**.spec.containers.image** - Defines the docker image that will run in the container. In the example, we're using a hello-world image available in a Dockerhub repository.  
**.spec.containers.imagePullPolicy** - Defines when the image should be updated. In our case we want to trigger the update each time a new version of the image is published in our Dockerhub repository.  
**.ports.containerPort** - Specifies the ports that should be opened in the container for sending and receiving traffic.  
**.containers.readinessProbe** - Allows Kubernetes to check if the new version of pod is ready to run. In this example, when an update occurs a liveness HTTP request will be sent periodically to our container to determine, whether the new pod is ready to replace the old one. Firstly, 10 seconds will pass before the first request is sent (initialDelaySeconds). We can think of this parameter as the estimated minimum amount of time that our container needs to become ready.
Afterwards, each 3 seconds (periodSecond) another HTTP request will be sent. The number of consecutive times that the service should respond before we consider a container ready is specified by successThreshold, in this case it's 1. Also, we don't want to send the requests ceaselessly, so the the maximum threshold for failed requests is set to 25. After 25 failed requests, the container will be labeled as broken.
Caution:
In this tutorial we assume that our application is stateless, if you want your containers to have a persistent memory avaiable check out [this](https://kubernetes.io/docs/tasks/configure-pod-container/configure-persistent-volume-storage/) page.


##Deploy!
So it looks like we're finally ready to deploy our application.
In the Cloud Shell enter:

```console
kubectl apply -f deployment.yaml
```

Now, you can also monitor the state of your pods by executing the following commands:
```console
kubectl get deployments
```
```console
kubectl get pods
```
Now we want to make our application avaiable to the world. Let's create a service and expose our application through port 80.
```console
kubectl expose deployment hello-world --type=LoadBalancer --name=my-service
```
This command exposed a service by creating a GCP load balancer with a static IP assigned to it. However, there are other ways to create a service. You can explore them [here](https://kubernetes.io/docs/concepts/services-networking/service/#publishing-services-service-types)  

You can view all your services by entering `kubectl get pods` in the Cloud Shell.
```console
NAME                            TYPE           CLUSTER-IP      EXTERNAL-IP   PORT(S) 
my-service                      LoadBalancer   10.47.252.211   <pending>     80:30894/TCP   ```
```

After some time the new service should become available with a public static IP under EXTERNAL-IP field.
You can now enter your application via browser: 
![app-foto](https://s3.eu-west-3.amazonaws.com/elasticbeanstalk-eu-west-3-430227218185/article/Screenshot+2019-05-14+at+12.48.44.png)

You can enter your virtual machine through the Cloud Shell via bash. Do that by executing following command:
```console
kubectl exec <your_app_id> -it -- /bin/bash
```




 

