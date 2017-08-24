#!/bin/sh

configure() {
	aws configure set default.region us-east-1
	aws configure set output json
}

uploadArtifactS3() {
    mv target/${FILE_NAME} ${RELEASE_NAME}
	aws s3 cp ${RELEASE_NAME} s3://${S3_BUCKET}/
}

createLambdaFunction() {
	if aws lambda get-function --function-name ${FUNCTION_NAME} --query 'Configuration.FunctionName'
	then
		aws lambda update-function-code --function-name $FUNCTION_NAME --s3-bucket ${S3_BUCKET} --s3-key ${FILE_NAME}
	else
		aws lambda create-function --region us-east-1 --function-name $FUNCTION_NAME --code S3Bucket=${S3_BUCKET},S3Key=${RELEASE_NAME} --role arn:aws:iam::175801550592:role/lambda_basic_execution --handler tee.finder.qwertee.Application::handleRequest --runtime java8
	fi
}

FILE_NAME="qwertee-1.0-SNAPSHOT.jar"
RELEASE_NAME="qwertee-${CIRCLE_BUILD_NUM}.jar"
FUNCTION_NAME="qwertee-tee-finder"
S3_BUCKET="daily-tee-finder-deployment"
configure
uploadArtifactS3
rm ${FILE_NAME}
createLambdaFunction