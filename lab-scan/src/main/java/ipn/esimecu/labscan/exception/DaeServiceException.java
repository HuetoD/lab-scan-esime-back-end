package ipn.esimecu.labscan.exception;

import ipn.esimecu.labscan.service.DaeService;

public class DaeServiceException extends RuntimeException {

    public DaeServiceException(Throwable throwable) { super(throwable); }
    public DaeServiceException(String cause) { super(cause); }

}
