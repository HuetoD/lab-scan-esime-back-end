package ipn.esimecu.labscan.mapper;

import java.io.Serializable;
import java.util.function.Supplier;

public abstract class Mapper<E, DTO extends Serializable> implements SuperMapper<E, DTO> {

    protected Supplier<DTO> temporalDtoSupplier = null;
    protected Supplier<DTO> dtoSupplier = null;

    public Mapper() {
        super();
        this.initSuppliers();
    }

    public Mapper<E, DTO> withSupplier(Supplier<DTO> supplier) {
        dtoSupplier = supplier;
        return this;
    }

    @Override
    public final DTO map(E entity) {
        DTO dto = internalMap(entity, dtoSupplier.get());
        dtoSupplier = temporalDtoSupplier;
        return dto;
    }

    protected abstract DTO internalMap(E entity, DTO dto);

    protected abstract void initSuppliers();

}
