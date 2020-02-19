<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Availability adding">
    <#if shopAddresses?has_content>
        <#if laptopModels?has_content>
            <h2>${errorMessage!}</h2>

            <h1>Додати запис про наявність:</h1>
            <form method="post" action="/availability/add">
                <label>Модель ноутбука:
                    <select name="laptopModel">
                        <#list laptopModels as model>
                            <option>${model}</option>
                        </#list>
                    </select>
                </label>
                <label>Ціна: <input type="number" step="10" min="5000" name="price" value="${(price??)?then(price?c, '')}"/></label>
                <label>Кількість: <input type="number" step="1" min="1" name="quantity" value="${(quantity??)?then(quantity?c, '')}"/></label>
                <label>Адреса магазину:
                    <select name="shopAddress">
                        <#list shopAddresses as address>
                            <option>${address}</option>
                        </#list>
                    </select>
                </label>
                <p><label>Дата початку продаж: <input type="date" name="dateStartStr" value="${dateStartStr!}" min="2019-02-16"></label>
                <label>Дата закінчення продаж: <input type="date" name="dateEndStr" value="${dateEndStr!}" min="2019-02-16"></label></p>
                <button type="submit">Додати</button>
            </form>
        <#else>
            <h1>Не знайдено жодного ноутбука.</h1>
            <p><a href="/laptop/add">Додайте новий ноутбук</a> для створення запису про наявність.</p>
        </#if>
    <#else>
        <h1>Не знайдено жодного активного магазину.</h1>
        <p><a href="/shop/add">Додайте новий магазин</a> для створення запису про наявність.</p>
    </#if>
</@pad.page>