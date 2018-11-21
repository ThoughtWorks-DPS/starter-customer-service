#!/bin/bash
# assumes $KEY is set in the env by the CI tool

openssl aes-256-cbc -d -in gcloud-secret -k $KEY > gcloud-service-account.json
gcloud auth activate-service-account k8starter-cluster-dev-admin@twdps-k8-starter.iam.gserviceaccount.com --key-file=gcloud-service-account.json
rm -f gcloud-service-account.json
gcloud container clusters get-credentials $GKE_CLUSTER_NAME --zone us-central1-a --project $GCLOUD_PROJECT_ID

