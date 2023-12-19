package ipn.esimecu.labscan.exception;

import ipn.esimecu.labscan.service.DaeService;

public class DaeServiceException extends RuntimeException {

    public final boolean isInternal;

    public DaeServiceException(Throwable throwable, String cause, boolean isInternal) {
        super(cause, throwable);
        this.isInternal = isInternal;
    }

    public DaeServiceException(String cause, boolean isInternal) { this(null, cause, isInternal); }

    public DaeServiceException(Throwable throwable) { this(throwable, null, true); }

}
