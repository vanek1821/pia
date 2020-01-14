
#!/bin/bash

mvn clean package
docker build -t pia/vanek ./
docker run --rm -p 8080:8080 pia/vanek