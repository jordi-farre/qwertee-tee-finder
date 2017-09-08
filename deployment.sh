#!/bin/bash

configure() {
	aws configure set default.region us-east-1
	aws configure set output json
}

ROLE_ARN="none"
createRole() {
    aws iam create-role --role-name ${ROLE_NAME} --assume-role-policy-document file://assumeRolePolicyDocument.json
    aws iam put-role-policy --role-name ${ROLE_NAME} --policy-name s3 --policy-document file://s3PolicyFile.json
    aws iam put-role-policy --role-name ${ROLE_NAME} --policy-name logs --policy-document file://logPolicyFile.json
    ROLE_ARN=$(aws iam get-role --role-name ${ROLE_NAME} | ./jq.sh -r '.Role.Arn')
    echo "Role $ROLE_NAME created with arn $ROLE_ARN"
}

uploadArtifactS3() {
    mv target/${FILE_NAME} ${RELEASE_NAME}
	aws s3 cp ${RELEASE_NAME} s3://${S3_BUCKET}/
    echo "File $RELEASE_NAME copied to $S3_BUCKET bucket"
}

createFunction() {
    FUNCTION_VERSION=$(aws lambda create-function --region us-east-1 --function-name $FUNCTION_NAME --code S3Bucket=${S3_BUCKET},S3Key=${RELEASE_NAME} --role ${ROLE_ARN} --handler tee.finder.qwertee.Application::handleRequest --runtime java8 --timeout 30 --memory-size 256 --environment Variables={QWERTEE_URL=https://www.qwertee.com/rss,S3_BUCKET=site-tees-production} --publish | ./jq.sh -r '.Version')
    echo "Function $FUNCTION_NAME created with version $FUNCTION_VERSION"
    FUNCTION_ERROR=$(aws lambda invoke --function-name $FUNCTION_NAME /dev/null | ./jq.sh -r ".FunctionError")
    echo "Function $FUNCTION_NAME executed with error $FUNCTION_ERROR"
    if [ -z "$FUNCTION_ERROR" ]; then
        aws lambda create-alias --function-name $FUNCTION_NAME --name PRODUCTION --function-version $FUNCTION_VERSION
        echo "Alias PRODUCTION created for $FUNCTION_NAME with version $FUNCTION_VERSION"
    else
        exit 1
    fi
}

updateFunction() {
    aws lambda update-function-configuration --function-name $FUNCTION_NAME --environment Variables={QWERTEE_URL=https://www.qwertee.com/rss,S3_BUCKET=site-tees-production}
    aws lambda update-function-code --function-name $FUNCTION_NAME --s3-bucket ${S3_BUCKET} --s3-key ${RELEASE_NAME}
    FUNCTION_VERSION=$(aws lambda publish-version --function-name $FUNCTION_NAME | ./jq.sh -r '.Version')
    echo "Function $FUNCTION_NAME updated with version $FUNCTION_VERSION"
    FUNCTION_ERROR=$(aws lambda invoke --function-name $FUNCTION_NAME /dev/null | ./jq.sh -r ".FunctionError")
    echo "Function $FUNCTION_NAME executed with error $FUNCTION_ERROR"
    if [ -z "$FUNCTION_ERROR" ]; then
        aws lambda update-alias --function-name $FUNCTION_NAME --name PRODUCTION --function-version $FUNCTION_VERSION
        echo "Alias PRODUCTION updated for $FUNCTION_NAME with version $FUNCTION_VERSION"
    else
        exit 1
    fi
}

createLambdaFunction() {
	if aws lambda get-function --function-name ${FUNCTION_NAME} --query 'Configuration.FunctionName'
	then
        updateFunction
	else
        createFunction
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
