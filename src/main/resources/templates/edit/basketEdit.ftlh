<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Basket editing">
    <h2>${errorMessage!}</h2>

    <h1>Зміна інформації кошику:</h1>
    <form method="post" action="/basket/edit/${editBasket.id}">
        <#if employeeIds?has_content>
            <label>ID співробітника:
                <select name="employeeId">
                    <#list employeeIds as employeeId>
                        <#if editBasket.employee??>
                            <option value="${employeeId}"<#if employeeId == editBasket.employee.id>
                                selected="selected"</#if>>${employeeId}</option>
                        <#else>
                            <option>${employeeId}</option>
                        </#if>
                    </#list>
                </select>
            </label>
        </#if>

        <#if clientIds?has_content>
            <label>ID клієнта:
                <select name="clientId">
                    <#list clientIds as clientId>
                        <#if editBasket.client??>
                            <option value="${clientId}"<#if clientId == editBasket.client.id>
                                selected="selected"</#if>>${clientId}</option>
                        <#else>
                            <option>${clientId}</option>
                        </#if>
                    </#list>
                </select>
            </label>
        </#if>

        <label>Час покупки: <input type="datetime-local" name="dateTimeStr" min="2019-02-16 00:00"
                                   value="${dateTimeStr!}"/></label>
        <button type="submit">Зберегти</button>
    </form>
</@pad.page>