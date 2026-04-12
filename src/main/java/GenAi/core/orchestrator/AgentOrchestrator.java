package GenAi.core.orchestrator;

import GenAi.core.api.Communication;
import org.springframework.stereotype.Service;

@Service
public class AgentOrchestrator {

    private final Communication communication;

    public AgentOrchestrator(Communication communication) {
        this.communication = communication;
    }

    public String execute(String code) {
        return communication.processCodeRequest(code);
    }
}
