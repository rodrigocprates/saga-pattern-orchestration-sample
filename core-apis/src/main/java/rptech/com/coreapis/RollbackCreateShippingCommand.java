package rptech.com.coreapis;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class RollbackCreateShippingCommand {

    @TargetAggregateIdentifier
    public final String shippingId;

    public final String orderId;

    public final String paymentId;

    public RollbackCreateShippingCommand(String shippingId, String orderId, String paymentId) {
        this.shippingId = shippingId;
        this.orderId = orderId;
        this.paymentId = paymentId;
    }
}
