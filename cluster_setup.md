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