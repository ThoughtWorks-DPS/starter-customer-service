#!/bin/bash
# assumes $KEY is set in the env by the CI tool
PROJECT_ID=twdps-k8-starter
CLUSTER_NAME=k8startercluster

openssl aes-256-cbc -d -in gcloud-secret -k $KEY > gcloud-service-account.json
gcloud auth activate-service-account k8starter-cluster-dev-admin@twdps-k8-starter.iam.gserviceaccount.com --key-file=gcloud-service-account.json
rm -f gcloud-service-account.json
gcloud container clusters get-credentials $CLUSTER_NAME --zone us-central1-a --project $PROJECT_ID

