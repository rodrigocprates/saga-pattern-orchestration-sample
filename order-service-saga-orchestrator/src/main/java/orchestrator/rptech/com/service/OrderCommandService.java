package orchestrator.rptech.com.service;

import orchestrator.rptech.com.endpoint.dto.OrderCreateDTO;

import java.util.concurrent.CompletableFuture;

public interface OrderCommandService {
    CompletableFuture<String> createOrder(OrderCreateDTO orderCreateDTO);
}
