package orchestrator.rptech.com.saga;

import orchestrator.rptech.com.model.OrderStatus;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import rptech.com.coreapis.*;

import javax.inject.Inject;
import java.util.UUID;

@Saga
public class OrderManagementSaga {

    @Inject
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent orderCreatedEvent) {
        String paymentId = UUID.randomUUID().toString();
        System.out.println("Saga invoked");

        //associate Saga
        SagaLifecycle.associateWith("paymentId", paymentId);

        System.out.println("order id" + orderCreatedEvent.orderId);

        //send the commands
        commandGateway.send(new CreateInvoiceCommand(paymentId, orderCreatedEvent.orderId));

    }


    @SagaEventHandler(associationProperty = "paymentId")
    public void handle(InvoiceCreatedEvent invoiceCreatedEvent){
        // TODO
        // if (payment_fail) rollbackPayment() else createShipping()


        String shippingId = UUID.randomUUID().toString();

        System.out.println("Saga continued");

        //associate Saga with shipping
        SagaLifecycle.associateWith("shipping", shippingId);

        CreateShippingCommand createShippingCommand = new CreateShippingCommand(shippingId, invoiceCreatedEvent.orderId, invoiceCreatedEvent.paymentId);

        //send the create shipping command
        commandGateway.send(new CreateShippingCommand(shippingId, invoiceCreatedEvent.orderId, invoiceCreatedEvent.paymentId));

        //Object response = commandGateway.sendAndWait(new CreateShippingCommand(shippingId, invoiceCreatedEvent.orderId, invoiceCreatedEvent.paymentId));
        //System.out.println(">> Create shipping, and wait. Response: " + response);


    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderShippedEvent orderShippedEvent){
        // TODO
        // if (shipping_fail) rollbackShipping() else createShipping()

        commandGateway.send(new UpdateOrderStatusCommand(orderShippedEvent.orderId, String.valueOf(OrderStatus.SHIPPED)));
    }


    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderUpdatedEvent orderUpdatedEvent){
        SagaLifecycle.end();
    }
}
