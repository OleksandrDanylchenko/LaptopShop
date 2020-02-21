<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Basket adding">
    <#if clientIds?has_content>
        <#if employeeIds?has_content>
            <h2>${errorMessage!}</h2>

            <h1>Додати новий кошик:</h1>
            <form method="post" action="/basket/add">
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
                <label>Час покупки: <input type="datetime-local" name="dateTimeStr" value="${RequestParameters.dateTimeStr!}"/></label>
                <button type="submit">Додати</button>
            </form>
        <#else>
            <h1>Не знайдено жодного співробітника.</h1>
            <p><a href="/employee/add">Додайте нового співробітника</a> для створення кошика.</p>
        </#if>
    <#else>
        <h1>Не знайдено жодного активного магазину.</h1>
        <p><a href="/shop/add">Додайте новий магазин</a> для створення кошика.</p>
    </#if>
</@pad.page>