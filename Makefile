NAMESPACE ?= spring-boot-scaffold
IMG_NAME ?= spring-boot-scaffold
# version based tags
IMG_TAG ?= $(shell git describe)
DEV_IMG_TAG ?= ${IMG_TAG}
# projects
GCP_DEV_PROJECT ?= scaffold-dev
GCP_STG_PROJECT_ID ?= scaffold-stg
GCP_PRD_PROJECT ?= scaffold-prd

# image paths
GCP_REGISTRY_HOST ?= gcr.io
DEV_IMG_PATH ?= ${GCP_REGISTRY_HOST}/${GCP_DEV_PROJECT_ID}/${IMAGE_NAME}
STG_IMG_PATH ?= ${GCP_REGISTRY_HOST}/${GCP_STG_PROJECT_ID}/${IMAGE_NAME}
PROD_IMG_PATH ?= ${GCP_REGISTRY_HOST}/${GCP_PROD_PROJECT_ID}/${IMAGE_NAME}

# images
DEV_IMG ?= ${DEV_IMG_PATH}:${DEV_IMG_TAG}
PRD_IMG ?= ${PRD_IMG_PATH}:${IMG_TAG}

clean:
	@ echo "Deleting all resource from Kubernetes Cluster"
	@ kubectl config set-context --current --namespace=$(NAMESPACE)
	@ kubectl delete namespace $(NAMESPACE)

# configuration tasks
configure_common:
	@ echo "Configuring namespace"
	@ kubectl apply -f deployment/base/namespace.yml
	@ kubectl config set-context --current --namespace=$(NAMESPACE)

configure_prd: configure_common
	@ echo "Configuring for prduction"
	@ kubectl apply -f deployment/config/prduction.yml

configure_stg: configure_common
	@ echo "Configuring for prduction"
	@ kubectl apply -f deployment/config/staging.yml

configure_dev: configure_common
	@ echo "Configuring for development"
	@ kubectl apply -f deployment/config/development.yml

configure_redis:
	@ echo "Configure redis secrets"
	@ kubectl create secret generic redis --from-literal=host=$(redis_host)\
	  --from-literal=port=$(redis_port)

configure_jwt:
	@ echo "Configure jwt secrets"
	@ kubectl create secret generic auth --from-literal=clientId=$(client_id)\
	  --from-literal=clientSecret=$(client_secret) --from-literal=jwtKey=$(jwt_key)

# end - configuration tasks

.PHONY: build_common
build_common:
	@ echo "Building java app"
	@ mvn clean package

# docker image build tasks
.PHONY: build
build: build_common
	@ echo "Building dev docker image for version ${IMAGE_VERSION}-$(ci_pipeline_id)"
	@ docker build -t ${IMAGE_VERSION}-$(ci_pipeline_id) .

# docker publish image tasks
.PHONY: publish
publish:
	@ echo "Publishing dev docker image to Google Container Registry"
	@ docker push ${DEV_IMG_PATH}:${IMAGE_VERSION}-$(ci_pipeline_id)

# k8s deployment tasks
deploy_dev:
	@ echo "Deploying ledger service version ${IMAGE_VERSION} into dev cluster"
	@ export IMAGE_NAME=${DEV_IMG_PATH} && export VERSION=${IMAGE_VERSION} && export CI_PIPELINE_ID=$(ci_pipeline_id)
	@ envsubst < deployment/base/deployment.yml | kubectl apply -n $NAMESPACE -f -
	@ unset IMAGE_NAME && unset VERSION && unset CI_PIPELINE_ID

deploy_stg:
	@ echo "Deploying ledger service version ${IMAGE_VERSION} into dev cluster"
	@ export IMAGE_NAME=${STG_IMG_PATH} && export VERSION=${IMAGE_VERSION} && export CI_PIPELINE_ID=$(ci_pipeline_id)
	@ envsubst < deployment/base/deployment.yml | kubectl apply -n $NAMESPACE -f -
	@ unset IMAGE_NAME && unset VERSION && unset CI_PIPELINE_ID

deploy_prod:
	@ echo "Deploying ledger service version ${IMAGE_VERSION} into prod cluster"
	@ export IMAGE_NAME=${PROD_IMG_PATH} && export VERSION=${IMAGE_VERSION} && export CI_PIPELINE_ID=$(ci_pipeline_id)
	@ envsubst < deployment/base/deployment.yml | kubectl apply -n $NAMESPACE -f -
	@ unset IMAGE_NAME && unset VERSION && unset CI_PIPELINE_ID
# end - k8s deployment tasks


# do not use this unless absolutely necessary
clean:
	@ echo "Deleting all resource from Kubernetes Cluster"
	@ kubectl config set-context --current --namespace=${NAMESPACE}
	@ kubectl delete namespace ${NAMESPACE}

reinstall_dev:
	@ echo "Doing fresh installation on dev cluster; cleaning everything"
	@ make clean configure_dev install_dev

# should never be used, but can be in a pipeline step so that you can manually trigger it from a pipeline
reinstall_prd:
	@ echo "Doing fresh installation on prd cluster; cleaning everything"
	@ make clean configure_prd install_prd
# end - k8s deployment tasks

# local development
run:
	@ echo "Running app on local with local profile"
	@ mvn spring-boot:run -Dspring-boot.run.profiles=local

sonar:
	@ echo "Running sonar-scanner"
	@ mvn sonar:sonar
# end - local development
