# proxying to kube


docker run -d  -p 80:80 -v ~/nginx/default.conf:/etc/nginx/conf.d/default.conf --network host --name nginx nginx