#!/bin/sh

configure() {
	aws configure set default.region us-east-1
	aws configure set output json
}

createLambdaFunction() {
	if aws lambda get-function --function-name ${FUNCTION_NAME} --query 'Configuration.FunctionName'
	then
		aws lambda update-function-code --function-name $FUNCTION_NAME --s3-bucket ${S3_BUCKET} --s3-key ${FILE_NAME}
	else
		aws lambda create-function --region us-east-1 --function-name $FUNCTION_NAME --code S3Bucket=${S3_BUCKET},S3Key=${FILE_NAME} --role arn:aws:iam::175801550592:role/lambda_basic_execution --handler tee.finder.qwertee.Hello::handleRequest --runtime java8
	fi
}

sudo apt-get update && apt-get install -y awscli
FILE_NAME="target/qwertee-1.0-SNAPSHOT.jar"
FUNCTION_NAME="qwertee-tee-finder"
S3_BUCKET="daily-tee-finder-deployment"
configure
createPackage
uploadArtifactS3
rm ${FILE_NAME}
createLambdaFunction