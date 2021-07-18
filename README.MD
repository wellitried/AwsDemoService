### Run
- docker run -d -p 8080:8080 mrkvigor/aws-demo:latest
---
### Build/push container:
- mvn clean install  
- docker build -f src/main/docker/Dockerfile.jvm -t mrkvigor/aws-demo .
- docker push mrkvigor/aws-demo

### Install docker:
- sudo apt update && sudo apt  install docker.io
- sudo groupadd docker && sudo gpasswd -a $USER docker && newgrp docker
- docker --version

### Install kubectl:
- curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
- sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
- kubectl version --client

### Set AWS credentials
- export AWS_ACCESS_KEY_ID=XXX
- export AWS_SECRET_ACCESS_KEY=YYY
- export AWS_DEFAULT_REGION=us-east-1

### Install eksctl
- sudo pip3 install awscli --upgrade
- curl --silent --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /opt

### Create cluster
- eksctl create cluster --name \*name\*
- kubectl create -f demo-deployment.yml
- kubectl expose deployment demo-deployment --type=LoadBalancer --port=8080

---

###### Install minikube:
- curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
- sudo install minikube-linux-amd64 /usr/local/bin/minikube
- minikube start

###### Run demo service on k8s:
- kubectl create -f demo-deployment.yml
- kubectl expose deployment demo-deployment --type=LoadBalancer --port=8080
- minikube service demo-deployment --url
