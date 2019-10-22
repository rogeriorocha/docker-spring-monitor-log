ARG ELK_VERSION
FROM elasticsearch:${ELK_VERSION}

#FROM docker.elastic.co/elasticsearch/elasticsearch:${ELK_VERSION}

ENV ES_JAVA_OPTS="-Des.path.conf=/etc/elasticsearch"
CMD ["-E", "network.host=0.0.0.0", "-E", "discovery.zen.minimum_master_nodes=1"]
