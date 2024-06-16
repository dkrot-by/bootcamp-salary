package com.colvir.bootcamp.salary.mapper;

import com.colvir.bootcamp.salary.dto.*;
import com.colvir.bootcamp.salary.model.PaymentOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentOrderMapper {

    // Запрос -> сущность
    PaymentOrder RequestToPaymentOrder(PaymentOrderRequest request);

    // Сущность -> ответ, в сущности есть ссылка на родительский объект Работник,
    // поэтому вытягиваем из него в ответ дополнительные поля: id и имя
    @Mapping(target = "workerId", source = "worker.id")
    @Mapping(target = "workerName", source = "worker.name")
    PaymentOrderResponse paymentOrderToResponse(PaymentOrder paymentOrder);

    // Список из сущностей -> список для ответа
    List<PaymentOrderResponse> paymentOrderListToResponseList(List<PaymentOrder> paymentOrders);

    default PaymentOrderListResponse paymentOrderListToResponse(List<PaymentOrder> paymentOrders) {
        return new PaymentOrderListResponse(paymentOrderListToResponseList(paymentOrders));
    }

}
