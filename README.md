# text-processing-service
betvictor text-processing-service

Additional informations:

For kafka communication i've used the bitnami/kafka helm chart (helm install betvictor-kafka bitnami/kafka) <br />
The service are also compatible with helm <br />
You can start the service with skaffold if it's more convenient (skaffold dev --port-forward) <br />

Version informations:

java: 11 <br />
helm: v3.5.3 <br />
skaffold: v1.21.0 <br />
k8s: v1.19.3 <br />

Install / deployment example:

helm install betvictor-kafka bitnami/kafka <br />
from text-processing-service root -> skaffold dev --port-forward -> "http://localhost:{exposed-port}/betvictor/text?p_start=1&p_end=2&avg_p_length=long" endpoint available
