package zk.example.order.api;

public class OrderException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public OrderException(String message, Throwable cause) {
		super(message, cause);
	}

	public OrderException(String message) {
		super(message);
	}
}
