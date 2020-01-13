# Quick Start

Before start, you need to:

1, Create a sqs queue on aws, get queue uri 

2, Create a IAM user, generate access key and access secret

3, Set those values in application.properties

```
cloud.aws.credentials.accessKey=accessKey
cloud.aws.credentials.secretKey=secretKey
cloud.aws.region.static=region
cloud.aws.stack.auto=false

sqs.uri=uri
sqs.queueName=queueName
```
