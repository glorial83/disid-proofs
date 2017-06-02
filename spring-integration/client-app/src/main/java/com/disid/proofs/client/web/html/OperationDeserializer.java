package com.disid.proofs.client.web.html;
import com.disid.proofs.client.domain.Operation;
import com.disid.proofs.client.service.api.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.roo.addon.web.mvc.controller.annotations.config.RooDeserializer;

/**
 * = OperationDeserializer
 *
 * TODO Auto-generated class documentation
 *
 */
@RooDeserializer(entity = Operation.class)
public class OperationDeserializer extends JsonObjectDeserializer<Operation> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private OperationService operationService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ConversionService conversionService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param operationService
     * @param conversionService
     */
    @Autowired
    public OperationDeserializer(@Lazy OperationService operationService, ConversionService conversionService) {
        this.operationService = operationService;
        this.conversionService = conversionService;
    }
}
