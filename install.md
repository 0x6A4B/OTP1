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


### Create RAID, check BIOS for VTx extensions etc.



### Install Proxmox OR create answer file and proxmox autoinstall


