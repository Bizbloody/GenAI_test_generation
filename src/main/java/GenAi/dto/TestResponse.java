package GenAi.dto;

public class TestResponse {

    private String generatedTests;

    public TestResponse(String generatedTests) {
        this.generatedTests = generatedTests;
    }

    public String getGeneratedTests() {
        return generatedTests;
    }
}