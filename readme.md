ELK








if(doc['params.servico.tempoGasto'].value == null) {
  return ''
} else {
  return doc['params.servico.tempoGasto'].value
}


docker run -it --rm  \
   -v "$PWD":/usr/src/mymaven \
   -v "$HOME/.m2":/root/.m2 \
   -v "$PWD/target:/usr/src/mymaven/target" \
   -w /usr/src/mymaven maven mvn package 

docker build -t rogeriosilvarocha/fs-service:latest .


docker inspect --format='{{json .State.Health}}' 8f348ca3b31a

docker run -d --name=po-service -p 8080:8080 rogeriosilvarocha/po-service:latest
docker run -d --name=prometheus -p 9090:9090 -v /home/rpsr/poc-service/src/main/resources/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus --config.file=/etc/prometheus/prometheus.yml
docker run -d --name=grafana -p 3000:3000 grafana/grafana





docker run -it --rm  \
   -v "$PWD":/usr/src/mymaven \
   -v "$HOME/.m2":/root/.m2 \
   -v "$PWD/target:/usr/src/mymaven/target" \
   -w /usr/src/mymaven maven mvn package test 