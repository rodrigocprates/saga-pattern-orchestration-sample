package orchestrator.rptech.com.service;

import orchestrator.rptech.com.endpoint.dto.OrderCreateDTO;
import orchestrator.rptech.com.model.OrderStatus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;
import rptech.com.coreapis.CreateOrderCommand;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderCommandServiceImpl implements OrderCommandService {

    private final CommandGateway commandGateway;

    public OrderCommandServiceImpl(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public CompletableFuture<String> createOrder(OrderCreateDTO orderCreateDTO) {
        return commandGateway.send(new CreateOrderCommand
                (UUID.randomUUID().toString(), orderCreateDTO.getItemType(),
                        orderCreateDTO.getPrice(), orderCreateDTO.getCurrency(), String.valueOf(OrderStatus.CREATED)));
    }
}
