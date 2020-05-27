package rptech.com.coreapis;

public class OrderShippingRollbackEvent {

    public final String shippingId;

    public final String orderId;

    public final String paymentId;

    public OrderShippingRollbackEvent(String shippingId, String orderId, String paymentId) {
        this.shippingId = shippingId;
        this.orderId = orderId;
        this.paymentId = paymentId;
    }
}
