
# How to run
To run the application based on the provided Dockerfile, follow these steps:

1. Build the Docker image:

    ```bash
      docker build -t nbp-api-service:latest .
    ```

2. Run a container based on the built image:
    ```bash
      docker run -p 8080:8080 nbp-api-service:latest
    ```

After executing these steps, the application will be accessible at http://localhost:8080

The Swagger documentation for the API is available at http://localhost:8080/swagger-ui/index.html.