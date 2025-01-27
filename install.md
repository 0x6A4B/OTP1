# Install

## Proxmox

### Download, verify checksum, create bootable


Download latest proxmox iso, current version 8.3-1

https://www.proxmox.com/en/downloads


Validate checksum

Checksum SHA256: b5c2d10d6492d2d763e648bc8562d0f77a90c39fac3a664e676e795735198b45

```
echo "b5c2d10d6492d2d763e648bc8562d0f77a90c39fac3a664e676e795735198b45 *proxmox-ve_8.3-1.iso" | shasum -a 256 --check
```

Response:
```
proxmox-ve_8.3-1.iso: OK
```

Write ISO to USB:


Insert USB stick and check dmesg for device

```
dmesg | tail
```

Response:

```
[4291734.693737] [T3005762] sd 6:0:0:0: [sda] Attached SCSI removable disk
```

Write to /dev/sda in 1 MB blocks showing progress status

```
sudo dd if=proxmox-ve_8.3-1.iso of=/dev/sda bs=1M status=progress
```

Response:
```
1186988032 bytes (1,2 GB, 1,1 GiB) copied, 1 s, 1,2 GB/s
1381+1 records in
1381+1 records out
1449048064 bytes (1,4 GB, 1,3 GiB) copied, 331,169 s, 4,4 MB/s
```

=======

Proxmox failed to boot => trying debian bookworm

Debian installed but switching to proxmox kernel lead to it not starting because of megaraid timing out

Ubuntu doesn't install and complains the same

OpenSuse Leap installs nicely and seems to work => RAID not working with iommu? So no hypervisor?


### Create RAID, check BIOS for VTx extensions etc.



### Install Proxmox OR create answer file and proxmox autoinstall


### OpenSuse installed, working on bare metal

Installing:
- openjdk21
- maven
- minikube
- docker
- curl
- vim (already installed by default)
- git (already installed by default)


Starting:
- minikube +  dashboard
- docker with jenkins
- docker with mariadb
- docker with spring boot

Setup:
- add users for ssh
```
sudo useradd -m -g users -G wheel,docker [USERNAME]
sudo passwd [USERNAME]
```
- add users for jenkins
- point reverseproxy to jenkins


Ports from outside:
- mariadb 23306
- ssh 22666
-

Addresses:

- https://otp1-jenkins.0x6a4b.dev
- https://otp1.0x6a4b.dev/adminer
- https://otp1.0x6a4b.dev/api
- https://127.0.0.1:8001/api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy/#/workloads?namespace=default (requires tunneling)


SSH connection:
```
ssh -p 22666 otp1.0x6a4b.dev
```

MariaDB shell:
```
mariadb -u [USERNAME] -h otp1.0x6a4b.dev -P 23306 -p
```
-p parameter can be used to use a specific DB e.g. -p MYDB, without anything it logs to the mariadb without selecting a db

Minikube via SSH tunnel (127.0.0.1:8001):
```
ssh -L 8001:192.168.1.103:8001 $USER@otp1.0x6a4b.dev -p 22666
```

Then to connect to minikube dashboard with web browser:
```
https://127.0.0.1:8001/api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy/#/workloads?namespace=default
```


With putty:
- open putty
- host [user]@otp1.0x6a4b.dev
- port 22666
- connection -> SSH -> tunnels ->
  - sourceport: 8001
  - destination: 8001
- add
    - to save the session: session -> save session
- open



### Programs and versions

```
mvn -version
```
Apache Maven 3.9.9

```
java --version
```
openjdk 21.0.5 2024-10-15

```
docker --version
```
Docker version 26.1.5-ce, build 411e817ddf71

```
kubectl version
```
Client Version: version.Info{Major:"1", Minor:"18", GitVersion:"v1.18.10", GitCommit:"62876fc6d93e891aa7fbe19771e6a6c03773b0f7", GitTreeState:"clean", BuildDate:"2023-07-05T12:00:00Z", GoVersion:"go1.21.9", Compiler:"gc", Platform:"linux/amd64"}
Server Version: version.Info{Major:"1", Minor:"31", GitVersion:"v1.31.0", GitCommit:"9edcffcde5595e8a5b1a35f88c421764e575afce", GitTreeState:"clean", BuildDate:"2024-08-13T07:28:49Z", GoVersion:"go1.22.5", Compiler:"gc", Platform:"linux/amd64"}

```
minikube version
```
minikube version: v1.34.0



### RAID

Fujitsu Primergy TX1320M2F2

PRAID CP400i

- Clear drive configurations
- Create a new virtual drive on VD Mgmt page
- Add 3 of the for disks as there are 3 SAS disks and 1 SATA for extra as a files disk
- Initialize virtual drive
