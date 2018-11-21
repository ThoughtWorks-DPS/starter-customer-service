#!/bin/bash
# assumes $KEY is set in the env by the CI tool
PROJECT_ID=twdps-k8-starter
CLUSTER_NAME=k8startercluster

gcloud container clusters get-credentials $CLUSTER_NAME --zone us-central1-a --project $PROJECT_ID

# remove key file, not needed anymore
