FROM node:21.7-alpine3.19
LABEL authors="edgar.ramirez"
WORKDIR /app
COPY . .
EXPOSE 3001
RUN adduser  \
    --disabled-password \
    product-web-app-user

USER product-web-app-user
