<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Basket editing">
    <h2>${errorMessage!}</h2>

    <h1>Зміна інформації кошику:</h1>
    <form method="post" action="/basket/edit/${editBasket.id}">
        <label>ID співробітника:
            <select name="employeeId">
                <#list employeeIds as employeeId>
                    <option>${employeeId}</option>
                </#list>
            </select>
        </label>
        <label>ID клієнта
            <select name="clientId">
                <#list clientIds as clientId>
                    <option>${clientId}</option>
                </#list>
            </select>
        </label>
        <label>Час покупки: <input type="datetime-local" name="dateTimeStr" value="${dateTimeStr!}"/></label>
        <button type="submit">Зберегти</button>
    </form>
</@pad.page>