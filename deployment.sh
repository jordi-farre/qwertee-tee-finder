#!/bin/sh

configure() {
	aws configure set default.region us-east-1
	aws configure set output json
}

ROLE_ARN="none"
createRole() {
    aws iam create-role --role-name ${ROLE_NAME} --assume-role-policy-document file://assumeRolePolicyDocument.json
    aws iam put-role-policy --role-name ${ROLE_NAME} --policy-name s3 --policy-document file://s3PolicyFile.json
    ROLE_ARN=$(aws iam get-role --role-name ${ROLE_NAME} | ./jq.sh '.Role.Arn')
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
		aws lambda create-function --region us-east-1 --function-name $FUNCTION_NAME --code S3Bucket=${S3_BUCKET},S3Key=${RELEASE_NAME} --role ${ROLE_ARN} --handler tee.finder.qwertee.Application::handleRequest --runtime java8
	fi
}

ROLE_NAME="qwertee-lambda-role"
FILE_NAME="qwertee-1.0-SNAPSHOT.jar"
RELEASE_NAME="qwertee-${CIRCLE_BUILD_NUM}.jar"
FUNCTION_NAME="qwertee-tee-finder"
S3_BUCKET="daily-tee-finder-deployment"
configure
createRole
uploadArtifactS3
rm ${FILE_NAME}
createLambdaFunction