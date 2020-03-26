<#import "../parts/htmlPad.ftlh" as htmlPad>

<@htmlPad.page "Basket editing">
    <div class="h1">Зміна інформації кошика:</div>
    <form method="post" action="/basket/edit/${editBasket.id}" class="form-group needs-validation w-100" novalidate>
        <#if employeeIds?has_content>
            <div class="form-row my-2">
                <div class="col">
                    <label class="d-inline ml-2" for="employeeId">ID співробітника:</label>
                    <select class="custom-select" name="employeeId">
                        <#list employeeIds as employeeId>
                            <#if editBasket.employee??>
                                <option value="${employeeId}"<#if employeeId == editBasket.employee.id>
                                    selected="selected"</#if>>${employeeId}</option>
                            <#else>
                                <option>${employeeId}</option>
                            </#if>
                        </#list>
                    </select>
                </div>
            </div>
        </#if>

        <#if clientIds?has_content>
            <div class="form-row my-2">
                <div class="col">
                    <label class="d-inline ml-2" for="clientId">ID покупця:</label>
                    <select class="custom-select" name="clientId">
                        <#list clientIds as clientId>
                            <#if editBasket.client??>
                                <option value="${clientId}"<#if clientId == editBasket.client.id>
                                    selected="selected"</#if>>${clientId}</option>
                            <#else>
                                <option>${clientId}</option>
                            </#if>
                        </#list>
                    </select>
                </div>
            </div>
        </#if>

        <div class="form-row my-2">
            <div class="col">
                <label class="d-inline ml-2" for="dateTimeStr">Дата/час покупки:</label>
                <input type="datetime-local" min="2020-02-16T00:00" class="form-control"
                       name="dateTime" value="${editBasket.dateTime!}"
                       placeholder="Дата/Час покупки" required/>
                <div class="invalid-feedback">Некоректна дата/час покупки!</div>
            </div>
        </div>

        <button type="submit" class="btn btn-danger float-right">Зберегти</button>
    </form>
</@htmlPad.page>