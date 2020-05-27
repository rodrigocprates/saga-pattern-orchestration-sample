package shipping.rptech.com.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import rptech.com.coreapis.CreateShippingCommand;
import rptech.com.coreapis.OrderShippedEvent;
import rptech.com.coreapis.OrderShippingRollbackEvent;
import rptech.com.coreapis.RollbackCreateShippingCommand;
import shipping.rptech.com.enums.OrderShippingStatus;

import java.math.BigDecimal;

@Aggregate
public class ShippingAggregate {
 
    @AggregateIdentifier
    private String shippingId;
 
    private String orderId;
 
    private String paymentId;

    private OrderShippingStatus status;

    public ShippingAggregate() {
    }
 
    @CommandHandler
    public ShippingAggregate(CreateShippingCommand createShippingCommand){
        AggregateLifecycle.apply(new OrderShippedEvent(createShippingCommand.shippingId, createShippingCommand.orderId, createShippingCommand.paymentId));
    }

    @CommandHandler
    public ShippingAggregate(RollbackCreateShippingCommand rollbackCreateShippingCommand){
        AggregateLifecycle.apply(new OrderShippingRollbackEvent(rollbackCreateShippingCommand.shippingId, rollbackCreateShippingCommand.orderId, rollbackCreateShippingCommand.paymentId));
    }

    @EventSourcingHandler
    protected void on(OrderShippedEvent orderShippedEvent){
        this.shippingId = orderShippedEvent.shippingId;
        this.orderId = orderShippedEvent.orderId;
        this.paymentId = orderShippedEvent.paymentId;
    }

    @EventSourcingHandler
    protected void on(RollbackCreateShippingCommand orderShippedEvent){
        this.shippingId = orderShippedEvent.shippingId;
        this.orderId = orderShippedEvent.orderId;
        this.paymentId = orderShippedEvent.paymentId;
    }
}