# Cluster setup

# Install openSUSE

```
#Add user to wheel
sudo usermod -aG wheel $USER
visudo  # uncomment wheel privileges

#  Install docker
sudo zypper in docker docker-compose
# Enable service
sudo systemctl start docker
sudo systemctl enable docker
sudo usermod -aG docker $USER
```

# Install k3s (SKIP THIS)
[k3s instllation](https://docs.k3s.io/installation/requirements)

k3s install script

```
curl -sfL https://get.k3s.io | sh -
systemctl status k3s    # check status

# ? why => look into this
sudo /usr/local/bin/k3s kubectl get all -n kube-system

```








## Install k3d

```
wget -q -O - https://raw.githubusercontent.com/k3d-io/k3d/main/install.sh | bash

k3d cluster create mycluster
kubectl get nodes


# kubectl
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
sudo install kubectl /usr/local/bin/kubectl


# helm
curl -s https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 | bash


# rancher
sudo helm repo add rancher-latest https://releases.rancher.com/server-charts/latest
sudo helm install rancher rancher-latest/rancher \
   --namespace cattle-system \
   --create-namespace \
   --set ingress.enabled=false \
   --set tls=external \
   --set replicas=1


kubectl apply -f rancher.yaml

sudo firewall-cmd --zone=public --add-port=8901/tcp


```

rancher.yaml
```yaml
apiVersion: v1
kind: Service
metadata:
  labels:
    app: rancher
  name: ranchernp
  namespace: cattle-system
spec:
  ports:
  - name: http
    nodePort: 30080
    port: 80
    protocol: TCP
    targetPort: 80
  - name: https-internal
    nodePort: 30081
    port: 443
    protocol: TCP
    targetPort: 443
  selector:
    app: rancher
  type: NodePort
```



firewall
```
firewall-cmd --permanent --add-port=6443/tcp #apiserver
firewall-cmd --permanent --zone=trusted --add-source=10.42.0.0/16 #pods
firewall-cmd --permanent --zone=trusted --add-source=10.43.0.0/16 #services
firewall-cmd --reload
```



# Kubernetes API


sudo /usr/local/bin/kubectl create serviceaccount servicebot -n local

sudo /usr/local/bin/kubectl get serviceaccounts -n local

sudo /usr/local/bin/kubectl describe serviceaccount servicebot -n local

sudo /usr/local/bin/kubectl create token servicebot -n local

create role

sudo /usr/sbin/kubectl apply -f servicebot_role.yaml

check

sudo /usr/local/bin/kubectl get role servicebot-role -n local

sudo /usr/local/bin/kubectl get rolebinding servicebot-binding -n local

can i patch:

sudo /usr/local/bin/kubectl auth can-i patch deployments.apps --as=system:serviceaccount:local:servicebot --namespace=local


ca-data

sudo /usr/local/bin/kubectl config view --raw --output='jsonpath={.clusters[0].cluster.certificate-authority-data}'


curl -X GET https://localhost:42473/api --header "Authorization: Bearer TOKEN" --insecure



sudo /usr/local/bin/kubectl get pods -n local


eyJhbGciOiJSUzI1NiIsImtpZCI6Ii16Q3lTbmJ6OUNWa1lFd2g0RWNOQkptaTdySUhKazROX2lEa0QtU1VhVmMifQ.eyJhdWQiOlsiaHR0cHM6Ly9rdWJlcm5ldGVzLmRlZmF1bHQuc3ZjLmNsdXN0ZXIubG9jYWwiLCJrM3MiXSwiZXhwIjoxNzQ2NjQwNDkzLCJpYXQiOjE3NDY2MzY4OTMsImlzcyI6Imh0dHBzOi8va3ViZXJuZXRlcy5kZWZhdWx0LnN2Yy5jbHVzdGVyLmxvY2FsIiwianRpIjoiZWRjMWQ4YzEtNzgxZS00MTBjLThiNjEtNjhiNzUyNzg3MTcyIiwia3ViZXJuZXRlcy5pbyI6eyJuYW1lc3BhY2UiOiJsb2NhbCIsInNlcnZpY2VhY2NvdW50Ijp7Im5hbWUiOiJzZXJ2aWNlYm90IiwidWlkIjoiNTljYTdjMmUtZjRjYy00NzYzLTlkNjgtYWFjZTE3ZWJhMzY4In19LCJuYmYiOjE3NDY2MzY4OTMsInN1YiI6InN5c3RlbTpzZXJ2aWNlYWNjb3VudDpsb2NhbDpzZXJ2aWNlYm90In0.aYdWWe2GyvKncbN0QojFXzWcPh7cVU2KhTJzW78X6sq0zxcdAx7pt5P8M0YbhOaj5Rb-LZujx2dAa41x6CkjFYzSsZ1SZkD3O2f1W3bYXF1mYSzgkSCfB1fxNW3FdTu11HfqecRclYl4mQvuRkpEtFKGNk0ttIqKiL_6q3hqko1vihGFkrxO_kZZzw2a67aE8WLwG7tXzb6pIiu0G1JwHxLXWjggPgcGLqh5Lq5aUu_ZZcgC9LI76c5IxA9gNmGm5dBlHE_toqd5azXky4XumbVx4rvi4jwW0Du0UY1bloWjNCQbGPetV4_BHtRN_OKfJGus1z2rMTcujGrrK8BL4Q

 
curl -X GET https://1localhost:42473/api --header "Authorization: Bearer TOKEN" --insecure

 
curl -X GET https://localhost:42473/api --header "Authorization: Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6Ii16Q3lTbmJ6OUNWa1lFd2g0RWNOQkptaTdySUhKazROX2lEa0QtU1VhVmMifQ.eyJhdWQiOlsiaHR0cHM6Ly9rdWJlcm5ldGVzLmRlZmF1bHQuc3ZjLmNsdXN0ZXIubG9jYWwiLCJrM3MiXSwiZXhwIjoxNzQ2NjQwNDkzLCJpYXQiOjE3NDY2MzY4OTMsImlzcyI6Imh0dHBzOi8va3ViZXJuZXRlcy5kZWZhdWx0LnN2Yy5jbHVzdGVyLmxvY2FsIiwianRpIjoiZWRjMWQ4YzEtNzgxZS00MTBjLThiNjEtNjhiNzUyNzg3MTcyIiwia3ViZXJuZXRlcy5pbyI6eyJuYW1lc3BhY2UiOiJsb2NhbCIsInNlcnZpY2VhY2NvdW50Ijp7Im5hbWUiOiJzZXJ2aWNlYm90IiwidWlkIjoiNTljYTdjMmUtZjRjYy00NzYzLTlkNjgtYWFjZTE3ZWJhMzY4In19LCJuYmYiOjE3NDY2MzY4OTMsInN1YiI6InN5c3RlbTpzZXJ2aWNlYWNjb3VudDpsb2NhbDpzZXJ2aWNlYm90In0.aYdWWe2GyvKncbN0QojFXzWcPh7cVU2KhTJzW78X6sq0zxcdAx7pt5P8M0YbhOaj5Rb-LZujx2dAa41x6CkjFYzSsZ1SZkD3O2f1W3bYXF1mYSzgkSCfB1fxNW3FdTu11HfqecRclYl4mQvuRkpEtFKGNk0ttIqKiL_6q3hqko1vihGFkrxO_kZZzw2a67aE8WLwG7tXzb6pIiu0G1JwHxLXWjggPgcGLqh5Lq5aUu_ZZcgC9LI76c5IxA9gNmGm5dBlHE_toqd5azXky4XumbVx4rvi4jwW0Du0UY1bloWjNCQbGPetV4_BHtRN_OKfJGus1z2rMTcujGrrK8BL4Q" --insecure 
 
curl -X GET https://localhost:42473/api/v1/namespaces/local/pods --header "Authorization: Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6Ii16Q3lTbmJ6OUNWa1lFd2g0RWNOQkptaTdySUhKazROX2lEa0QtU1VhVmMifQ.eyJhdWQiOlsiaHR0cHM6Ly9rdWJlcm5ldGVzLmRlZmF1bHQuc3ZjLmNsdXN0ZXIubG9jYWwiLCJrM3MiXSwiZXhwIjoxNzQ2NjQ2Mjc4LCJpYXQiOjE3NDY2NDI2NzgsImlzcyI6Imh0dHBzOi8va3ViZXJuZXRlcy5kZWZhdWx0LnN2Yy5jbHVzdGVyLmxvY2FsIiwianRpIjoiYTgwZDAxMzYtODgwNS00Yjk1LTkyMTktZjkzZGE0YTM1OWU4Iiwia3ViZXJuZXRlcy5pbyI6eyJuYW1lc3BhY2UiOiJsb2NhbCIsInNlcnZpY2VhY2NvdW50Ijp7Im5hbWUiOiJzZXJ2aWNlYm90IiwidWlkIjoiNTljYTdjMmUtZjRjYy00NzYzLTlkNjgtYWFjZTE3ZWJhMzY4In19LCJuYmYiOjE3NDY2NDI2NzgsInN1YiI6InN5c3RlbTpzZXJ2aWNlYWNjb3VudDpsb2NhbDpzZXJ2aWNlYm90In0.shZnq8uUpgezN3FF8tXakYfVV-5nPHM-ARlCGP2c0s47nnvOo5jVjL5k5hnnzoiG96xcLfs_UZKnSQ3aiqYdytCzFkjVUq-_ob8f7GU4NgB3_UohMKvciZU6G4_QLY2bjqCS4Qa5kIliRXTX6EEYiwSBEiOPmZVHWm0G0m5BK1_cvu6pJ4QVyW8O-BTCh0_tQPcObMMxM_FV3QKWSM2oHNV9nKZ0OFLFhzIHUhaiYrta90VRf4HhqKmL6aSnewwWogpzmVSVyVNzmFHchwyecqNfxkutTnB1bnpBYlrx86zau1r5cCgq2aunwUzfFIFGrzUKz5LgXfX41C8zQQPELA" --insecure




remote computer

kubectl get pods -n local --kubeconfig=kubeconfig  --insecure-skip-tls-verify

kubectl apply -f deployment.yaml -n local --kubeconfig=kubeconfig  --insecure-skip-tls-verify

cat deployment.yaml | sed "s/{{KUBE_DATE}}/$(date +"%Y-%m-%d_%H:%M")/g" | kubectl apply -f - -n local --kubeconfig=kubeconfig --insecure-skip-tls-verify

kubectl apply -f $(sed "s/{{KUBE_DATE}}/v$(date +"%Y%m%d%H%M")/g") -n local --kubeconfig=kubeconfig --insecure-skip-tls-verify


DEPLOY

```
kubectl get deployment otp-server -n local --kubeconfig=kubeconfig --insecure-skip-tls-verify -o yaml > temp.yaml

sed -i "s/\bdate:.*$/date: v$(date +"%Y%m%d%H%M")/" temp.yaml

kubectl apply -f temp.yaml -n local --kubeconfig=kubeconfig --insecure-skip-tls-verify
```



THIS WONT WORK AS IT WONT PASS VALIDATION?!?!

TEMPFILE=$(mktemp)
sed "s/{{KUBE_DATE}}/v$(date +"%Y%m%d%H%M")/g" deployment.yaml > "$TEMPFILE"
kubectl apply -f "$TEMPFILE" -n local --kubeconfig=kubeconfig --insecure-skip-tls-verify
rm "$TEMPFILE"


