package api;

public class ApiConfiguration {

    //Azure

    /*private final String endpoint;
    private final String apiKey;
    private final String deploymentId;
    private final String apiVersion;


    public ApiConfiguration(String endpoint, String apiKey, String deploymentId, String apiVersion) {
        this.endpoint = endpoint;
        this.apiKey = apiKey;
        this.deploymentId = deploymentId;
        this.apiVersion = apiVersion;
    }

    // Getters
    public String getEndpoint() { return endpoint; }
    public String getApiKey() { return apiKey; }
    public String getDeploymentId() { return deploymentId; }
    public String getApiVersion() { return apiVersion; }
*/
    private final String baseUrl;
    private final String apiKey;
    private final String modelName;

    public ApiConfiguration(String baseUrl, String apiKey, String modelName) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.modelName = modelName;
    }

    public String getBaseUrl() { return baseUrl; }
    public String getApiKey() { return apiKey; }
    public String getModelName() { return modelName; }
}