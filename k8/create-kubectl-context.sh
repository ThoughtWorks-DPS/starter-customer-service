#!/bin/bash
# assumes $KEY is set in the env by the CI tool
GCLOUD_SECRET_FILE=$1
PROJECT_ID=$2

openssl aes-256-cbc -d -in $GCLOUD_SECRET_FILE -k $KEY > gcloud-service-account.json

gcloud auth activate-service-account k8starter-cluster-dev-admin@twdps-k8-starter.iam.gserviceaccount.com --key-file=gcloud-service-account.json
gcloud container clusters get-credentials $CLUSTER_NAME --zone us-central1-a --project $PROJECT_ID

# remove key file, not needed anymore
rm -f gcloud-service-account.json